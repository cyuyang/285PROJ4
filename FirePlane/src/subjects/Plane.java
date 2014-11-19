package subjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Location;
import util.Speed;
import interfaces.Movable;

public abstract class Plane extends Stuff
{
  
  private boolean isAlive;
  
  public Plane(double x, double y, BufferedImage _image)
  {
    super(x, y, new Speed(0, 0), _image);
    isAlive = true;
  }
<<<<<<< HEAD
  
  
  //this is very important, we have to have a function to paint them
  
  public void paint(Graphics g)
  {
    // TODO draw the plane
    g.drawImage(getImage(), (int)getLocation().getX(), (int)getLocation().getY(), null);
  }
  
=======

   
>>>>>>> FETCH_HEAD
}
