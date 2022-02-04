package game1;

import utilities.Vector2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class View extends JComponent {

    public Game game;
    ArrayList<VFX> vfx = new ArrayList<>(4);

    public View(Game game) {
        this.game = game;
        vfx.add(new VFX("src/resources/star1.png",new Vector2D(Constants.FRAME_WIDTH*Math.random(),Constants.FRAME_HEIGHT*Math.random()),Vector2D.polar(Math.random()*2*Math.PI,1)));
        vfx.add(new VFX("src/resources/star2.png",new Vector2D(Constants.FRAME_WIDTH*Math.random(),Constants.FRAME_HEIGHT*Math.random()),Vector2D.polar(Math.random()*2*Math.PI,1)));
        vfx.add(new VFX("src/resources/nebula_big_trans.png",new Vector2D(Constants.FRAME_WIDTH*Math.random(),Constants.FRAME_HEIGHT*Math.random()),Vector2D.polar(Math.random()*2*Math.PI,1)));
        vfx.add(new VFX("src/resources/nebula_small.png",new Vector2D(Constants.FRAME_WIDTH*Math.random(),Constants.FRAME_HEIGHT*Math.random()),Vector2D.polar(Math.random()*2*Math.PI,1)));
        vfx.add(new VFX("src/resources/nebula_small_trans.png",new Vector2D(Constants.FRAME_WIDTH*Math.random(),Constants.FRAME_HEIGHT*Math.random()),Vector2D.polar(Math.random()*2*Math.PI,1)));
    }

    Font game_over_font = new Font("game_over_font",1,40);
    Font score_font = new Font("score_font",Font.BOLD,28);

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        // paint the background
        g.setColor(new Color(2,2,20));
        g.fillRect(0, 0, getWidth(), getHeight());

        //vfx
        for (VFX v : vfx)
            v.draw(g,1,1);

        synchronized (game) {
            //game objects
            for (Asteroid it : game.asteroids)
                it.draw(g);
            for (Bullet b : game.p_bullets)
                b.draw(g);
            for (Bullet b : game.enemy_bullets)
                b.draw(g);
            for (PowerUP p : game.powerUPS)
                p.draw(g);
            for (Ship s : game.ships)
                s.draw(g);
            game.ship.draw(g);
            if (game.ship.hp > 0)
            {
                //if the ship is alive
                game.ship.drawHud(g);
                g.setColor(Color.YELLOW);
                g.setFont(score_font);
                g.drawString(String.valueOf((int)game.score), Constants.FRAME_WIDTH - 192, Constants.FRAME_HEIGHT - 50);
            }
            else {
                g.setColor(Color.ORANGE);
                g.setFont(score_font);
                g.drawString("current score: "+game.final_score,Constants.FRAME_WIDTH/2-130,(int)(Constants.FRAME_HEIGHT/3.1));
                g.setColor(Color.YELLOW);
                g.setFont(game_over_font);
                g.drawString("Top Scores", Constants.FRAME_WIDTH/2-135,(int)(Constants.FRAME_HEIGHT/2.75));
                for (int i = 0; i < 10; i++)
                    g.drawString(String.valueOf(game.scores[i]), Constants.FRAME_WIDTH/2-135, 400 + i * 32);
            }

        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}