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

    clients.forEach(client ->
    {
      System.out.println(client.getId());
      System.out.println(client.getFirstName());
      System.out.println(client.getLastName());
      System.out.println(client.getAddress());
      System.out.println(client.getPesel());
      System.out.println(client.getBalance());
    }
    );

    clients.add(new Client(1, "XD", "XD", "XD", "XD", 12.0));

    bankDatabase.saveDatabase();
  }

  public static void usage()
  {
    System.out.println("Usage: ");
  }


}
