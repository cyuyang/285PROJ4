package subjects;

import interfaces.Movable;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import util.Location;
import util.Speed;

public abstract class Stuff
{
  private Location location;
  private Speed speed;
  private Integer ID;
  private static Integer nextID;
  private static ArrayList<Stuff> allStuffs = new ArrayList<Stuff>();
  
  public Stuff(double x, double y)
  {
    ID = nextID;
    ++nextID;   
    setLocation(x,y);
  }
  
  
  public void receiveInstruction(String instr)
  { 
    String temp = instr.substring(0,1);
    instr = instr.substring(2);
    switch(temp)
    {
      case "+":
      /*instruction type
       * "+#PLANE#B#Xcord#Ycord"
       */
        addStuff(createStuff(instr));
        break;
      case "-":
      /*instruction type
       * "-#ID"
       */
        deleteStuff(instr);
        break;
      case "?":
      /*instruction type
       *"?#ID#Xspeed#Yspeed"  
       */
        
        break;
      default:
        break;
    }
  }
  
  
  public Stuff createStuff(String s)
  {
    int i = 0;
    String str[];
    str = new String[4];
    double xCord;
    double yCord;
    
    for (String temp: s.split("#"))
    {
      str[i] = temp;
      ++i;
    }
    String type = str[0]+str[1];
    try
    {
      xCord = Double.valueOf(str[2]).doubleValue();
      yCord = Double.valueOf(str[3]).doubleValue();
    }
    catch( NumberFormatException excep )
    {
      System.out.println("Wrong create instr");
    }
        
    switch (type)
    {
      case "PLANE":
         return new Plane(xCord,yCord);
      case "BULLET":
        // return new Bullet();
      default:
         return null;
    }  
  }
  
  
  int keyID;
  boolean isFind;
  public void deleteStuff(String s)
  {
    try
    {
      keyID = Integer.valueOf(s).intValue();
    }
    catch( NumberFormatException excep )
    {
      System.out.println("Wrong create instr");
    }
    
    isFind = false;
    for (Stuff stuff: allStuffs)
    {
      if (stuff.getID() == keyID)
      {
        allStuffs.remove(stuff);
        isFind = true;
        break;
      }
    }
    if (!isFind)
    {
      System.out.println("Wrong ID!");
    }   
  }
  

 
  public void move()
  {
    getLocation().setX(getLocation().getX() + getSpeed().getXSpeed());
    getLocation().setY(getLocation().getY() + getSpeed().getYSpeed());
  }

 
  public void addStuff(Stuff s)
  {
    getAllStuffs().add(s);
  }
  
  
<<<<<<< HEAD
=======
  public void setLocation(double x, double y)
  {
    getLocation().setX(x);
    getLocation().setY(y);
  }
  
  
>>>>>>> master
  public Location getLocation()
  {
    return location;
  }
<<<<<<< HEAD
 
  public abstract Speed getSpeed();
=======
  
>>>>>>> master
 
  public Integer getID()
  {
    return ID;
  }
  
  
  public Speed getSpeed()
  {
    return speed;
  }
  
  
  public void setSpeed(double x, double y)
  {
    getSpeed().setXSpeed(x);
    getSpeed().setYSpeed(y);
  }
  
  
  public static ArrayList<Stuff> getAllStuffs()
  {
    return allStuffs;
  }
  
  public static void setAllStuffs(ArrayList<Stuff> allStuffs)
  {
    Stuff.allStuffs = allStuffs;
  }

  
  public abstract void paint(Graphics g);
  public abstract BufferedImage getImage();
  
}

