package xml_handlers;

/**
 * This is the class which represent all the fields of each service
 * saved inside the XML
 *
 * @author Luca Mortillaro
 *
 */
public class ItemInstance {

    private String service_name;
    private String username;
    private String encrypted_password;

    public ItemInstance(String service_name, String username, String encrypted_password){
        this.service_name = service_name;
        this.username = username;
        this.encrypted_password = encrypted_password;

    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }
}
