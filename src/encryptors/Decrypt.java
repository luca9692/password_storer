package encryptors;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Decrypt {

    private String in_text;
    private String password;
    private String encrypted;

    public Decrypt(String in_text, String password) {
        this.in_text = in_text;
        this.password = password;
    }

    public String decrypt_password(){
        StandardPBEStringEncryptor decoder = new StandardPBEStringEncryptor();

        decoder.setPassword(this.password);
        this.encrypted = decoder.decrypt(this.in_text);
        return this.encrypted;
    }

    public String getIn_text() {
        return in_text;
    }

    public void setIn_text(String in_text) {
        this.in_text = in_text;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    @Override
    public String toString() {
        return "encryptors.Encrypt{}";
    }




}
