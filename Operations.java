import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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

  }

  public void showClients()
  {

    accounts.forEach(client ->
    {
      System.out.println("+-----------------------------------------+");
      System.out.println("| Id:\t\t" + client.getId() + "\t\t |");
      System.out.println("| First name:\t" + client.getFirstName());
      System.out.println("| Last name:\t" + client.getLastName());
      System.out.println("| Address:\t" + client.getAddress());
      System.out.println("| Pesel:\t" + client.getPesel());
      System.out.println("| Balance:\t" + client.getBalance());
      System.out.println("+-----------------------------------------+\n");
    }
    );
    // System.out.println("+-----------------------------------------+");
  }

}
