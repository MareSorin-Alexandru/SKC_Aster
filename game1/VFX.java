package game1;

import utilities.Vector2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VFX {

    public BufferedImage img;
    public Vector2D position;
    public Vector2D direction;

    public VFX(String path,Vector2D position, Vector2D direction){
        try {
            img = ImageIO.read(new File(path));
            this.position=position;
            this.direction=direction;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g, double sx, double sy)
    {
        AffineTransform at = AffineTransform.getTranslateInstance(position.x,position.y);
        at.rotate(direction.angle(),img.getWidth()/2,img.getHeight()/2);
        at.scale(1,1);
        g.drawImage(img,at,null);
    }
}
