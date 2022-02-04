package game1;

import utilities.Vector2D;

import java.awt.*;

public abstract class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public int hp;
    public double radius;

    public GameObject(Vector2D position,Vector2D velocity,int hp,double radius){
        this.position = position;
        this.velocity = velocity;
        this.hp = hp;
        this.radius = radius;
    }

    public abstract void hit();

    public abstract void update();

    public abstract void draw(Graphics2D g);
}
