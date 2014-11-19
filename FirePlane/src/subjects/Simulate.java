package subjects;

import java.util.ArrayList;
import util.Speed;


public class Simulate
{
  

  public void receiveInstruction(String instr)
  {
    String temp = instr.substring(0, 2);
    instr = instr.substring(3);
   
    /*
     * "00" add
     * "01" delete
     * "02" move
     */
    switch ( temp )
    {
      case "00":
        //instruction type "00#PLANE#B#Xcord#Ycord"
        addStuff(createStuff(instr));
        break;
      case "01":
        //instruction type "01#ID"
        deleteStuff(instr);
        break;
      case "02":
        //instruction type"02#ID#Xspeed#Yspeed"
        moveStuff(instr);
        break;
      default:
        break;
    }
  }

//instruction type "PLANE#B#Xcord#Ycord"
  public Stuff createStuff(String s)
  {
    double xCord = 0;
    double yCord = 0;
    int i = 0;
    String str[];
    str = new String[4];

    for( String temp : s.split("#") )
    {
      str[i] = temp;
      ++i;
    }
    String type = str[0] + str[1];
    try
    {
      xCord = Double.valueOf(str[2]).doubleValue();
      yCord = Double.valueOf(str[3]).doubleValue();
    }
    catch( NumberFormatException excep )
    {
      System.out.println("Wrong create instr");
    }

    switch ( type )
    {
      case "PLANE":
       // return new Plane(xCord, yCord);
      case "BULLET":
        // return new Bullet();
      default:
        return null;
    }
  }

  

  public void addStuff(Stuff s)
  {
    Stuff.getAllStuffs().add(s);
  }

  
 //instruction type "ID"
  public void deleteStuff(String s)
  {
    int keyID = 0;
    boolean isFind = false;
       
    try
    {
      keyID = Integer.valueOf(s).intValue();
    }
    catch( NumberFormatException excep )
    {
      System.out.println("Wrong create instr");
    }

    for( Stuff stuff : Stuff.getAllStuffs())
    {
      if( stuff.getID() == keyID )
      {
        Stuff.getAllStuffs().remove(stuff);
        isFind = true;
        break;
      }
    }
    if( !isFind )
    {
      System.out.println("Wrong ID!");
    }
  }
  
  
 //instruction type"ID#Xspeed#Yspeed" 
  public void moveStuff(String s)
  {
    int keyID = 0;
    boolean isFind = false;
    double xSpeed = 0;
    double ySpeed = 0;
    
    int i = 0;
    String str[];
    str = new String[3];

    for( String temp : s.split("#") )
    {
      str[i] = temp;
      ++i;
    }
    
    try
    {
      keyID = Integer.valueOf(str[0]).intValue();
      xSpeed = Double.valueOf(str[1]).doubleValue();
      ySpeed = Double.valueOf(str[2]).doubleValue();
    }
    catch( NumberFormatException excep )
    {
      System.out.println("Wrong create instr");
    }

    for( Stuff stuff : Stuff.getAllStuffs() )
    {
      if( stuff.getID() == keyID )
      {
        stuff.setSpeed(xSpeed, ySpeed);
        isFind = true;
        break;
      }
    }
    if( !isFind )
    {
      System.out.println("Wrong ID!");
    }
  }

  
  
  
  /*
  public static void setAllStuffs(ArrayList<Stuff> newStuffs)
  {
    allStuffs = newStuffs;
  }*/

  
  /*At least you cannot reset all the planes
  public static void resetStuffs()
  {
    allStuffs = new ArrayList<Stuff>();
  }*/

}
