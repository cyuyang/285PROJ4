package net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import net.Packet.PacketTypes;
import net.Packet;
import subjects.PlayerPlaneMP;

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
  /*
  public void startServer()
  {
    try
    {
      System.out.println("Server: Starting....");
      serverSocket = new ServerSocket(Settings.SERVER_PORT);
      System.out.println("Connection successful");
      serverRunning = true;
      
      while(serverRunning)
      {
        clientSocket = serverSocket.accept();
        System.out.println("Server: Client connected from: " + clientSocket.getInetAddress().getHostAddress());
        setupStreams(clientSocket);
        outputStream.writeObject("Server: Welcome to server!");
        Object o;
        try
        {
          o = inputStream.readObject();
          outputStream.writeObject("[ECHO]: " + (String)o);;
        }
        catch( ClassNotFoundException e )
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    catch( IOException e )
    {
      e.printStackTrace();
      System.out.println("Error");
      System.exit(7);
    }
    
  }*/
  
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
        System.out.println("{" + address.getHostAddress() + ":" + port + "}" + pLogin.getUserName());
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
}
