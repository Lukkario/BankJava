import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

class DatabaseHandler
{
  private List<Client> clients = null;
  private File database = null;
  private String loaclDatabaseName = "";

  DatabaseHandler(String databaseFileName, List<Client> clientList)
  {
    clients = clientList;
    loaclDatabaseName = databaseFileName;
    loadDatabase();
  }

  private void loadDatabase()
  {
    int temporaryId;
    String temporaryFirstName;
    String temporaryLastName;
    String temporaryPesel;
    String temporaryAddress;
    double temporaryBalance;

    database = new File(loaclDatabaseName);

    if(database.exists() && !database.isDirectory())
    {
      try
      {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(database);

        doc.getDocumentElement().normalize();

  	    NodeList nList = doc.getElementsByTagName("client");

  	    for (int temp = 0; temp < nList.getLength(); temp++)
        {
          Node nNode = nList.item(temp);
          if (nNode.getNodeType() == Node.ELEMENT_NODE)
          {
            Element eElement = (Element) nNode;
            temporaryId = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
            temporaryFirstName = eElement.getElementsByTagName("firstname").item(0).getTextContent();
            temporaryLastName = eElement.getElementsByTagName("lastname").item(0).getTextContent();
            temporaryPesel = eElement.getElementsByTagName("pesel").item(0).getTextContent();
            temporaryAddress = eElement.getElementsByTagName("address").item(0).getTextContent();
            temporaryBalance = Double.parseDouble(eElement.getElementsByTagName("balance").item(0).getTextContent());
            clients.add(new Client(temporaryId, temporaryFirstName, temporaryLastName, temporaryPesel, temporaryAddress, temporaryBalance));
          }
        }
      }
      catch(Exception e)
      {
        System.out.println("Error occured while loading database.");
      }
    }
    else
    {
      try
      {
        database.createNewFile();
      }
      catch(Exception e)
      {
        System.out.println("Could not create file.");
      }
    }

  }


  public void saveDatabase()
  {
    try
    {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      Element rootElement = doc.createElement("bankdata");
      doc.appendChild(rootElement);

      clients.forEach(client ->
      {
          Element xmlClient = doc.createElement("client");
          rootElement.appendChild(xmlClient);

          Element id = doc.createElement("id");
          id.appendChild(doc.createTextNode(Integer.toString(client.getId())));
          xmlClient.appendChild(id);

          Element firstName = doc.createElement("firstname");
          firstName.appendChild(doc.createTextNode(client.getFirstName()));
          xmlClient.appendChild(firstName);

          Element lastName = doc.createElement("lastname");
          lastName.appendChild(doc.createTextNode(client.getLastName()));
          xmlClient.appendChild(lastName);

          Element pesel = doc.createElement("pesel");
          pesel.appendChild(doc.createTextNode(client.getPesel()));
          xmlClient.appendChild(pesel);

          Element address = doc.createElement("address");
          address.appendChild(doc.createTextNode(client.getAddress()));
          xmlClient.appendChild(address);

          Element balance = doc.createElement("balance");
          balance.appendChild(doc.createTextNode(Double.toString(client.getBalance())));
          xmlClient.appendChild(balance);

          try
          {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(loaclDatabaseName));
            transformer.transform(source, result);
          }
          catch(TransformerException tfe)
          {
            tfe.printStackTrace();
          }

      }
      );

    }
    catch(Exception e)
    {
      e.getMessage();
    }
  }
}
