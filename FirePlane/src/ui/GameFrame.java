package ui;

import imageResource.StaticImageResource;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame
{
  final private static int WINDOWS_WIDTH = 550;
  final private static int WINDOWS_HEIGHT = 700;
  
  private static GamePanel currentGamePanel;
  public GameFrame(String title)
  {
    super(title);
    StaticImageResource.initializeImages();
    currentGamePanel = new GamePanel(StaticImageResource.backgroudImages[0]);
    this.add(currentGamePanel);
    this.addKeyListener(currentGamePanel);
    this.setSize(WINDOWS_WIDTH, WINDOWS_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  
  public static void setCurrentPanel(GamePanel _gamePanel)
  {
    currentGamePanel = _gamePanel;
  }
  
  public static GamePanel getCurrentPanel()
  {
    return currentGamePanel;
  }
  
}
