import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

class Operations
{
  private DatabaseHandler database = null;
  private List<Client> accounts = null;
  private Scanner input = null;
  private DecimalFormat balanceFormat = new DecimalFormat("#.##");
  private String leftAlignFormat = "| %-6d | %-10s | %-21s | %-11s | %-27s | %-25.2f |%n";

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
      catch(IndexOutOfBoundsException e)
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
        System.out.println("[!] Error: incorrect input. Last name can contains only letters up to 21 characters.");
        System.out.print("Last name: ");
        temporaryLastName = input.nextLine();
      }

      System.out.print("Pesel: ");
      temporaryPesel = input.nextLine();
      while((!Pattern.matches("\\d{11}", temporaryPesel)))
      {
        System.out.println("[!] Error: incorrect input. Pesel must be 11 digit long.");
        System.out.print("Pesel: ");
        temporaryPesel = input.nextLine();
      }

      System.out.print("Address: ");
      temporaryAddress = input.nextLine();
      while((!Pattern.matches("[a-zA-Z0-9\\/\\\\. -]+", temporaryAddress)) || temporaryAddress.length() > 27)
      {
        System.out.println("[!] Error: incorrect input. Address can not be longet than 27 characters.");
        System.out.print("Address: ");
        temporaryAddress = input.nextLine();
      }

      System.out.println("\nFirst name: " + temporaryFirstName);
      System.out.println("Last name: " + temporaryLastName);
      System.out.println("Pesel: " + temporaryPesel);
      System.out.println("Address: " + temporaryAddress);

      choice = comfirmPromt("Do you wish to add this client?");
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
          System.out.println("[!] An error ocured while adding user - " + e.getMessage());
        }
      }

  }

    public void deleteClient()
    {
      int temporaryId;
      final int userToBeDeletedId;
      String userInputId;
      String choice;
      Client temporaryClient = null;
      System.out.print("Provide user ID: ");
      userInputId = input.nextLine();

      while(true)
      {
        try
        {
          temporaryId = Integer.parseInt(userInputId);
          break;
        }
        catch (NumberFormatException nfe)
        {
          System.out.println("[!] Error: wrong input. ID must be a natural number.");
        }

        System.out.print("Provide user ID: ");
        userInputId = input.nextLine();

      }

      try
      {
        userToBeDeletedId = temporaryId;
        temporaryClient = accounts.stream().filter(client -> client.getId() == userToBeDeletedId).findFirst().get();
        System.out.println(temporaryClient);

        choice = comfirmPromt("Do you wish to delete this client?");

        if(choice.equals("n") || choice.equals("N"))
        {
          System.out.println("\nClient " + temporaryClient.getFirstName() + " " + temporaryClient.getLastName() + " has NOT been deleted.");
        }
        else
        {
          accounts.remove(accounts.indexOf(temporaryClient));
          System.out.println("Client has been deleted.");
        }
      }
      catch(NoSuchElementException nsee)
      {
        System.out.println("[!] Could not find user with ID " + temporaryId);
      }
      catch(NullPointerException npe)
      {
        System.out.println("[!] Could not find user with ID " + temporaryId);
      }

    }

    public void depositMoney()
    {
      String userInputId;
      String moenyToBeDeposit;
      String choice;
      Client clientToBeMoneyDeposit;
      final int userId;
      double balanceToBeAdded = 0.0;


      System.out.print("Insert user ID: ");
      userInputId = input.nextLine();
      while((!Pattern.matches("[0-9]+", userInputId)))
      {
        System.out.println("[!] Error: incorrect input. ID can contain only natural numbers.");
        System.out.print("Insert user ID: ");
        userInputId = input.nextLine();
      }

      try
      {
        userId = Integer.parseInt(userInputId);

        try
        {
          clientToBeMoneyDeposit = accounts.stream().filter(client -> client.getId() == userId).findFirst().get();

          while(true)
          {
            try
            {
              System.out.print("Insert ammount of money to be deposit: ");
              moenyToBeDeposit = input.nextLine();
              //System.out.println(moenyToBeDeposit.substring(0,1));
              if(moenyToBeDeposit.equals("") || moenyToBeDeposit.substring(0,1).equals("-"))
              {
                System.out.println("[!] Error: incorrect input.");
              }
              else
              {
                balanceToBeAdded = Double.parseDouble(moenyToBeDeposit.replace(",", "."));
                moenyToBeDeposit = balanceFormat.format(balanceToBeAdded);
                balanceToBeAdded = Double.parseDouble(moenyToBeDeposit.replace(",", "."));
                break;
              }
            }
            catch(NumberFormatException nfe)
            {
              System.out.println("[!] Error: incorrect input.");
              System.out.print("Insert ammount of money to be deposit: ");
            }
          }

          System.out.println("Money to be deposited to "+ clientToBeMoneyDeposit.getFirstName() +" "+ clientToBeMoneyDeposit.getLastName() +": " +balanceToBeAdded);
          choice = comfirmPromt("Do you wish to delete deposit?");

          if(choice.equals("n") || choice.equals("N"))
          {
            System.out.println("Deposit has been terminated.");
          }
          else
          {
            clientToBeMoneyDeposit.setBalance(clientToBeMoneyDeposit.getBalance() + balanceToBeAdded);
            database.saveDatabase();
            System.out.println("Money has been deposited.");
          }
        }
        catch(NoSuchElementException nsee)
        {
          System.out.println("[!] Could not find user with ID " + userInputId);
        }
        catch(NullPointerException npe)
        {
          System.out.println("[!] Could not find user with ID " + userInputId);
        }
        catch(Exception e)
        {
          System.out.println("[!] An error occured while depoting money.");
        }

      }
      catch (NumberFormatException nfe)
      {
        System.out.println("[!] Error: could not parse intput.");
      }

    }

    public void withdrawMoney()
    {
      String userInputId;
      String moenyToBeDeposit;
      String choice;
      Client clientToBeMoneyDeposit;
      final int userId;
      double balanceToBeAdded = 0.0;


      System.out.print("Insert user ID: ");
      userInputId = input.nextLine();
      while((!Pattern.matches("[0-9]+", userInputId)))
      {
        System.out.println("[!] Error: incorrect input. ID can contain only natural numbers.");
        System.out.print("Insert user ID: ");
        userInputId = input.nextLine();
      }

      try
      {
        userId = Integer.parseInt(userInputId);

        try
        {
          clientToBeMoneyDeposit = accounts.stream().filter(client -> client.getId() == userId).findFirst().get();

          while(true)
          {
            try
            {
              System.out.print("Insert ammount of money to be withdraw: ");
              moenyToBeDeposit = input.nextLine();
              //System.out.println(moenyToBeDeposit.substring(0,1));
              if(moenyToBeDeposit.equals("") || moenyToBeDeposit.substring(0,1).equals("-"))
              {
                System.out.println("[!] Error: incorrect input.");
              }
              else
              {
                balanceToBeAdded = Double.parseDouble(moenyToBeDeposit.replace(",", "."));
                moenyToBeDeposit = balanceFormat.format(balanceToBeAdded);
                balanceToBeAdded = Double.parseDouble(moenyToBeDeposit.replace(",", "."));
                break;
              }
            }
            catch(NumberFormatException nfe)
            {
              System.out.println("[!] Error: incorrect input.");
              System.out.print("Insert ammount of money to be deposit: ");
            }
          }

          System.out.println("Money to be withdraw to "+ clientToBeMoneyDeposit.getFirstName() +" "+ clientToBeMoneyDeposit.getLastName() +": " +balanceToBeAdded);
          choice = comfirmPromt("Do you wish to  deposit?");

          if(choice.equals("n") || choice.equals("N"))
          {
            System.out.println("Withdraw has been terminated.");
          }
          else
          {
            clientToBeMoneyDeposit.setBalance(clientToBeMoneyDeposit.getBalance() - balanceToBeAdded);
            database.saveDatabase();
            System.out.println("Money has been withdrawed.");
          }
        }
        catch(NoSuchElementException nsee)
        {
          System.out.println("[!] Could not find user with ID " + userInputId);
        }
        catch(NullPointerException npe)
        {
          System.out.println("[!] Could not find user with ID " + userInputId);
        }
        catch(Exception e)
        {
          System.out.println("[!] An error occured while withdrawing money.");
        }

      }
      catch (NumberFormatException nfe)
      {
        System.out.println("[!] Error: could not parse intput.");
      }

    }


    public void transferMoney()
    {
      String userIdFromWhomWillTransferGo = "";
      String userIdToWhomWillTransferGo = "";
      String moneyToTransferd;
      String choice;
      Client clientFromWhomWillTransferGo;
      Client clientToWhomWillTransferGo;
      final int userId1;
      final int userId2;
      double balanceChange = 0.0;


      System.out.print("Insert user ID from whom will transfer be made: ");
      userIdFromWhomWillTransferGo = input.nextLine();
      while((!Pattern.matches("[0-9]+", userIdFromWhomWillTransferGo)))
      {
        System.out.println("[!] Error: incorrect input. ID can contain only natural numbers.");
        System.out.print("Insert user ID from whom will transfer be made: ");
        userIdFromWhomWillTransferGo = input.nextLine();
      }

      System.out.print("Insert user ID to whom will transfer be made: ");
      userIdToWhomWillTransferGo = input.nextLine();
      while((!Pattern.matches("[0-9]+", userIdToWhomWillTransferGo)))
      {
        System.out.println("[!] Error: incorrect input. ID can contain only natural numbers.");
        System.out.print("Insert user ID to whom will transfer be made: ");
        userIdToWhomWillTransferGo = input.nextLine();
      }

      try
      {
        userId1 = Integer.parseInt(userIdFromWhomWillTransferGo);
        userId2 = Integer.parseInt(userIdToWhomWillTransferGo);

        try
        {
          try
          {
            clientFromWhomWillTransferGo = accounts.stream().filter(client -> client.getId() == userId1).findFirst().get();
          }
          catch(NoSuchElementException nsee)
          {
            System.out.println("[!] Could not find user with ID " + userIdFromWhomWillTransferGo);
            return;
          }

          try
          {
            clientToWhomWillTransferGo = accounts.stream().filter(client -> client.getId() == userId2).findFirst().get();
          }
          catch(NoSuchElementException nsee)
          {
            System.out.println("[!] Could not find user with ID " + userIdToWhomWillTransferGo);
            return;
          }

          while(true)
          {
            try
            {
              System.out.print("Insert ammount of money to be transferd: ");
              moneyToTransferd = input.nextLine();
              if(moneyToTransferd.equals("") || moneyToTransferd.substring(0,1).equals("-"))
              {
                System.out.println("[!] Error: incorrect input.");
              }
              else
              {
                balanceChange = Double.parseDouble(moneyToTransferd.replace(",", "."));
                moneyToTransferd = balanceFormat.format(balanceChange);
                balanceChange = Double.parseDouble(moneyToTransferd.replace(",", "."));
                break;
              }
            }
            catch(NumberFormatException nfe)
            {
              System.out.println("[!] Error: incorrect input.");
              System.out.print("Insert ammount of money to be deposit: ");
            }
          }

          System.out.println("Money to be transfer " +balanceChange+ " from "+ clientFromWhomWillTransferGo.getFirstName() +" "+ clientFromWhomWillTransferGo.getLastName() +" to " +clientToWhomWillTransferGo.getFirstName() +" "+ clientToWhomWillTransferGo.getLastName());
          choice = comfirmPromt("Do you wish to  deposit?");

          if(choice.equals("n") || choice.equals("N"))
          {
            System.out.println("Transfer has been terminated.");
          }
          else
          {
            clientToWhomWillTransferGo.setBalance(clientToWhomWillTransferGo.getBalance() + balanceChange);
            clientFromWhomWillTransferGo.setBalance(clientFromWhomWillTransferGo.getBalance() - balanceChange);
            database.saveDatabase();
            System.out.println("Money has been transfered.");
          }
        }
        catch(NoSuchElementException nsee)
        {
          System.out.println("[!] Could not find user.");
        }
        catch(NullPointerException npe)
        {
          System.out.println("[!] Could not find user.");
        }
        catch(Exception e)
        {
          System.out.println("[!] An error occured while transfering money.");
        }

      }
      catch (NumberFormatException nfe)
      {
        System.out.println("[!] Error: could not parse intput.");
      }

    }

    public void showSpecificClients()
    {
      String choice = choicePromt("> ");

      switch (choice) {
        case "I":
          showClientById();
          break;
        case "F":
          showByFirstName();
          break;
        case "L":
          showByLastName();
          break;
        case "P":
          showByPesel();
          break;
        case "A":
          showByAddress();
          break;
        default:
          break;
        }
    }

  private void showClientById()
  {
    String userToBeShownById;
    final int userId;
    Client accountDetails;

    System.out.print("Insert user ID: ");
    userToBeShownById = input.nextLine();
    while((!Pattern.matches("[0-9]+", userToBeShownById)))
    {
      System.out.println("[!] Error: incorrect input. ID can contain only natural numbers.");
      System.out.print("Insert user ID: ");
      userToBeShownById = input.nextLine();
    }

    try
    {
      userId = Integer.parseInt(userToBeShownById);
      accountDetails =  accounts.stream().filter(client -> client.getId() == userId).findFirst().get();
      printHeader();
      System.out.format(leftAlignFormat, accountDetails.getId(), accountDetails.getFirstName(), accountDetails.getLastName(), accountDetails.getPesel(), accountDetails.getAddress(), accountDetails.getBalance());
      System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");

    }
    catch(NumberFormatException nfe)
    {
      System.out.println("[!] Incorrect ID.");
    }
    catch(NoSuchElementException nsee)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(NullPointerException npe)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(Exception e)
    {
      System.out.println("[!] An error occured while receiving data.");
    }

  }

  private void showByFirstName()
  {
    String userToBeShownByFirstName;
    List<Client> filterdAccounts = new ArrayList<Client>();
    final String pattern;

    System.out.print("Insert user first name: ");
    userToBeShownByFirstName = input.nextLine();
    while((!Pattern.matches("[a-zA-Z]+", userToBeShownByFirstName)))
    {
      System.out.println("[!] Error: incorrect input. First name can contain only letters.");
      System.out.print("Insert user first name: ");
      userToBeShownByFirstName = input.nextLine();
    }

    pattern = userToBeShownByFirstName;

    try
    {
      filterdAccounts = accounts.stream().filter(client -> client.getFirstName().equals(pattern)).collect(Collectors.toList());
      if(filterdAccounts.size() != 0)
      {
        printHeader();
        filterdAccounts.forEach(client ->
        {
          System.out.format(leftAlignFormat, client.getId(), client.getFirstName(), client.getLastName(), client.getPesel(), client.getAddress(), client.getBalance());
          System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
        }
        );
      }
      else
      {
          System.out.println("[!] Could not find any user with first name " + pattern);
      }
    }
    catch(NoSuchElementException nsee)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(NullPointerException npe)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(Exception e)
    {
      System.out.println("[!] An error occured while receiving data.");
    }

  }

  private void showByLastName()
  {
    String userToBeShownByLastName;
    List<Client> filterdAccounts = new ArrayList<Client>();
    final String pattern;

    System.out.print("Insert user last name: ");
    userToBeShownByLastName = input.nextLine();
    while((!Pattern.matches("[a-zA-Z]+", userToBeShownByLastName)))
    {
      System.out.println("[!] Error: incorrect input. Last name can contain only letters.");
      System.out.print("Insert user first name: ");
      userToBeShownByLastName = input.nextLine();
    }

    pattern = userToBeShownByLastName;

    try
    {
      filterdAccounts = accounts.stream().filter(client -> client.getLastName().equals(pattern)).collect(Collectors.toList());
      if(filterdAccounts.size() != 0)
      {
        printHeader();
        filterdAccounts.forEach(client ->
        {
          System.out.format(leftAlignFormat, client.getId(), client.getFirstName(), client.getLastName(), client.getPesel(), client.getAddress(), client.getBalance());
          System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
        }
        );
      }
      else
      {
          System.out.println("[!] Could not find any user with last name " + pattern);
      }
    }
    catch(NoSuchElementException nsee)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(NullPointerException npe)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(Exception e)
    {
      System.out.println("[!] An error occured while receiving data.");
    }

  }

  private void showByPesel()
  {
    String userToBeShownByPesel;
    List<Client> filterdAccounts = new ArrayList<Client>();
    final String pattern;

    System.out.print("Insert user pesel: ");
    userToBeShownByPesel = input.nextLine();
    while((!Pattern.matches("\\d{11}", userToBeShownByPesel)))
    {
      System.out.println("[!] Error: incorrect input. Pesel name can contain only 11 numbers.");
      System.out.print("Insert user pesel: ");
      userToBeShownByPesel = input.nextLine();
    }

    pattern = userToBeShownByPesel;

    try
    {
      filterdAccounts = accounts.stream().filter(client -> client.getPesel().equals(pattern)).collect(Collectors.toList());
      if(filterdAccounts.size() != 0)
      {
        printHeader();
        filterdAccounts.forEach(client ->
        {
          System.out.format(leftAlignFormat, client.getId(), client.getFirstName(), client.getLastName(), client.getPesel(), client.getAddress(), client.getBalance());
          System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
        }
        );
      }
      else
      {
          System.out.println("[!] Could not find any user with pesel " + pattern);
      }
    }
    catch(NoSuchElementException nsee)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(NullPointerException npe)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(Exception e)
    {
      System.out.println("[!] An error occured while receiving data.");
    }

  }

  private void showByAddress()
  {
    String userToBeShownByAddress;
    List<Client> filterdAccounts = new ArrayList<Client>();
    final String pattern;

    System.out.print("Insert user address: ");
    userToBeShownByAddress = input.nextLine();
    while((!Pattern.matches("[a-zA-Z0-9\\/\\\\. -]+", userToBeShownByAddress)))
    {
      System.out.println("[!] Error: incorrect input. Address name can contain only letters, numbers, dots, slashes, backslashes and dashes.");
      System.out.print("Insert user addres: ");
      userToBeShownByAddress = input.nextLine();
    }

    pattern = userToBeShownByAddress;

    try
    {
      filterdAccounts = accounts.stream().filter(client -> client.getAddress().equals(pattern)).collect(Collectors.toList());
      if(filterdAccounts.size() != 0)
      {
        printHeader();
        filterdAccounts.forEach(client ->
        {
          System.out.format(leftAlignFormat, client.getId(), client.getFirstName(), client.getLastName(), client.getPesel(), client.getAddress(), client.getBalance());
          System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
        }
        );
      }
      else
      {
          System.out.println("[!] Could not find any user with address " + pattern);
      }
    }
    catch(NoSuchElementException nsee)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(NullPointerException npe)
    {
      System.out.println("[!] Could not find user.");
    }
    catch(Exception e)
    {
      System.out.println("[!] An error occured while receiving data.");
    }

  }
  //---------------------------------------------------------------------------------------------------------------------------------------------

  public void showClients()
  {
    printHeader();
    accounts.forEach(client ->
    {
      System.out.format(leftAlignFormat, client.getId(), client.getFirstName(), client.getLastName(), client.getPesel(), client.getAddress(), client.getBalance());
      System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
    }
    );
  }

  private void printHeader()
  {
    System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
    System.out.format("|   ID   | First Name |       Last Name       |    Pesel    |           Address           |          Balance          |%n");
    System.out.format("+--------+------------+-----------------------+-------------+-----------------------------+---------------------------+%n");
  }

  private String comfirmPromt(String messageToBeShow)
  {
    String choice;
    System.out.print(messageToBeShow + " [y/n] ");
    choice = input.nextLine();
    while(!choice.equals("Y") && !choice.equals("y") && !choice.equals("N") && !choice.equals("n"))
    {
      System.out.print(messageToBeShow + " [y/n] ");
      choice = input.nextLine();
    }
    return choice;
  }

  private String choicePromt(String messageToBeShow)
  {
    String choice;
    System.out.print(messageToBeShow);
    choice = input.nextLine();
    while(!choice.equals("I") && !choice.equals("F") && !choice.equals("L") && !choice.equals("P") && !choice.equals("A"))
    {
      System.out.println("[!] Incorrect option.");
      System.out.print(messageToBeShow);
      choice = input.nextLine();
    }
    return choice;
  }

}
