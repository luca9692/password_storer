package xml_handlers;

import encryptors.Decrypt;
import encryptors.Encrypt;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.lang3.StringEscapeUtils;

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
    public String xml_name = "jasypt_encryptor.xml";
    private String secure;
    private Map<Integer, ItemInstance> saved_passwords = new HashMap<Integer, ItemInstance>();

    public Initializer(String secure) {
        this.secure = secure;
        this.load_map();
        if (!check_secure()){
            System.out.println("Wrong password, closing");
            System.exit(0);
        }
    }



    /**
     *
     * @param service_name the name of the service you are saving
     * @param user_name_email the username or email associated to the service
     * @param plaintext_password the password associated to the service
     */

    public void add_new_instance(String service_name, String user_name_email, String plaintext_password) {
        int random_int = 1; // Random int initialization
        int max_iteration = 2500;
        while (saved_passwords.containsKey(random_int)){  //Searching firs int not already mapped to a service
            random_int++;
            if(random_int > max_iteration){
                System.out.println("You are saving too many password for this little program :(");
                System.exit(0);
            }
        }
        String encrypted_password = new Encrypt(plaintext_password, this.secure).encrypt_password();
        ItemInstance new_instance = new ItemInstance(service_name, user_name_email, encrypted_password);
        saved_passwords.put(random_int, new_instance);
        write_map();
        System.out.println("New instance correctly added!");
    }

    /**
     * Print plaintext password for the requeste ID
     * @param id the ID of the service that need to be decrypted
     */
    public void decrypt(int id) {

        if (saved_passwords.containsKey(id)){
            System.out.println("La password è:");
            String plaintext_password = new Decrypt(saved_passwords.get(id).getEncrypted_password(), this.secure).decrypt_password();
            System.out.println(plaintext_password);
        }else{
            System.out.println("Inserted ID not matching any known item");
        }

    }

    /**
     *
     * @param id
     */
    public void modify_instance(int id, String username, String new_password) {
        if (saved_passwords.containsKey(id)){
            ItemInstance temp_instance = saved_passwords.get(id);
            String new_password_encrypted = new Encrypt(new_password, this.secure).encrypt_password();
            temp_instance.setEncrypted_password(new_password_encrypted);
            write_map();
            System.out.println("Modified correctly");
        }else{
            System.out.println("Item not found");
        }
    }

    /**
     *
     * @param  id
     */
    public void remove_instance(int id) {
        if (saved_passwords.containsKey(id)){
            saved_passwords.remove(id);
            write_map();
            System.out.println("Removed correctly");
        }else{
            System.out.println("Item not found");
        }

    }

    /**
     *
     * @return
     */
    private boolean check_secure() {
        if(!saved_passwords.isEmpty()) {
            String test_password;
            for (Map.Entry<Integer, ItemInstance> entry : saved_passwords.entrySet()) {
                test_password = entry.getValue().getEncrypted_password();
                try{
                    new Decrypt(test_password, this.secure).decrypt_password();
                }catch (EncryptionOperationNotPossibleException e){
                    return false;
                }
                break;
            }
        }
        return true;
    }

    /**
     * Load the xml file inside an HashMap structure
     * allowing to modify it natively with Java
     */
    private void load_map() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("jasypt_encryptor.xml");   // Read the document

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
            System.out.println("File not found! Creating it now...");
            this.write_map();
        }
    }

    /**
     *  Print the list of all accounts and passwords
     */
    public void print_all() {
        System.out.println("\nYour Accounts and passwords in cleartext-------------------------");
        System.out.printf("%-8s %-25s %-40s %s\n", "ID", "Service name", "Username", "Password");
        for (Map.Entry<Integer, ItemInstance> entry : saved_passwords.entrySet()) {
            System.out.printf("%-8d %-25s %-40s %s\n",
                    entry.getKey(), entry.getValue().getService_name(),
                    entry.getValue().getUsername(), new Decrypt(entry.getValue().getEncrypted_password(), this.secure).decrypt_password());

        }
    }

    /**
     *  Print the whole document to output
     */
    public void printer(){
        System.out.println("\nYour Accounts and passwords encrypted-------------------------");
        System.out.printf("%-8s %-25s %-40s %s\n", "ID", "Service name", "Username", "Password");
        for (Map.Entry<Integer, ItemInstance> entry : saved_passwords.entrySet()) {
        System.out.printf("%-8d %-25s %-40s %s\n",
                entry.getKey(), entry.getValue().getService_name(),
                entry.getValue().getUsername(), entry.getValue().getEncrypted_password());

        }

    }

    /**
     * API to convert the hashmap to the xml.
     * Prefer this to an external service :)
     */
    private void write_map() {
        StringBuilder sb = new StringBuilder("<root>\n");


        for (Map.Entry<Integer, ItemInstance> entry : saved_passwords.entrySet()) {
            sb.append("    <service id=\"" + entry.getKey() + "\">\n" +
                    "        <name>" +  StringEscapeUtils.escapeXml(entry.getValue().getService_name()) + "</name>\n" +
                    "        <username>" +  StringEscapeUtils.escapeXml(entry.getValue().getUsername()) + "</username>\n" +
                    "        <password>" +  StringEscapeUtils.escapeXml(entry.getValue().getEncrypted_password()) + "</password>\n" +
                    "    </service>\n");

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



}
