package net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

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
      String message = new String(packet.getData());
      if (message.trim().equalsIgnoreCase("ping"))
      {
        System.out.println("CLIENT > " + new String(packet.getData()));
        sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
        System.out.println(packet.getPort());
      }
      
    }
    
    
  }
  /*
  public void setupStreams(Socket s) throws IOException
  {
    outputStream = new ObjectOutputStream(s.getOutputStream());
    inputStream = new ObjectInputStream(s.getInputStream());
  }*/
  
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
    
  }
}
