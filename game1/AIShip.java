package game1;

import utilities.Vector2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static game1.Constants.*;

public class AIShip extends Ship {

    public static BufferedImage ship;
    static {
        try {
            ship = ImageIO.read(new File("src/resources/enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Game game;
    private long last_direction_change = 0;
    private Vector2D target;

    public AIShip(Vector2D position, Game game) {
        super(position);
        this.game=game;
        this.target=new Vector2D();
    }

    private static int randomSign()
    {
        if (Math.random() <= 0.5)
            return -1;
        else return 1;
    }

    @Override
    public void update() {

        if (System.currentTimeMillis()-last_direction_change > 3000)
        {
            velocity.set(Math.random()*MAG_ACC*randomSign(),Math.random()*MAG_ACC*randomSign());
            last_direction_change = System.currentTimeMillis();
        }
        position.addScaled(velocity,DT).wrap(FRAME_WIDTH,FRAME_HEIGHT);

        if (System.currentTimeMillis() - shield_hit > shield_regen_delay)
        {
            if (shield_curr < shield_max)
                shield_curr +=1;
            shield_hit = System.currentTimeMillis();
        }

        target.set(4000,4000);
        for (Asteroid a : game.asteroids)
        {
            if (a.position.dist(this.position) < target.dist(this.position) && a.position.dist(this.position) <400)
                target.set(a.position);
        }
        if (game.ship.position.dist(this.position) < target.dist(this.position) && game.ship.position.dist(this.position)<400)
            target.set(game.ship.position);

        weapon.regen();
        weapon_secondary.regen();

        bullet = null;
        if (target.x != 4000)
        {
            weapon.fire(position.angle(target));
            weapon_secondary.fire(position.angle(target));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = AffineTransform.getTranslateInstance(position.x- ship.getWidth()*0.3/2,position.y- ship.getHeight()*0.3/2);
        at.scale(0.3,0.3);
        g.drawImage(ship,at,null);
    }
}
