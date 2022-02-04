package game1;

import utilities.Vector2D;

import javax.imageio.ImageIO;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Asteroid extends GameObject {

    public static BufferedImage img;
    static {
        try {
            img = ImageIO.read(new File("src/resources/asteroid.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final double MAX_SPEED = 100;

    private long start_time;
    private long ttl;

    public Asteroid(Vector2D position, Vector2D velocity, double radius) {
        super(position,velocity,(int)(radius/10),radius);
        this.start_time=System.currentTimeMillis();
        this.ttl =(long) (radius * 500);
    }

    @Override
    public void hit() {
        if(hp > 0)
            hp--;
    }

    private static int randomSign()
    {
        if (Math.random() <= 0.5)
            return -1;
        else return 1;
    }

    public ArrayList<Asteroid> makeChildren(int n)
    {
        ArrayList<Asteroid> asteroidList = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            asteroidList.add(new Asteroid(new Vector2D(this.position),new Vector2D(Math.random()*MAX_SPEED*randomSign(),Math.random()*MAX_SPEED*randomSign()),this.radius-9));
        return asteroidList;
    }

    public static Asteroid makeRandomAsteroid() {
        return new Asteroid(new Vector2D(Math.random()* FRAME_WIDTH,Math.random()* FRAME_HEIGHT),new Vector2D(Math.random()*MAX_SPEED*randomSign(),Math.random()*MAX_SPEED*randomSign()),37.5);
    }

    public void update() {
        position.addScaled(velocity,DT).wrap(FRAME_WIDTH,FRAME_HEIGHT);

        if ((System.currentTimeMillis()-start_time > ttl) && (this.radius > 20))
            hp = -1;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.drawOval((int) (position.x - radius), (int) (position.y - radius), 2 * (int)radius, 2 * (int)radius);

        AffineTransform at = AffineTransform.getTranslateInstance(position.x-radius,position.y-radius);
        //at.rotate(direction.angle()-Math.PI/2,img.getWidth()/2,img.getHeight()/2);
        at.scale(radius/(img.getWidth()/2),radius/(img.getHeight()/2));
        g.drawImage(img,at,null);
    }


}