package game1;

import utilities.Action;
import utilities.Vector2D;

import javax.imageio.ImageIO;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerShip extends Ship {


    public static BufferedImage ship;
    static {
        try {
            ship = ImageIO.read(new File("src/resources/p_ship.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage ship_thrust;
    static {
        try {
            ship_thrust = ImageIO.read(new File("src/resources/p_ship_thrust.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage ship_destroyed;
    static {
        try {
            ship_destroyed = ImageIO.read(new File("src/resources/p_ship_destroyed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // rotation velocity in radians per second
    public static final double STEER_RATE = 3.4;
    public Vector2D direction;
    public Action ctrl;

    public PlayerShip(Action ctrl){
        super(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT*3/4));
        this.ctrl = ctrl;
        this.direction = Vector2D.polar(-Math.PI/2, 1);
    }

    public void update() {
        direction.rotate(STEER_RATE* DT* ctrl.turn);//
        velocity.set(direction.x*MAG_ACC * DT * ctrl.thrust, direction.y*MAG_ACC * DT * ctrl.thrust);
        position.add(velocity).wrap(FRAME_WIDTH,FRAME_HEIGHT);

        if (System.currentTimeMillis() - shield_hit > shield_regen_delay)
        {
            if (shield_curr < shield_max)
                shield_curr +=1;
            shield_hit = System.currentTimeMillis();
        }

        weapon.regen();
        weapon_secondary.regen();

        bullet = null;
        if (ctrl.shoot)
            weapon.fire(direction.angle());
        if (ctrl.shoot_secondary)
            weapon_secondary.fire(direction.angle());
    }

    Font ammo = new Font("ammo",2,30);
    Color shield_color = new Color(0,255,255,165);
    Color hp_color = new Color(0,255,0,165);
    Color primary_ammo = new Color(255,255,255,165);
    Color secondary_ammo = shield_color;
    public void draw(Graphics2D g)
    {
        //ship
        AffineTransform at = AffineTransform.getTranslateInstance(position.x- ship.getWidth()/2,position.y- ship.getHeight()/2);
        at.rotate(direction.angle()-Math.PI/2, ship.getWidth()/2, ship.getHeight()/2);
        at.scale(1,1);
        if(hp > 0)
        {
            if(ctrl.thrust==0)
                g.drawImage(ship,at,null);
            else
                g.drawImage(ship_thrust,at,null);
        }
        else
            g.drawImage(ship_destroyed,at,null);

        //hitbox
        //g.setColor(Color.WHITE);
        //g.drawRect((int)(position.x-radius),(int)(position.y-radius),(int)radius*2,(int)radius*2);
    }

    public void drawHud(Graphics2D g)
    {
        //hud
        g.setColor(shield_color);
        for (int i  = 0; i < shield_max; i++)
            g.drawRect(24+i*40,40,37,37);
        for (int i = 0; i < shield_curr; i++)
            g.fillRect(24+i*40,40,37,37);
        g.setColor(hp_color);
        for (int i = 0; i < hp; i++)
            g.fillRect(24+i*30,20,28,28);
        g.setColor(primary_ammo);
        for (int i = 0; i < weapon.ammo_curr; i++)
            g.fillRect(24+i*6,FRAME_HEIGHT-40,6,30);
        g.setColor(secondary_ammo);
        for (int i = 0; i < weapon_secondary.ammo_curr; i++)
            g.fillOval(24+i*22,FRAME_HEIGHT-40,20,20);

    }
}
