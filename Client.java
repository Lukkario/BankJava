class Client
{
  private String firstName;
  private String lastName;
  private String pesel;
  private String address;
  private double balance;
  private int id;

  Client()
  {
    firstName = "";
    lastName = "";
    pesel = "";
    address = "";
    balance = 0.0;
  }

  Client(int clientId,String clientFirstName, String clientLastName, String clientPesel, String clientAddress, double clientBalance)
  {
    id = clientId;
    firstName = clientFirstName;
    lastName = clientLastName;
    pesel = clientPesel;
    address = clientAddress;
    balance = clientBalance;
  }

  public void setId(int setClientId)
  {
    id = setClientId;
  }

  public void setFirstName(String setClientFirstName)
  {
    firstName = setClientFirstName;
  }

  public void setLastName(String setClientLastName)
  {
    lastName = setClientLastName;
  }

  public void setPesel(String setClientPesel)
  {
    pesel = setClientPesel;
  }

  public void setAddress(String setClientAddress)
  {
    address = setClientAddress;
  }

  public void setBalance(double setClientBalance)
  {
    balance = setClientBalance;
  }

  public String toString()
  {
    return "Id: " + id + "\nFirst name: " + firstName +"\nLast Name: " + lastName + "\nPesel: " + pesel + "\nAddress: " + address + "\nBalance: " + balance + "\n";
  }

  public int getId()
  {
    return id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getPesel()
  {
    return pesel;
  }

  public String getAddress()
  {
    return address;
  }

  public double getBalance()
  {
    return balance;
  }

}
