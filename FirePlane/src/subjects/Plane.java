package subjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Location;
import util.Speed;
import interfaces.Movable;

public class Plane extends Stuff implements Movable
{
  
  private boolean isAlive;
  
  public Plane(Location startLocation, Speed _speed, BufferedImage _planeImage)
  {
    super(startLocation, _speed, _planeImage);
    isAlive = true;
  }
  
  //this is very important, we have to have a function to paint them
  
  public void paint(Graphics g)
  {
    // TODO draw the plane
    g.drawImage(getImage(), (int)getLocation().getX(), (int)getLocation().getY(), null);
  }
  
  @Override
  public void move()
  {
    getLocation().setX(getLocation().getX() + getSpeed().getXSpeed());
    getLocation().setY(getLocation().getY() + getSpeed().getYSpeed());
  }

  @Override
  public void moveLeft()
  {
    // TODO Auto-generated method stub
    //planeLocation.setX(planeLocation.getX() - speed);
  }

  @Override
  public void moveRight()
  {
    // TODO Auto-generated method stub
    //planeLocation.setX(planeLocation.getX() + speed);
  }

  @Override
  public void moveUp()
  {
    // TODO Auto-generated method stub
    //planeLocation.setY(planeLocation.getY() - speed);
  }

  @Override
  public void moveDown()
  {
    // TODO Auto-generated method stub
    //planeLocation.setY(planeLocation.getY() + speed);
  }
  
}
