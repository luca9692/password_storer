package interfaces;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;
import xml_handlers.Initializer;

import java.util.List;

/**
 * This is the command line interface.
 * It has everything to store, modify and delete passwords.
 * I hope to change it with a GUI ASAP.
 *
 * @author Luca Mortillaro
 */

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
            .nargs(3)
            .help("Modify user and/or password associated to a certain ID");

        group.addArgument("-r", "--remove")
            .metavar("SERVICE_ID")
            .nargs(1)
            .help("Remove the password associated to a given ID. Use print to see IDs and Services");

        Namespace ns;
        try {
            ns = parser.parseArgs(args);
            System.out.println(ns);
            String secure = (ns.getString("secure"));
            Initializer handle = new Initializer(secure);
            if(ns.getString("print").equals("true")){
                handle.printer();
            } else if(ns.getString("encrypt") != null){
                List new_stuff = ns.getList("encrypt");
                handle.add_new_instance(
                        new_stuff.get(0).toString(), // SERVICE NAME
                        new_stuff.get(1).toString(), // USERNAME or EMAIL
                        new_stuff.get(2).toString()); // PASSWORD
            } else if(ns.getString("decrypt") != null){
                handle.decrypt(Integer.parseInt(ns.getList("decrypt").get(0).toString()));
            } else if(ns.getString("modify") != null){
                List new_stuff = ns.getList("modify");
                handle.modify_instance(
                        Integer.parseInt(new_stuff.get(0).toString()), // ID
                        new_stuff.get(1).toString(), // USERNAME or EMAIL
                        new_stuff.get(2).toString()); // PASSWORD
            } else if(ns.getString("remove") != null){
                handle.remove_instance(Integer.parseInt(ns.getList("remove").get(0).toString()));
            }



        } catch (
        ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }


    }
}
