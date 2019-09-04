package encryptors;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Encrypt {

    private String in_text;
    private String password;
    private String encrypted;

    public Encrypt(String in_text, String password) {
        this.in_text = in_text;
        this.password = password;
    }

    public String encrypt_password(){
        StandardPBEStringEncryptor encoder = new StandardPBEStringEncryptor();
        encoder.setPassword(this.password);
        this.encrypted = encoder.encrypt(this.in_text);
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
