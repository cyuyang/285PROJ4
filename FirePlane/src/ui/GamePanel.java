package ui;

import imageResource.StaticImageResource;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import subjects.PlayerPlane;
import util.Location;
import util.Speed;

public class GamePanel extends JPanel implements KeyListener, Runnable
{ 
  private static PlayerPlane p1;
  private Speed p1Speed = new Speed(0.0, 0.0);
  
  BufferedImage currentBackGroundBufferedImage;
  public GamePanel(BufferedImage bg)
  {
    p1 = new PlayerPlane(
        new Location(100.0, 100.0), p1Speed , StaticImageResource.playerPlanes[0], "p1");
    currentBackGroundBufferedImage = bg;
    new Thread(this).start();
    this.addListener();
    // TODO Auto-generated constructor stub
  }
  
  public void update()
  {
    //TODO this function is to update every single elements in the game. 
    //Everything will be on this game Panel 
    p1.move();
  }
  
  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    g.drawImage(currentBackGroundBufferedImage, 0, 0, null);
    p1.paintPlane(g);
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
    
    switch(e.getKeyCode())
    {
      case KeyEvent.VK_UP:
        p1.setSpeed(new Speed(0.0, -10.0));
        break;
      case KeyEvent.VK_DOWN:
        p1.setSpeed(new Speed(0.0, 10.0));
        break;
      case KeyEvent.VK_LEFT:
        p1.setSpeed(new Speed(-10.0, 0.0));
        break;
      case KeyEvent.VK_RIGHT:
        p1.setSpeed(new Speed(10.0, 0.0));
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub
    
        p1.setSpeed(new Speed(0.0, 0.0));
    
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
  
  
}
