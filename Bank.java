import java.util.List;
import java.util.ArrayList;

class Bank
{
  private static List<Client> clients = new ArrayList<Client>();

  public static void main(String[] args)
  {
    if(args.length != 1)
    {
      usage();
      System.exit(1);
    }
    DatabaseHandler bankDatabase = new DatabaseHandler(args[0], clients);
    Menu mainMenu = new Menu(bankDatabase, clients);
    System.exit(0);
  }

  public static void usage()
  {
    System.out.println("Usage: ");
  }


}
