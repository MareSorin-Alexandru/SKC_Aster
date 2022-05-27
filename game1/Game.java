package game1;

import utilities.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public List<Asteroid> asteroids;
    public List<AIShip> ships;
    public List<Bullet> enemy_bullets;
    public List<PowerUP> powerUPS;

    public static BasicKeys ctrl;
    public double score;
    public PlayerShip ship;
    public List<Bullet> p_bullets;

    private double asteroid_n = 2;
    private double enemies_n = 0.2;

    //view related
    double[] scores = new double[10];
    double final_score = 0;
    boolean done = false;

    //buffers
    List<Asteroid> asteroid_buffer = new LinkedList<Asteroid>();

    public Game(BasicKeys ctrl) {

        //player
        this.ctrl = ctrl;
        score = 0;
        ship = new PlayerShip(ctrl.action);
        p_bullets = new LinkedList<Bullet>();
        powerUPS = new LinkedList<PowerUP>();

        //ships
        ships = new LinkedList<AIShip>();
        for (int i = 0; i < enemies_n; i++)
        {
            ships.add(new AIShip(new Vector2D(300,500),this));
        }
        enemy_bullets = new LinkedList<Bullet>();

        //asteroids
        asteroids = new LinkedList<Asteroid>();
        for (int i = 0; i < asteroid_n; i++) {
            asteroids.add(Asteroid.makeRandomAsteroid());
        }

    }

    public void update() {
        //ADDITIONS

        //if big asteroid dead - more asteroids at the same location
        for (Asteroid a : asteroids)
            switch (a.hp)
            {
                case -1:
                    asteroid_buffer.addAll(a.makeChildren(6));
                    break;
                case 0:
                    asteroid_buffer.addAll(a.makeChildren(3));
                    break;
                default:
                    break;
            }
        asteroids.addAll(asteroid_buffer);
        asteroid_buffer.clear();

        //ELIMINATIONS
        asteroids.removeIf(a -> a.hp<=0);
        p_bullets.removeIf(b -> b.hp<=0);
        enemy_bullets.removeIf(b -> b.hp<=0);
        ships.removeIf(a -> a.hp<=0);
        powerUPS.removeIf(p -> p.hp<=0);

        //UPDATES

        //asteroids
        for (Asteroid a : asteroids)
            a.update();

        //ships
        for (Ship s : ships)
        {
            s.update();
            if (s.bullet!=null)
                enemy_bullets.add(s.bullet);
        }

        //if ship is alive update it
        if (ship.hp>0)
            ship.update();
        //else update the score and trigger an explosion(once at the moment of death)
        else if (!done)
            {
                scores = ScoreHandler.getScores(score);
                final_score=score;
                p_bullets.add(new Bullet(500,new Vector2D(ship.position),new Vector2D(ship.velocity),65,100,2));
                done = true;
            }
        if(ship.bullet != null)
            p_bullets.add(ship.bullet);

        //powerups
        for (PowerUP p : powerUPS)
            p.update();

        //bullets
        for (Bullet b : p_bullets)
            b.update();
        for (Bullet b : enemy_bullets)
            b.update();

        //HIT DETECTION
        //aux vars
        double aux_x, aux_y;
        //powerups
        for (PowerUP p : powerUPS)
        {
            aux_x =  p.position.x-ship.position.x;
            aux_y = p.position.y-ship.position.y;
            if ( p.radius*p.radius + 2*p.radius*ship.radius + ship.radius*ship.radius >= aux_x*aux_x + aux_y*aux_y )
            {
                p.hit();
                switch (p.type)
                {
                    //hp
                    case 0:
                        ship.hp++;
                        break;
                        //score
                    case 1:
                        score*=1.1;
                        break;
                        //NUKE
                    case 2:
                        p_bullets.add(new Bullet(100,new Vector2D(p.position),new Vector2D(p.velocity),85,100,2));
                        break;
                    case 3:
                        ship.weapon.ammo_max+=4;
                        if (ship.weapon.ammo_regen_rate > 100)
                            ship.weapon.ammo_regen_rate-=5;
                        break;
                }
            }
        }

        //ships and asteroids
        for (int i = 0; i < asteroids.size(); i++)
            for (int j = 0; j < ships.size(); j++)
            {
                aux_x = asteroids.get(i).position.x-ships.get(j).position.x;
                aux_y = asteroids.get(i).position.y-ships.get(j).position.y;
                if(asteroids.get(i).radius*asteroids.get(i).radius + 2*asteroids.get(i).radius*ships.get(j).radius + ships.get(j).radius*ships.get(j).radius >= aux_x*aux_x + aux_y*aux_y )
                {
                    asteroids.get(i).hit();
                    ships.get(j).hit();
                }
            }

        for (Asteroid a : asteroids)
        {
            aux_x = ship.position.x-a.position.x;
            aux_y = ship.position.y-a.position.y;
            if ( a.radius*a.radius + 2*a.radius+ship.radius + ship.radius*ship.radius >= aux_x*aux_x + aux_y*aux_y)
            {
                a.hit();
                ship.hit();
                score += a.radius/8;
                if (Math.random()>0.9)
                    powerUPS.add(new PowerUP(new Vector2D(a.position),(int)(Math.random()*4),1000));
            }
        }

        //bullets and asteroids
        for (int i = 0; i < p_bullets.size(); i++)
        {
            for( int j = 0; j < asteroids.size(); j++)
            {
                aux_x = p_bullets.get(i).position.x-asteroids.get(j).position.x;
                aux_y = p_bullets.get(i).position.y-asteroids.get(j).position.y;
                if (p_bullets.get(i).radius*p_bullets.get(i).radius + 2*p_bullets.get(i).radius*asteroids.get(j).radius + asteroids.get(j).radius*asteroids.get(j).radius >= aux_x*aux_x + aux_y*aux_y)
                {
                    p_bullets.get(i).hit();
                    asteroids.get(j).hit();
                    score += asteroids.get(j).radius/4;
                    if (Math.random()>0.95)
                        powerUPS.add(new PowerUP(new Vector2D(asteroids.get(j).position),(int)(Math.random()*4),3500));
                }
            }
        }
        for (int i = 0; i < enemy_bullets.size(); i++)
        {
            for( int j = 0; j < asteroids.size(); j++)
            {
                aux_x = enemy_bullets.get(i).position.x-asteroids.get(j).position.x;
                aux_y = enemy_bullets.get(i).position.y-asteroids.get(j).position.y;
                if (enemy_bullets.get(i).radius*enemy_bullets.get(i).radius + 2*enemy_bullets.get(i).radius*asteroids.get(j).radius + asteroids.get(j).radius*asteroids.get(j).radius >= aux_x*aux_x + aux_y*aux_y )
                {
                    enemy_bullets.get(i).hit();
                    asteroids.get(j).hit();
                }
            }
        }

        //ships and player
        for (Ship s : ships)
        {
            aux_x = s.position.x-ship.position.x;
            aux_y = s.position.y-ship.position.y;
            if (s.radius*s.radius + 2*s.radius*ship.radius + ship.radius*ship.radius >= aux_x*aux_x + aux_y*aux_y)
            {
                s.hit();
                ship.hit();
                score += s.radius/3;
                if (Math.random()>0.8)
                    powerUPS.add(new PowerUP(new Vector2D(ship.position),(int)(Math.random()*4),900));
            }
        }

        //bullets and ships
        for (int i = 0; i < p_bullets.size(); i++)
            for (int j = 0; j < ships.size(); j++)
            {
                aux_x = p_bullets.get(i).position.x-ships.get(j).position.x;
                aux_y = p_bullets.get(i).position.y-ships.get(j).position.y;
                if (p_bullets.get(i).radius*p_bullets.get(i).radius + 2*p_bullets.get(i).radius*ships.get(j).radius +ships.get(j).radius*ships.get(j).radius >= aux_x*aux_x + aux_y*aux_y)
                {
                    p_bullets.get(i).hit();
                    ships.get(j).hit();
                    score+= ships.get(j).radius;
                    if (Math.random()>0.85)
                        powerUPS.add(new PowerUP(new Vector2D(p_bullets.get(i).position),(int)(Math.random()*4),4000));
                }
            }
        for (Bullet b : enemy_bullets)
        {
            aux_x = b.position.x-ship.position.x;
            aux_y = b.position.y-ship.position.y;
            if (b.radius*b.radius + 2*b.radius*ship.radius +ship.radius*ship.radius >= aux_x*aux_x + aux_y*aux_y)
            {
                ship.hit();
                b.hit();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        BasicKeys ctrl = new BasicKeys();
        Game game = new Game(ctrl);
        View view = new View(game);
        JEasyFrame frame = new JEasyFrame(view, "Asteroids");
        frame.addKeyListener(ctrl);

        // run the game
        while (true) {
            if (ctrl.action.restart)
            {
                game = new Game(ctrl);
                view.game = game;
                frame.comp = view;
                frame.addKeyListener(ctrl);
            }
            //if this level is complete, go to the next one
            if(game.asteroids.isEmpty() && game.ships.isEmpty())
            {
                game.asteroid_n += 0.75;
                game.enemies_n += 0.4;
                //ships
                for (int i = 0; i < game.enemies_n; i++)
                    game.ships.add(new AIShip(new Vector2D(300,500),game));
                //asteroids
                for (int i = 0; i < game.asteroid_n; i++)
                    game.asteroids.add(Asteroid.makeRandomAsteroid());
            }
            else
                synchronized (game)
                {
                    game.update();
                    view.repaint();
                }
            Thread.sleep(Constants.DELAY);
        }
    }



}