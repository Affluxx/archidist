package eip;


import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class ProducerConsumer {
    public static void main(String[] args ) throws Exception {
        //Configure the logger
        BasicConfigurator.configure();

        //Camel Context
        CamelContext context = new DefaultCamelContext();

        //Create a road who containt the consumer
        RouteBuilder routeBuilder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                //We define a consumer 'consumer-1'
                // and it's gona write the message
                from("direct:consumer-1").to("log:affiche-1-log");
                from("direct:consumer-2").to("file:messages");
                from("direct:route1").choice().when(header("entete").isEqualTo("ecrire")).to("direct:consumer-2").otherwise().to("direct:consumer-1");
            }
        };

        //add the road to the context
        routeBuilder.addRoutesToCamelContext(context);
        //Start the context to activate the road
        context.start();
        //create a producer
        ProducerTemplate pt = context.createProducerTemplate();
        //who send a message to the consumer 'consumer-1'
        //pt.sendBody("direct:consumer-1", "Hello World !");

        /**
         * La partie avec le Scanner
         */
        Scanner scanner = new Scanner(System.in);
        /**
         * Ici on peux envoyer une chaine depuis l'entrée standard. Si cette chaine est exit on ferme.
         * //String s = scanner.nextLine();
            //if (s.equals("exit")) {
            //System.exit(0);
            //}
            //pt.sendBody("direct:consumer-1", s);
         */


        /**
         * Ici on peux envoyer des messages depuis l'entrée. Si la chaine est exit on ferme
         */
        String s = scanner.nextLine();
        while (!s.equals("exit")) {
            pt.sendBody("direct:consumer-1",s);
            s = scanner.nextLine();
        }
        System.exit(0);
    }
}
