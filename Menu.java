import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Menu
{
  DatabaseHandler database = null;
  private List<Client> accounts = null;

  Menu(DatabaseHandler databaseHandler, List<Client> clients)
  {
    database = databaseHandler;
    accounts = clients;
    startMenu();
  }

  public void startMenu()
  {
    Scanner userInput = new Scanner(System.in);
    Operations bankOperations = new Operations(database, accounts, userInput);
    while(true)
    {
      System.out.print("> ");
      switch (userInput.nextLine()) {
        case "a":
          bankOperations.addClient();
          // System.out.println("a");
          break;
        case "e":
          bankOperations.deleteClient();
          //System.out.println("e");
          break;
        case "d":
          bankOperations.depositMoney();
          //System.out.println("d");
          break;
        case "w":
          bankOperations.withdrawMoney();
          //System.out.println("w");
          break;
        case "t":
          bankOperations.transferMoney();
          //System.out.println("t");
          break;
        case "s":
          // System.out.println("s");
          bankOperations.showClients();
          break;
        case "p":
          printSubMenu();
          bankOperations.showSpecificClients();
          //System.out.println("p");
          break;
        case "h":
          printMenu();
          break;
        case "q":
          database.saveDatabase();
          System.exit(0);
          break;
        default:
          System.out.println("Not such option. Type h to get help.");
          break;
      }
    }
  }

  private void printMenu()
  {
    System.out.println("a - [a]dds account to database");
    System.out.println("d - [d]eposit money");
    System.out.println("w - [w]ithdraw money");
    System.out.println("t - [t]ransfer moeny between accounts");
    System.out.println("s - [s]hows details of all accounts");
    System.out.println("e - d[e]letes account from database");
    System.out.println("p - shows details of s[p]ecific accounts");
    System.out.println("h - shows [h]elp menu");
    System.out.println("q - [q]uits program");
  }

  private void printSubMenu()
  {
    System.out.println("I - [I]D");
    System.out.println("F - [F]irst name");
    System.out.println("L - [L]ast name");
    System.out.println("P - [P]esel");
    System.out.println("A - [A]ddress");
  }
}
