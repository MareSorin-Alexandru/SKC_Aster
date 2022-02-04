package game1;

import utilities.Vector2D;

import java.awt.*;

public class PowerUP extends GameObject{

    public int type;
    public long ttl;
    public long spawn_timestamp;

    public PowerUP(Vector2D position, int type, long ttl)
    {
        super(position,new Vector2D(),1,8);
        this.type = type;
        this.ttl = ttl;
        this.spawn_timestamp=System.currentTimeMillis();
    }

    @Override
    public void hit() {
        hp--;
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - ttl > spawn_timestamp)
            hp--;
    }

    @Override
    public void draw(Graphics2D g) {
        switch (type)
        {
            case 0:
                g.setColor(Color.GREEN);
                break;
            case 1:
                g.setColor(Color.YELLOW);
                break;
            case 2:
                g.setColor(Color.RED);
                break;
            case 3:
                g.setColor(Color.WHITE);
                break;
        }
        g.drawOval((int)(position.x-radius),(int)(position.y-radius),(int)radius*2,(int)radius*2);
    }
}
