package webservice.rest;

import webservice.model.City;
import webservice.model.CityManager;
import webservice.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
/**
 * This class represent myClient with its
 * <ul>
 * <li></li>
 * </ul>
 *
 */
public class MyClient {
    //TODO : doc all
    private Service service;
    private JAXBContext jc;
    // Two constants
    private static final QName qname = new QName("", "");
    private static final String url = "http://127.0.0.1:8084";
    /**
     * Construct a new MyClient
     */
    public MyClient() {
        try {
            jc = JAXBContext.newInstance(CityManager.class, City.class,
                    Position.class);
        } catch (JAXBException je) {
            System.out.println("Cannot create JAXBContext " + je);
        }
    }
    /**
     * Search a city from the position
     * @param position
     * The position of the city
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void searchForCity(Position position) throws JAXBException {
        service = Service.create(qname);
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url);
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "POST");
        Source result = dispatcher.invoke(new JAXBSource(jc, position));
        printSource(result);
    }
    /**
     * Search cities around a position
     * @param position
     * The position to look around
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void searchNearCity(Position position) throws JAXBException {
        service = Service.create(qname);
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url + "/near");
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "POST");
        Source result = dispatcher.invoke(new JAXBSource(jc, position));
        printSource(result);
    }
    /**
     * Add a city
     * @param city
     * City to add
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void addCity(City city) throws JAXBException {
        service = Service.create(qname);
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url);
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "PUT");
        Source result = dispatcher.invoke(new JAXBSource(jc, city));
        printSource(result);
    }
    /*
    I don't know why but the source is always received null so i built the city to find in the URL
    */
    /**
     * Remove a City
     * @param city
     * The city to remove
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void removeCity(City city) throws JAXBException {
        service = Service.create(qname);
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url + "/" + city.getCountry() + "/" + city.getName() + "/" + city.getPosition().getLongitude() + "/" + city.getPosition().getLatitude());
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "DELETE");
        Source result = dispatcher.invoke(new JAXBSource(jc, city));
        printSource(result);
    }
    /**
     * Remove all city
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void removeAllCity() throws JAXBException {
        service = Service.create(qname);
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url + "/all");
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "DELETE");
        Source result = dispatcher.invoke(new JAXBSource(jc, new City()));
        printSource(result);
    }
    /*
    I don't know why but the source is always received null so i built the city to find in the URL
    */
    /**
     * Get all cities
     * @param cities
     * the cities we want
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void getCities(List<String> cities) throws JAXBException {
        service = Service.create(qname);
        String url = this.url;
        if(cities.isEmpty()){
            url += "/all";
        } else {
            for (String s : cities) {
                url += "/" +s;
            }
        }
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url);
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "GET");
        Source result = dispatcher.invoke(new JAXBSource(jc, new City()));
        printSource(result);
    }
    /**
     * Get all cities
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void getAllCities() throws JAXBException {
        service = Service.create(qname);
        String url = this.url;
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url);
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "GET");
        Source result = dispatcher.invoke(new JAXBSource(jc, new City()));
        printSource(result);
    }
    /**
     * Print the source result
     * @param s
     * The source to print
     */
    public void printSource(Source s) {
        try {
            System.out.println("============================= Response Received =========================================");
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(s, new StreamResult(System.out));
            System.out.println("\n======================================================================");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Main method
     * @param args
     * The arguments of the main method
     * @throws Exception
     * Can throw Exception
     */
    public static void main(String args[]) throws Exception {
        /**
         * We use a scenario to test all methods
         */
        /*
        - Affichez l'ensemble des villes
        - Supprimez toute les villes
        - Affichez l'ensemble des villes
        - Ajoutez Rouen en France (Lat : 49.443889; Long : 1.103333)
        - Ajoutez Mogadiscio en Somalie (lat : 2.333333; Long : 48.85)
        - Ajoutez Rouen en France (Lat : 49.443889; Long : 1.103333)
        - Ajoutez Bihorel en France (Lat : 49.455278; Long : 1.116944)
        - Ajoutez Londres en Angleterre (Lat : 51.504872; Long : -0.07857)
        - Ajoutez Paris en France (Lat : 48.856578; Long : 2.351828)
        - Ajoutez Paris en Canada (Lat : 43.2; Long : -80.38333)
        - Affichez l'ensemble des villes
        - Ajoutez Villers-Bocage en France (Lat : 49.083333; Long : -0.65)
        - Ajoutez Villers-Bocage en France (Lat : 50.021858; Long : 2.326126)
        - Affichez l'ensemble des villes
        - Supprimez Villers-Bocage en France (Lat : 49.083333; Long : -0.65)
        - Affichez l'ensemble des villes
        - Supprimez Londres en Angleterre (Lat : 51.504872; Long : -0.07857)
        - Supprimez Londres en Angleterre (Lat : 51.504872; Long : -0.07857)
        - Affichez la ville située a la position exacte (Lat :49.443889; Long : 1.103333
        - Affichez la ville située a la position exacte (Lat :49.083333; Long : -0.65
        - Affichez la ville située a la position exacte (Lat :43.2; Long : -80.38)
        - Affichez les villes situées à 10km de la position (Lat : 48.85; Long : 2.34)
        - Affichez les villes situées à 10km de la position (Lat :42; Long : 64)
        - Affichez les villes situées à 10km de la position (Lat :49.95; 1.11)
        - Affichez la(les) ville(s) nommée(s) "Mogadiscio"
        - Affichez la(les) ville(s) nommée(s) "Paris"
        - Affichez la(les) ville(s) nommée(s) "Hyrule"
        +- Supprimez la(les) ville(s) nommée(s) "Mogadiscio" ou "Paris ou "Hyrule"
        - Supprimez toutes les villes
        - Affichez l'ensemble des villes
        */
        MyClient client = new MyClient();
        System.out.println("get all");
        client.getAllCities();
        System.out.println("del all");
        client.removeAllCity();
        System.out.println("get all");
        client.getAllCities();
        City city = new City("Rouen",49.443889,1.103333,"FR");
        System.out.println("ADD --- \"Rouen\",49.443889,1.103333,\"FR\"");
        client.addCity(city);
        city = new City("Mogadiscio",49.443889,1.103333,"SO");
        System.out.println("ADD --- \"Mogadiscio\",49.443889,1.103333,\"SO\"");
        client.addCity(city);
        city = new City("Rouen",49.443889,1.103333,"FR");
        System.out.println("ADD --- \"Rouen\",49.443889,1.103333,\"FR\"");
        client.addCity(city);
        city = new City("Bihorel",49.455278,1.116944,"FR");
        System.out.println("ADD --- \"Bihorel\",49.455278,1.116944,\"FR\"");
        client.addCity(city);
        city = new City("Londres",51.504872,-0.07857,"EN");
        System.out.println("ADD --- \"Londres\",51.504872,-0.07857,\"EN\"");
        client.addCity(city);
        city = new City("Paris",48.856578,2.351828,"FR");
        System.out.println("ADD --- \"Paris\",48.856578,2.351828,\"FR\"");
        client.addCity(city);
        city = new City("Paris",43.2, -80.38333,"CA");
        System.out.println("ADD --- \"Paris\",43.2, -80.38333,\"CA\"");
        client.addCity(city);
        System.out.println("get all");
        client.getAllCities();
        city = new City("Villers-Bocage",49.083333, -0.65,"FR");
        System.out.println("ADD --- \"Villers-Bocage\",49.083333, -0.65,\"FR\"");
        client.addCity(city);
        city = new City("Villers-Bocage",50.021858, 2.326126,"FR");
        System.out.println("ADD --- \"Villers-Bocage\",50.021858, 2.326126,\"FR\"");
        client.addCity(city);
        System.out.println("get all");
        client.getAllCities();
        city = new City("Villers-Bocage",49.083333, -0.65,"FR");
        System.out.println("DEL --- \"Villers-Bocage\",49.083333, -0.65,\"FR\"");
        client.removeCity(city);
        System.out.println("get all");
        client.getAllCities();
        city = new City("Londres",51.504872,-0.07857,"EN");
        System.out.println("DEL --- \"Londres\",51.504872,-0.07857,\"EN\"");
        client.removeCity(city);
        city = new City("Londres",51.504872,-0.07857,"EN");
        System.out.println("DEL --- \"Londres\",51.504872,-0.07857,\"EN\"");
        client.removeCity(city);
        System.out.println("search at 49.443889, 1.103333");
        client.searchForCity(new Position(49.443889, 1.103333));
        System.out.println("search at 49.083333, -0.65");
        client.searchForCity(new Position(49.083333, -0.65));
        System.out.println("search at 43.2, -80.38");
        client.searchForCity(new Position(43.2, -80.38));
        System.out.println("searchNear 48.85, 2.34");
        client.searchNearCity(new Position(48.85, 2.34));
        System.out.println("searchNear 42, 64");
        client.searchNearCity(new Position(42, 64));
        System.out.println("searchNear 49.95, 1.11");
        client.searchNearCity(new Position(49.95, 1.11));
        List<String> names = new ArrayList<String>();
        System.out.println("search : Mogadiscio");
        names.add("Mogadiscio");
        client.getCities(names);
        names.clear();
        System.out.println("search : Paris");
        names.add("Paris");
        client.getCities(names);
        names.clear();
        System.out.println("search : Hyrule");
        names.add("Hyrule");
        client.getCities(names);
        names.clear();
        //Upgrades : we can search a list of names
        System.out.println("search : Mogadiscio, Paris, Hyrule");
        names.add("Mogadiscio");
        names.add("Paris");
        names.add("Hyrule");
        client.getCities(names);
        System.out.println("del all");
        client.removeAllCity();
        System.out.println("get all");
        client.getAllCities();
    }
}
