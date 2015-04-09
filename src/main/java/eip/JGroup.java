package eip;

import org.jgroups.Message;


/**
 * Class use to Create a JGroup
 */
public class JGroup {
    /**
     * Receive a message and display it
     * @param msg message received
     */
    public void receive(Message msg) {
        String message = (String) msg.getObject();

        if (message.equals("ADFTW")) {
            System.out.println("Reçu le mot-clé : " + message);
        } else {
            System.out.println("Message reçu \"" + message + "\"");
        }
    }
}
