import interfaces.Cli;

public class Main {

    public static void main(String[] args) {
        new Cli().parse(args);
        /*
        encryptors.Encrypt encoder = new encryptors.Encrypt("ciao", "F3rr4@@%pol");
        String s = encoder.encrypt_password();
        System.out.println(s);

        encryptors.Decrypt dec = new encryptors.Decrypt("XBQJIz9OPbsNdiETTESKdQ==", "F3rr4@@%pol");
        String z = dec.decrypt_password();
        System.out.println(z);

         */
    }


}
