package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Location;
import util.Speed;
import model.Plane;
import model.Player;
import model.PlayerPlane;
import model.Stuff;
import net.ServerSocket;
import util.Signal;

public class ServerNetHandler implements Runnable
{
  private ServerSocket server;
  
  public ServerNetHandler(ServerSocket inServer)
  {
    server = inServer;
    System.out.println("Net Thread ON!");
  }
  
  public void run()
  {
    //String receivedMessage = null;
    DatagramPacket receivePacket;
    String receivedMessage = null;
    String encoding = null;
    while(true)
    {
      receivePacket = server.receivePakcet();
      receivedMessage = new String(receivePacket.getData()).trim();
      System.out.println(receivedMessage);
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
        case "00": //login
          handleLogin(receivedMessage, receivePacket.getAddress(), receivePacket.getPort());
          //Signal.setStartSignal(true);
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
  
  public void handleLogin(String message, InetAddress address, int port)
  {
    //*****#1@USERNAME#2@IP#3@PORT#4@************
    //*****#1@USERNAME#2@IP#3@PORT#4@************
    String pattern = "(.*)(#1@)(.*)(#2@)(.*)";
    Pattern pt = Pattern.compile(pattern);
    Matcher mt = pt.matcher(message);
    if (mt.find())
    {
      Player player = 
          new Player(
              mt.group(3),
              address,
              port);
      PlayerPlane plane = new PlayerPlane(new Location(100,100), new Speed(10, 10), player);
      Simulator.addPlayer(player);
      Simulator.addPlayerPlane(plane);
      //server.sendPacket("#0@00#1@" + player.getUserName() + "#2@" + player.getID() + "#3@");
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
    //server.sendPacket(inStr);
    
  }
  
  public void handleMove(String message)
  {
    String pattern = "(.*)(#1@)(.*)(#2@)(.*)(#3@)(.*)(#4@)(.*)";
    Pattern pt = Pattern.compile(pattern);
    Matcher mt = pt.matcher(message);
    if (mt.find())
    {
      String userName = mt.group(3);
      int xSpeed = Integer.parseInt(mt.group(5));
      int ySpeed = Integer.parseInt(mt.group(7));
      
      Stuff tempStuff = Simulator.getPlayerPlane(userName);
      tempStuff.setSpeed(new Speed(xSpeed, ySpeed));
      
      /*String sendingPacket = 
          "#0@11#1@" + tempStuff.getID() + "#2@" + tempStuff.getLocation().getX().toString()
          +"#3@" + tempStuff.getLocation().getY().toString() + "#4@" + xSpeed.toString()
          +"#5@" + ySpeed.toString()+"#6@";
      
      ServerSocket.sendPacket(sendingPacket);*/
    }
  }
  
  public void handleDisconnect(String message)
  {
    
  }
  
}
