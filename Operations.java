import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

class Operations
{
  DatabaseHandler database = null;
  List<Client> accounts = null;
  Scanner input = null;

  Operations(DatabaseHandler databaseHandler, List<Client> clients, Scanner userInput)
  {
    database = databaseHandler;
    accounts = clients;
    input = userInput;
  }

  public void addClient()
  {
      int temporaryId;
      String temporaryFirstName;
      String temporaryLastName;
      String temporaryAddress;
      String temporaryPesel;
      double temporaryBalance = 0.0;
      String choice = "";

      try{
        temporaryId = accounts.get(accounts.size() - 1).getId() + 1;
      }
      catch(NullPointerException e)
      {
        temporaryId = 1;
      }
      catch(ArrayIndexOutOfBoundsException e)
      {
        temporaryId = 1;
      }


      System.out.print("First name: ");
      temporaryFirstName = input.nextLine();
      while((!Pattern.matches("[a-zA-Z]+", temporaryFirstName)) || temporaryFirstName.length() > 10)
      {
        System.out.println("[!] Error: incorrect input. Name can contains only letters up to 10 characters.");
        System.out.print("First name: ");
        temporaryFirstName = input.nextLine();
      }

      System.out.print("Last name: ");
      temporaryLastName = input.nextLine();
      while((!Pattern.matches("[a-zA-Z]+", temporaryLastName)) || temporaryLastName.length() > 21)
      {
        System.out.println("Error: incorrect input. Last name can contains only letters up to 21 characters.");
        System.out.print("Last name: ");
        temporaryLastName = input.nextLine();
      }

      System.out.print("Pesel: ");
      temporaryPesel = input.nextLine();
      while((!Pattern.matches("\\d{11}", temporaryPesel)))
      {
        System.out.println("Error: incorrect input. Pesel must be 11 digit long.");
        System.out.print("Pesel: ");
        temporaryPesel = input.nextLine();
      }

      System.out.print("Address: ");
      temporaryAddress = input.nextLine();
      while((!Pattern.matches("[a-zA-Z0-9 -]+", temporaryAddress)) || temporaryAddress.length() > 27)
      {
        System.out.println("Error: incorrect input. Address can not be longet than 27 characters.");
        System.out.print("Address: ");
        temporaryAddress = input.nextLine();
      }

      System.out.println("\nFirst name: " + temporaryFirstName);
      System.out.println("Last name: " + temporaryLastName);
      System.out.println("Pesel: " + temporaryPesel);
      System.out.println("Address: " + temporaryAddress);
      System.out.print("\nDo you wish to add this client? [Y/n] ");

      choice = input.nextLine();

      while(!choice.equals("Y") && !choice.equals("y") && !choice.equals("") && !choice.equals("N") && !choice.equals("n"))
      {
        System.out.print("Do you wish to add this client? [Y/n] ");
        choice = input.next();
      }

      if(choice.equals("n") || choice.equals("N"))
      {
        System.out.println("\nClient " + temporaryFirstName + " " + temporaryLastName + " has NOT been added.");
      }
      else
      {
        try
        {
          accounts.add(new Client(temporaryId, temporaryFirstName, temporaryLastName, temporaryPesel, temporaryAddress, temporaryBalance));
          database.saveDatabase();
          System.out.println("\nClient " + temporaryFirstName + " " + temporaryLastName + " has been added with ID " + temporaryId);
        }
        catch(Exception e)
        {
          System.out.println("An error ocured while adding user - " + e.getMessage());
        }
      }

  }

  public void showClients()
  {
    String leftAlignFormat = "| %-6d | %-10s | %-21s | %-11s | %-27s | %-25.2f |%n";
    System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
    System.out.format("|   ID   | First Name |       Last Name       |    Pesel    |           Address           |          Balance          |%n");
    System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
    accounts.forEach(client ->
    {
      System.out.format(leftAlignFormat, client.getId(), client.getFirstName(), client.getLastName(), client.getPesel(), client.getAddress(), client.getBalance());
      System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
    }
    );
  }

}
