package subjects;

//import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;


//import java.util.ArrayList;
import util.Location;
import util.Speed;

public abstract class Stuff
{
  private Location location = new Location(0, 0);
  private BufferedImage myImage;
  private Speed speed;
  private Integer ID;
  private static Integer nextID = 0;

  private static ArrayList<Stuff> allStuffs = new ArrayList<Stuff>();
  public Stuff(double x, double y, Speed _speed, BufferedImage _image)
  {
    this.myImage = _image;
    this.speed = _speed;
    ID = nextID;
    ++nextID;   
    setLocation(x,y);
  }
  
  public static ArrayList<Stuff> getAllStuffs()
  {
    return allStuffs;
  }
  
  public void move()
  {
    getLocation().setX(getLocation().getX() + getSpeed().getXSpeed());
    getLocation().setY(getLocation().getY() + getSpeed().getYSpeed());
  }

  
  public void setLocation(double x, double y)
  {
    getLocation().setX(x);
    getLocation().setY(y);
  }
  
  
  public Location getLocation()
  {
    return location;
  }
  
 
  public Integer getID()
  {
    return ID;
  }
  
  
  public Speed getSpeed()
  {
    return speed;
  }
  
  
  public void setSpeed(double speedX, double speedY)
  {
    speed.setXSpeed(speedX);
    speed.setYSpeed(speedY);
  }
  
 
  public abstract void paint(Graphics g);

  public BufferedImage getImage()
  {
    return myImage;
  }
  

}

