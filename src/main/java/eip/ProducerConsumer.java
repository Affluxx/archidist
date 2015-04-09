package eip;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.BasicConfigurator;
import org.jgroups.JChannel;
import org.jgroups.Message;

import java.util.Scanner;

public class ProducerConsumer {
    public static void main(String[] args ) throws Exception {
        final String CONSUMER1 = "direct:consumer-1";
        final String CONSUMER2 = "direct:consumer-2";
        final String LOG_ENDPOINT = "log:afficher-1-log";
        final String FILE_ENDPOINT = "file:messages";
        final String WRITE = "ecrire";
        final String END = "exit";
        final String ROAD = "direct:consumer-all";
        final String IP = "http://217.0.0.1.8084/all";
        final String REP = "reponse received : ${body}";
        final JChannel channel = new JChannel();
        //Configure the logger
        BasicConfigurator.configure();

        //Camel Context
        CamelContext context = new DefaultCamelContext();

        //Create a road who containt the consumer
        RouteBuilder consumer1RouteBuilder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                //We define a consumer 'consumer-1'
                // and it's gona write the message
                from(CONSUMER1).to(LOG_ENDPOINT);



            }
        };
        //Create the road who contains the consumer2
        RouteBuilder consumer2RouteBuilder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(CONSUMER2).to(FILE_ENDPOINT);
            }
        };
        // Create the road
        RouteBuilder routeBuilder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(ROAD).choice().when(header("destinataire").isEqualTo(WRITE)).to(CONSUMER2).otherwise().to(CONSUMER1);
            }
        };

        RouteBuilder httpRoad = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:CityManager")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to(IP)
                .log(REP);
            }
        };
        RouteBuilder geoRoad = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:geo")
                        .recipientList(simple("http://api.geonames.org/search?q=${body}&username=m1gil"))
                        .log("response received : ${body}");
            }
        };

        //add the road to the context
        consumer1RouteBuilder.addRoutesToCamelContext(context);
        consumer2RouteBuilder.addRoutesToCamelContext(context);
        routeBuilder.addRoutesToCamelContext(context);
        httpRoad.addRoutesToCamelContext(context);
        geoRoad.addRoutesToCamelContext(context);
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
        /**
        String s = scanner.nextLine();
        HashMap header = new HashMap();
        while (!s.equals(END)) {
            if (s.startsWith("w")) {
                header.put("destinataire", "ecrire");
            }
            pt.sendBodyAndHeaders(ROAD,s,header);
            s = scanner.nextLine();
            header.clear();
        }
        System.exit(0);
         */
        /**
         * This part is for the exercise 2
         */

        String s;
        while (scanner.hasNext()) {
            s = scanner.next();
            // Exercise 1 :
            if (s.equals(END)) {
                System.exit(0);
            } else {
                //Exercise 2
                //pt.sendBody(ROAD, s);

                //Exercise 4
                Message transit = new Message(null, null, s);
                channel.send(transit);

            }
        }


        /**
         * This part is for the exercise 3
         */
        pt.sendBody("direct:geo", "london");

        /**
         * This part is for the exercise 4
         */

        context.stop();
    }
}
