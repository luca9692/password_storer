package interfaces;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;
import xml_handlers.Initializer;

public class Cli {

    public void parse(String[] args){

        final ArgumentParser parser = ArgumentParsers.newFor("XML_Password_Encryptor")
            .addHelp(true)
            .build()
            .description("encryptors.Encrypt and encryptors.Decrypt stuff into an XML file");
        parser.addArgument("secure").help("Encription string for the XML");

        MutuallyExclusiveGroup group = parser.addMutuallyExclusiveGroup().required(true);

        group.addArgument("-p", "--print")
            .action(Arguments.storeTrue())
            .help("Print a list of all stored password and services");

        group.addArgument("-e", "--encrypt")
            .metavar("SERVICE_NAME", "USER_NAME_EMAIL", "PLAINTEXT_PASSWORD")
            .nargs(3)
            .help("Gets two arguments: website or service, username or email, and password");

        group.addArgument("-d", "--decrypt")
            .metavar("SERVICE_ID")
            .nargs(1)
            .help("encryptors.Decrypt the password associated to a given ID. Use print to see IDs and Services");

        group.addArgument("-m", "--modify")
            .metavar("ID", "USER_NAME_EMAIL", "PLAINTEXT_PASSWORD")
            .nargs(2)
            .help("Modify user and/or password associated to a certain ID");

        group.addArgument("-r", "--remove")
            .metavar("SERVICE_ID")
            .nargs(1)
            .help("Remove the password associated to a given ID. Use print to see IDs and Services");

        Namespace ns;
        try {
            ns = parser.parseArgs(args);
            System.out.println(ns);
            //TODO check secure
            String secure = (ns.getString("secure"));
            Initializer handle = new Initializer(secure);
            if(ns.getString("print").equals("true")){
                System.out.print("Print Completed");
                handle.printer();
            } else if(ns.getString("encrypt") != null){
                System.out.print("encrypt");
            } else if(ns.getString("decrypt") != null){
                System.out.print("dec");
            } else if(ns.getString("modify") != null){
                System.out.print("mod");
            } else if(ns.getString("remove") != null){
                System.out.print("rem");
            }



        } catch (
        ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }


    }
}
