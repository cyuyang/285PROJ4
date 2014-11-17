package ui;

import imageResource.StaticImageResource;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.Client;
import net.Packet00Login;
import net.Server;
import subjects.PlayerPlane;
import subjects.PlayerPlaneMP;
import subjects.Stuff;
import util.Location;
import util.Speed;

public class GamePanel extends JPanel implements KeyListener, Runnable
{ 
  private static PlayerPlane myPlayer;
  //myPlayer is whom on the 
  private Speed initialSpeed = new Speed(0.0, 0.0);
  
  
  
  BufferedImage currentBackGroundBufferedImage;
  public GamePanel(BufferedImage bg)
  {
    //players = new ArrayList<PlayerPlane>();
    myPlayer = new PlayerPlane(new Location(100, 100), new Speed(0, 0),
            StaticImageResource.playerPlanes[0], 
            JOptionPane.showInputDialog(this, "Enter your username: "));
    
    Stuff.getAllStuffs().add(myPlayer);
    currentBackGroundBufferedImage = bg;
    new Thread(this).start();
    //add listener to panel
    this.addListener();
    try
    {
      Server server = new Server();
      Client clientSocket = new Client("localhost");
      server.start();
      Packet00Login loginPacket = new Packet00Login(myPlayer.getUserName());
      loginPacket.writeData(clientSocket);
      clientSocket.start();
    }
    catch( IOException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(7);
    }
    
    // TODO Auto-generated constructor stub
  }
  
  public void update()
  {
    //TODO this function is to update every single elements in the game. 
    //Everything will be on this game Panel 
    /*for(PlayerPlane p : players)
    {
      p.move();;
    }*/
    for(Stuff p : Stuff.getAllStuffs())
    {
      //System.out.println(p.getUserName());
      if(p != null)
      {
        p.move();
      }
      
    }
    //`System.out.println("##########");
  }
  
  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    g.drawImage(currentBackGroundBufferedImage, 0, 0, null);
    /*for(PlayerPlane p : players)
    {
      p.paintPlane(g);
    }*/
    for(Stuff p : Stuff.getAllStuffs())
    {
      //System.out.println(p.getUserName());
      if(p != null)
      {
        p.paint(g);
      }
      
    }
    
  }
  
  @Override
  public void keyTyped(KeyEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    // TODO Auto-generated method stub
    if(myPlayer != null)
    {
      switch(e.getKeyCode())
      {
        case KeyEvent.VK_UP:
          myPlayer.setSpeed(new Speed(0.0, -10.0));
          break;
        case KeyEvent.VK_DOWN:
          myPlayer.setSpeed(new Speed(0.0, 10.0));
          break;
        case KeyEvent.VK_LEFT:
          myPlayer.setSpeed(new Speed(-10.0, 0.0));
          break;
        case KeyEvent.VK_RIGHT:
          myPlayer.setSpeed(new Speed(10.0, 0.0));
          break;
      }
    }
    
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub
      if(myPlayer != null)
        myPlayer.setSpeed(new Speed(0.0, 0.0));
    
  }
  
  @Override
  public void run()
  {
    while(true)
    {
      this.update();
      this.repaint();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }

  protected void addListener()
  {
    this.addKeyListener(this);
  }
  
  /*public static void addPlane(PlayerPlane addedPlane)
  {
    players.add(addedPlane);
  }*/
  
  
}
