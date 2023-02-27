package calcultransport;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CalculTransport {

	public static void main(String[] args)throws Exception{
		
		String clientXML = "";
		String conditionTaxationXML ="";
		String localiteXML = "";
		String tarifXML ="";
		
		// Charge les données des clients qui se trouve dans le fichier XML
        // dans un objet de type List de client et affecte une référence à cet
        // objet à la variable listeClient.
		File clientFile = new File(clientXML);
		List<Client> listeClient = loadClientDataFromXml(clientFile);
	
		//On affiche le résultat
		printListeclient(System.out, listeClient);
	}

	private static List<Client> loadClientDataFromXml(File clientXML) throws Exception {
		//Création d'un tableau
		List<Client>  listeClient = new ArrayList<>();
		
		//Crée un objet Document qui représente les données du fichier XML
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder  builder = factory.newDocumentBuilder();
		Document doc = builder.parse(clientXML);
		doc.getDocumentElement().normalize();
		
		//Recherche tous les elements <client>
		NodeList  clientNodeList = doc.getElementsByTagName("client");
		
		//Pour chaque élément XML de  la liste
		for(int i = 0; i < clientNodeList.getLength();i= i +1) {
			Node node = clientNodeList.item(i);
			
			if(node.getNodeType()== Node.ELEMENT_NODE) {
				
				//Récupère l'élément
				Element clientElement = (Element)node;
				
				//Création d'un nouvel objet Client
				Client client = new Client();
				
				client.idClient = Integer.parseInt(clientElement.getAttribute("idClient"));
				client.raisonSociale = clientElement.getElementsByTagName("raidonSociale").item(0).getTextContent();
				client.codePostal = Integer.parseInt(clientElement.getAttribute("codePostal"));
				client.ville = clientElement.getElementsByTagName("ville").item(0).getTextContent();
				
				//Ajoute  la personne à la liste
				listeClient.add(client);
			}
		}
		
		return listeClient;
	}

	//Ecrit les élément d’une liste d'objet de type Client dans le flux de sortie
	//passé  en paramètre
	private static void printListeclient(PrintStream out, List<Client> listeClient) {
		for (int i = 0; i< listeClient.size(); i= i+1) {
			Client client = listeClient.get(i);
			printClient(out, client);
		}
		
	}
	
	 // Ecrit les données d’un objet de type Client dans le flux de sortie
     // passé en paramètre.
	private static void printClient(PrintStream out, Client client) {

		out.printf("(%d),%s,(%d),%s\r\n",
				client.idClient,
				client.raisonSociale,
				client.codePostal,
				client.ville);
		
	}

}
