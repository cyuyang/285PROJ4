package net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.net.DatagramPacket;

import util.AddrWrapper;

public class ClientSocket
{
  private int portNum;
  private static DatagramSocket Socket;
  //private static DatagramSocket outSocket;
  private static InetAddress serverIP;
  
  public ClientSocket() throws SocketException
  {
    Socket = new DatagramSocket();
    //inSocket = new DatagramSocket();
    
    /*
     * 
     * TODO Change the IP address getting to something that is reliable
     * 
     */
    try
    {
      serverIP = InetAddress.getByName("localhost");
    }
    catch( UnknownHostException e )
    {
      e.printStackTrace();
    }
  }
  
  public static void sendPacket(String inStr)
  {
    //For test usage only
    //System.out.print("CLient Sent:");
    //System.out.println(inStr);
    byte[] buffer = inStr.getBytes();
      DatagramPacket outPacket = 
          new DatagramPacket(buffer, buffer.length, serverIP, Settings.SERVER_INPORT);
      try
      {
        Socket.send(outPacket);
      }
      catch( IOException e )
      {
        e.printStackTrace();
      }
   }
  
  public String receivePakcet()
  {
    byte[] buffer = new byte[100];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    try
    {
      System.out.print("Client Received:");
      Socket.receive(packet);
      System.out.println(new String(buffer));
      return new String(buffer);
      //TODO How to handle the received packet
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    return null;
  }
}
