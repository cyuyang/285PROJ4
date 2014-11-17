package net;

import imageResource.StaticImageResource;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import net.Packet.PacketTypes;
import net.Packet;
import subjects.PlayerPlaneMP;
import ui.GamePanel;
import util.Location;
import util.Speed;

public class Server extends Thread
{
  /*
  private static ServerSocket serverSocket;
  private static Socket clientSocket;
  private static boolean serverRunning = false;

  private ObjectOutputStream outputStream;
  private ObjectInputStream inputStream;
  */
  DatagramSocket socket;
  
  private ArrayList<PlayerPlaneMP> connectedPlayers = new ArrayList<PlayerPlaneMP>();
  
  public Server()
  {
    try
    {
      this.socket = new DatagramSocket(Settings.SERVER_PORT);
    }
    catch( SocketException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  public void run()
  {
    while(true)
    {
      byte[] data = new byte[1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try
      {
        socket.receive(packet);
      }
      catch( Exception e )
      {
        // TODO: handle exception
        e.printStackTrace();
      }
      parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
    }
  }
  
  private void parsePacket(byte[] data, InetAddress address, int port)
  {
    // TODO Auto-generated method stub
    String message = new String(data).trim();
    PacketTypes type = Packet.getPacketType(message.substring(0, 2));
    switch ( type )
    {
      case INVALID:
        break;
      case LOGIN:
        Packet00Login pLogin = new Packet00Login(data);
        System.out.println("[Server]: " + pLogin.getUserName() + "Connected");
        PlayerPlaneMP newPlayer 
        = new PlayerPlaneMP(new Location(100, 100), new Speed(0, 0),
            StaticImageResource.playerPlanes[0], pLogin.getUserName(), address, port);
        this.addConnection(newPlayer, pLogin);
        /*addConnection(newPlayer, pLogin);
          GamePanel.addPlane(newPlayer);
          this.connectedPlayers.add(newPlayer);*/
        
        break;
      case DISCONNECT:
        break;
      default:
        break;
    }
  }
  
  
  public void sendData(byte[] data, InetAddress ipAddress, int port)
  {
    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
    try
    {
      socket.send(packet);
    }
    catch( Exception e )
    {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  public void sendDataToAllClients(byte[] data)
  {
    // TODO Auto-generated method stub
    for(PlayerPlaneMP p : connectedPlayers)
    {
      sendData(data, p.ipAddress, p.port);
    }
  }
  
  private void addConnection(PlayerPlaneMP player, Packet00Login packet)
  {
    boolean alreadyConnected = false;
    for(PlayerPlaneMP p : this.connectedPlayers)
    {
      if(player.getUserName().equalsIgnoreCase(p.getUserName()))
      {
        if(p.ipAddress == null)
        {
          p.ipAddress = player.ipAddress;
        }
        if(p.port == -1)
          p.port = player.port;
        alreadyConnected = true;
      }
      else
      {
        sendData(packet.getData(), p.ipAddress, p.port);
      }
    }
    if(!alreadyConnected)
    {
      this.connectedPlayers.add(player);
      packet.writeData(this);
    }
  }
}
