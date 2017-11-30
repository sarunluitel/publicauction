/*
 * CS351L Project #4: PublicAuction.
 * Jacob Hurst, Jaehee Shin, Sarun Luitel, Vincent Huber.
 * 11/20/17
 *
 * AuctionHouse.java - Registers with auction central, contains fixed list of items
 * and auctions at most three items at a time, receives and acknowledges bids, requests
 * auction central to release/place holds on bank accounts associated with bids, requests
 * transfer when bid is successful, bids are successful after 30s of inactivity.
 */

package AuctionHouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class AuctionHouse
{
  private final String name;
  private final int publicID;
  private static int totalHouses = 0;
  private LinkedList<String> itemList = new LinkedList<>();
  private LinkedList<Item> itemsForSale = new LinkedList<>();
  
  /**
   * Default constructor.
   *
   * Generates a random public ID.
   */
  public AuctionHouse()
  {
    totalHouses++;
    publicID = totalHouses;
    name = "[House-" + publicID + "]";
    setItems();
  }
  
  /**
   * @return name of this auction house.
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Method to set items available to sell
   *
   * No parameters
   */
  private void setItems()
  {
    for(int i = 1; i < 4; i++)
    {
      itemsForSale.add(new Item(i));
    }
  }

  //Not sure if auction houses need to be ran independently anymore since they are created by auction central
  //May just need to have a thread and protocols for each auction house
  /**
   * Main method for auction house.
   *
   * @param args
   * @throws IOException
   */
  public static void main(String args[]) throws IOException
  {
    Scanner scan = new Scanner(System.in);
    String message;
  
    Socket socket = new Socket(InetAddress.getLocalHost(), 1111);
    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    
    while (!(message = scan.nextLine()).equals("EXIT"))
    {
      out.writeUTF(message);
      
      System.out.println(in.readUTF());
    }

    out.writeUTF("EXIT");
    in.close();
    out.close();
    socket.close();
  }
}
