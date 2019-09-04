package xml_handlers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class initializes the file inside the project and
 * does all the preliminary stuff before calling the delegated function.
 *
 * @author Luca Mortillaro
 */
public class Initializer {
    protected String author = "Luca Mortillaro";
    public String xml_name = "jasypt_encryptor.xml";
    private String secure;
    private Map<Integer, ItemInstance> saved_passwords = new HashMap<Integer, ItemInstance>();

    public Initializer(String secure) {
        this.secure = secure;
        this.load_map();
        this.write_map();
    }

    /**
     * API to convert the hashmap to the xml.
     * Prefer this to an external service :)
     */
    private void write_map() {
        StringBuilder sb = new StringBuilder("<root>\n");


        for (Map.Entry<Integer, ItemInstance> entry : saved_passwords.entrySet()) {
            sb.append("<service id=\"" + entry.getKey() + "\">\n" +
                    "        <name>" + entry.getValue().getService_name() + "</name>\n" +
                    "        <username>" + entry.getValue().getUsername() + "</username>\n" +
                    "        <password>" + entry.getValue().getEncrypted_password() + "</password>\n" +
                    "    </service>");

        }

        sb.append("</root>");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(xml_name));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }







    }

    /**
     * Load the xml file inside an HashMap structure
     * allowing to modify it natively with Java
     */
    private void load_map() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("test.xml");   // Read the document

            NodeList nodeList = document.getElementsByTagName("service"); // Get all service nodes from xml

            for (int i = 0; i < nodeList.getLength(); i++) {   //generates the hashmap with id and iteminstance

                Node node = nodeList.item(i);
                int temp_id = -1;
                String temp_service_name = "";
                String temp_username = "";
                String temp_password = "";

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList childNodes = node.getChildNodes();
                    temp_id = Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent());

                    for (int j=0; j<childNodes.getLength(); j++){

                        String nodename = childNodes.item(j).getNodeName();
                        if(nodename.equals("name")){
                            temp_service_name = childNodes.item(j).getTextContent();
                        }else if (nodename.equals("username")){
                            temp_username = childNodes.item(j).getTextContent();
                        }else if (nodename.equals("password")){
                            temp_password = childNodes.item(j).getTextContent();
                        }

                    }

                }
                ItemInstance temp_instance = new ItemInstance(temp_service_name, temp_username, temp_password);
                saved_passwords.put(temp_id, temp_instance);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("File not found 1");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File not found 2");
            e.printStackTrace();
        }
    }


    public void printer(){
        System.out.println("\nI tuoi dati-------------------------");
        System.out.printf("%-8s %-25s %-40s %s\n", "ID", "Service name", "Username", "Password");
        for (Map.Entry<Integer, ItemInstance> entry : saved_passwords.entrySet()) {
        System.out.printf("%-8d %-25s %-40s %s\n",
                entry.getKey(), entry.getValue().getService_name(),
                entry.getValue().getUsername(), entry.getValue().getEncrypted_password());

        }


    }


}
