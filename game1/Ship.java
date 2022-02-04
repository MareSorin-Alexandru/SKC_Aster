package game1;

import utilities.Vector2D;

import java.awt.*;

public abstract class Ship extends GameObject {

    // acceleration when thrust is applied
    public static final double MAG_ACC = 150;

    public int shield_curr;
    public int shield_max;
    public long shield_hit = 0;
    public double shield_regen_delay = 3000;

    public Weapon weapon;
    public Weapon weapon_secondary;

    public Bullet bullet;

    //player ship
    public Ship(Vector2D position) {
        super(position,new Vector2D(),3, 10);
        this.shield_curr = this.shield_max=4;
        this.weapon= new Weapon(this,35,200,650,5,175,1,0);
        this.weapon_secondary = new Weapon(this,5,1000,10000,37.5,75,8,1);
    }

    public void hit()
    {
        if(shield_curr > 0)
        {
            shield_curr--;
            shield_hit = System.currentTimeMillis();
        }
        else
            hp--;
    }

    public abstract void update();

    public abstract void draw(Graphics2D g);
}
