package webservice.rest;

import webservice.model.City;
import webservice.model.CityManager;
import webservice.model.HTTPMethod;
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
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
/**
 * This class represent a Client
 *
 */
public class MyClient {
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
     * Here we have the Post Method
     */
    /**
     * Search a city from the position
     * @param position : The position of the city
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void searchForCity(Position position) throws JAXBException {
        executeRequest(HTTPMethod.POST,null,position);
    }
    /**
     * Search cities around a position
     * @param position
     * The position to look around
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void searchNearCity(Position position) throws JAXBException {
        executeRequest(HTTPMethod.POST,url + "/near",position);
    }

    /**
     * Here we have the Put Method
     */
    /**
     * Add a city
     * @param city
     * City to add
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void addCity(City city) throws JAXBException {
        executeRequest(HTTPMethod.PUT,null,city);
    }

    /**
     * Here we have the Delete method
     */
    /**
     * Remove a City
     * @param city
     * The city to remove
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void removeCity(City city) throws JAXBException {
        executeRequest(
                HTTPMethod.DELETE,
                url + "/" + city.getName()
                        + "/" + city.getPosition().getLatitude()
                        + "/" + city.getPosition().getLongitude()
                        + "/" + city.getCountry(),
                null
        );
    }
    /**
     * Remove all city
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void removeAllCity() throws JAXBException {
        executeRequest(HTTPMethod.DELETE, url + "/all", null);
    }

    /**
     * Here we have the Get Method
     */

    /**
     * Return the cities with the name
     * @param name : the name of the city
     * @throws JAXBException : can throw a JAXBException
     */
    public void getCitiesByName(String name) throws JAXBException {
        executeRequest(HTTPMethod.GET, url + "/" + name, null);
    }

    /**
     * Get all cities
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void getAllCities() throws JAXBException {
        getCitiesByName("all");
    }

    /**
     * Get all cities
     * @param cities
     * the cities we want
     * @throws JAXBException
     * Can throw a JAXBException
     */
    public void getCities(List<String> cities) throws JAXBException {
        String url = this.url;
        if(cities.isEmpty()){
            getCitiesByName("all");
        } else {
            for (String name: cities) {
                getCitiesByName(name);
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
     * In the client we have a part of code who always come back.
     * So we decide to factoring it in this method.
     */

    /**
     * Execute the http request on the url
     * @param method : the http method to execute
     * @param url the url to execute the method (opt)
     * @param obj the object to send (opt)
     * @throws JAXBException
     */
    private void executeRequest(HTTPMethod method, String url, Object obj) throws JAXBException {

        if (method == null) {
            throw new IllegalArgumentException();
        }

        if (url == null || url.isEmpty()) {
            url = this.url;
        }

        if (obj == null) {
            obj = new CityManager();
        }

        service = Service.create(qname);
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url);
        Dispatch<Source> dispatcher = service.createDispatch(qname,
                Source.class, Service.Mode.MESSAGE);
        Map<String, Object> requestContext = dispatcher.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, method.getMethod());
        Source result = dispatcher.invoke(new JAXBSource(jc, obj));
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
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING, new MyServiceTP());
        e.publish("http://127.0.0.1:8084/");

        MyClient client = new MyClient();
        System.out.println("get all");
        client.getAllCities();

        System.out.println("del all");
        client.removeAllCity();

        System.out.println("get all");
        client.getAllCities();

        System.out.println("Add Rouen");
        client.addCity(new City("Rouen", 49.443889, 1.103333, "FR"));


        System.out.println("Add Mogadiscio");
        client.addCity(new City("Mogadiscio", 49.443889, 1.103333, "SO"));


        System.out.println("Add Rouen");
        client.addCity(new City("Rouen", 49.443889, 1.103333, "FR"));


        System.out.println("Add Bihorel");
        client.addCity(new City("Bihorel", 49.455278, 1.116944, "FR"));

        System.out.println("Add Londres");
        client.addCity(new City("Londres", 51.504872, -0.07857, "EN"));

        System.out.println("Add Paris");
        client.addCity(new City("Paris", 48.856578, 2.351828, "FR"));

        System.out.println("ADD --- \"Paris\",43.2, -80.38333,\"CA\"");
        client.addCity(new City("Paris", 43.2, -80.38333, "CA"));

        System.out.println("get all");
        client.getAllCities();

        System.out.println("Ass Villers-bocage");
        client.addCity(new City("Villers-Bocage", 49.083333, -0.65, "FR"));

        System.out.println("Add Villers-Bocage");
        client.addCity(new City("Villers-Bocage", 50.021858, 2.326126, "FR"));

        System.out.println("get all");
        client.getAllCities();

        System.out.println("Del Villers-Bocage");
        client.removeCity(new City("Villers-Bocage", 49.083333, -0.65, "FR"));

        System.out.println("get all");
        client.getAllCities();

        System.out.println("Del Londres");
        client.removeCity(new City("Londres", 51.504872, -0.07857, "EN"));

        System.out.println("Del Londres");
        client.removeCity(new City("Londres", 51.504872, -0.07857, "EN"));

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

        System.out.println("search : Mogadiscio");
        client.getCitiesByName("Mogadiscio");
        System.out.println("search : Paris");
        client.getCitiesByName("Paris");
        System.out.println("search : Hyrule");
        client.getCitiesByName("Hyrule");
        //Upgrades : we can search a list of names
        List<String> names = new ArrayList<String>();
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
