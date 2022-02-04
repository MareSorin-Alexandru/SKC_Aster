package game1;

import utilities.Vector2D;

import java.awt.*;

import static game1.Constants.*;

public class Bullet extends GameObject {

    private long start_time;
    private long ttl;
    private int bullet_hp;
    private int type;


    public Bullet(long ttl, Vector2D position, Vector2D velocity, double radius, int bullet_hp, int type) {
        super(position, velocity,bullet_hp,radius);
        this.ttl = ttl;
        this.radius = radius;
        this.start_time = System.currentTimeMillis();
        this.bullet_hp = bullet_hp;
        this.type = type;
    }

    @Override
    public void hit() {
        hp--;
    }

    public void update() {
        position.addScaled(velocity,DT).wrap(FRAME_WIDTH,FRAME_HEIGHT);

        if (System.currentTimeMillis()-start_time > ttl)
           hp = 0;
    }

    Color cannon_bullet = new Color(0,200,200,190);
    Color nuke_color = new Color(255,100,0,100);
    public void draw(Graphics2D g)
    {
        switch (type)
        {
            case 0:
                g.setColor(Color.WHITE);
                g.drawRect((int)position.x,(int)position.y,2,2);
                break;
            case 1:
                g.setColor(cannon_bullet);
                g.fillOval((int)(position.x-radius),(int)(position.y-radius),(int)radius*2,(int)radius*2);
                break;
            case 2:
                g.setColor(nuke_color);
                g.fillOval((int)(position.x-radius),(int)(position.y-radius),(int)radius*2,(int)radius*2);
                break;
        }
    }
}
