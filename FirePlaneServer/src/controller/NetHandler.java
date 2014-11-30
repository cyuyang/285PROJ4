package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Location;
import util.Speed;
import model.Player;
import model.PlayerPlane;
import model.Stuff;
import net.ServerSocket;
import util.Signal;

public class NetHandler implements Runnable
{
  private ServerSocket server;
  
  public NetHandler(ServerSocket inServer)
  {
    server = inServer;
    System.out.println("Net Thread ON!");
  }
  
  public void run()
  {
    String receivedMessage = null;
    String encoding = null;
    while(true)
    {
      receivedMessage = server.receivePakcet();
      String pattern = "(.*)(#0@)(.*)(#1@)(.*)";
      Pattern pt = Pattern.compile(pattern);
      Matcher match = pt.matcher(receivedMessage);
      if (match.find())
      {
        encoding = match.group(3);
      }
      else
      {
        System.out.println("Matching Not Found");
      }
      
      switch(encoding)
      {
        case "00":
          handleLogin(receivedMessage);
          Signal.setStartSignal();
          break;
        case "01":
          handleDisconnect(receivedMessage);
          break;
        case "10":
          handleShoot(receivedMessage);
          break;
        case "11":
          handleMove(receivedMessage);
          break;
        default:
          System.out.println("Unable to decode the message");
      }
      
    }
  }
  
  public void handleLogin(String message)
  {
    //*****#1@USERNAME#2@IP#3@PORT#4@************
    String pattern = "(.*)(#1@)(.*)(#2@)(.*)(#3@)(.*)(#4@)(.*)";
    Pattern pt = Pattern.compile(pattern);
    Matcher mt = pt.matcher(message);
    if (mt.find())
    {
      Player player = 
          new Player(
              mt.group(3),
              mt.group(5),
              Integer.parseInt(mt.group(7))
              );
      Simulator.addPlayer(player);
    }
    
    
  }
  
  public void handleShoot(String message)
  {
    //*********#1@ID#2@**********
    String pattern = "(.*)(#1@)(.*)(#2@)(.*)";
    Pattern pt = Pattern.compile(pattern);
    Matcher mt = pt.matcher(message);
    if (mt.find())
    {
      Simulator.getPlayerPlane(mt.group(3)).shoot();
    }
  }
  
  public void handleMove(String message)
  {
    String pattern = "(.*)(#1@)(.*)(#2@)(.*)(#3@)(.*)(#4@)(.*)";
    Pattern pt = Pattern.compile(pattern);
    Matcher mt = pt.matcher(message);
    if (mt.find())
    {
      String userName = mt.group(3);
      Integer xSpeed = Integer.parseInt(mt.group(5));
      Integer ySpeed = Integer.parseInt(mt.group(7));
      
      Stuff tempStuff = Simulator.getPlayerPlane(userName);
      System.out.println(userName);
      tempStuff.setSpeed(new Speed(xSpeed, ySpeed));
      
      String sendingPacket = 
          "#0@11#1@" + tempStuff.getID() + "#2@" + tempStuff.getLocation().getX().toString()
          +"#3@" + tempStuff.getLocation().getY().toString() + "#4@" + xSpeed.toString()
          +"#5@" + ySpeed.toString()+"#6@";
      
      ServerSocket.sendPacket(sendingPacket);
    }
  }
  
  public void handleDisconnect(String message)
  {
    
  }
  
}
