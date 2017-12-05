/*
 * CS351L Project #4: PublicAuction.
 * Jacob Hurst, Jaehee Shin, Sarun Luitel, Vincent Huber.
 * 11/27/17
 *
 * BankProtocol.java - Protocol for the bank to follow.
 */

package Bank;

import Agent.Agent;
import Message.Message;

import java.io.Serializable;
import java.net.Socket;

class BankProtocol implements Serializable
{
  private Socket socket;
  private Message message;

  private BankAccount account;

  /**
   * Default constructor.
   *
   * @param socket
   * @param message
   */
  public BankProtocol(Socket socket, Message message)
  {
    this.socket = socket;
    this.message = message;
    //    public Message setup;
    //    setup = handleRequest(message);
  }

  /**
   * Handles requests as they are received from socket.
   *
   * @param request
   * @return
   */
  public Message handleRequest(Message request)
  {
    Message response;
    String message;
    System.out.println("B handling -> " + request.getMessage());
    switch (request.getMessage())
    {
      case "auction central":
        message = "";
        response = new Message(null, "[Bank]: ", message, "Connected", request.getKey(), request.getAmount());
        System.out.println("[Bank]: " + message);
        break;
      case "new":
        String name = ((Agent) request.getSender()).getAgentName();
        int publicID =  ((Agent) request.getSender()).getPublicID();

        System.out.println("[Bank]: Creating new account for " + name + ".");

        account = new BankAccount(name, publicID, 500);
        Bank.addAccounts(account);

        message = "New account = [NAME=" + account.getName() + ", ID=" + account.getPublicID()+ ", BAL=$" + account.getBalance() + ".00].";
        System.out.println("[Bank]: " + message);
        System.out.println("[Bank]: " + Bank.getNumAccounts() + " account(s) are opened!");

        response = new Message(null, "[Bank]: ", message, "Account created", request.getKey(), account.getBalance());
        break;

      case "balance":
        message = "See amount for balance.";
        response = new Message(null, "[Bank]: ", message, "Balance provided", request.getKey(), account.getBalance());
        System.out.println("[Bank]: " + message);
        break;
      case "block":
        message = "Blocking " + account.getBalance() + " on " + account.getName() + "'s...";
        response = new Message(null, "[Bank]: ", message, "Blocked an amount", request.getKey(), account.getBalance());
        System.out.println("[Bank]: " + message);
        break;
      case "unblock":
        message = "Unblocking " + account.getBalance() + " on " + account.getName() + "'s...";
        response = new Message(null, "[Bank]: ", message, "Blocked an amount", request.getKey(), account.getBalance());
        System.out.println("[Bank]: " + message);
        break;
      case "transaction":
        message = "Purchase made, removing $" + account.getBalance() + ".00 from " + account.getName() + "'s account...";
        response = new Message(null, "[Bank]: ", message, "Funds removed", request.getKey(), account.getBalance());
        System.out.println("[Bank]: " + message);
        break;
      case "EXIT":
        message = "Goodbye!";
        response = new Message(null, "[Bank]: ", message, "Goodbye!", request.getKey(), account.getBalance());
        System.out.println("[Bank]: " + message);
        break;
      default:
        message = "Error - request not recognized.";
        int balance = (account != null) ? account.getBalance() : -1;
        response = new Message(null, "[Bank]: ", message, "", -1, balance);
        System.out.println("[Bank]: " + message);
        break;
    }
    return response;
  }
}
