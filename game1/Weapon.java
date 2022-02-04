package game1;

import utilities.Vector2D;

public class Weapon {

    private Ship parent;

    public double bullet_radius;
    public double bullet_speed;
    public int bullet_hp;

    public int ammo_curr;
    public int ammo_max;

    public long fire = 0;
    public long fire_delay;

    public long ammo_last_regen = 0;
    public long ammo_regen_rate;

    public int type;

    public Weapon(Ship parent, int ammo_max, long fire_delay, long ammo_regen_rate,double bullet_radius, double bullet_speed, int bullet_hp, int type){
        this.parent = parent;

        this.ammo_curr = this.ammo_max = ammo_max;

        this.fire_delay = fire_delay;
        this.ammo_regen_rate = ammo_regen_rate;

        this.bullet_radius = bullet_radius;
        this.bullet_speed = bullet_speed;
        this.bullet_hp = bullet_hp;

        this.type = type;
    }

    public void fire(double angle)
    {
        if(System.currentTimeMillis()-fire > fire_delay && ammo_curr > 0)
        {
            parent.bullet = new Bullet(3000,new Vector2D(parent.position),Vector2D.polar(angle,bullet_speed),bullet_radius,bullet_hp,type);
            fire = System.currentTimeMillis();
            ammo_curr--;
        }
    }

    public void regen()
    {
        if (ammo_curr < ammo_max && System.currentTimeMillis()-ammo_last_regen > ammo_regen_rate)
        {
            ammo_curr++;
            ammo_last_regen = System.currentTimeMillis();
        }
    }

}
