package main.java.tp.rest;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import main.java.tp.model.City;
import main.java.tp.model.CityManagerService;
import main.java.tp.model.CityNotFound;
import main.java.tp.model.Position;

/**
 * This class represent myClient. It can
 * <ul>
 *     <li></li>
 * </ul>
 *
 */
public class MyClient {
	private Service service;
	private JAXBContext jc;

    private static final QName SERVICE_NAME =
            new QName("http://model.tp.java.main/", "CityManagerService");
    private static final QName PORT_NAME =
            new QName("http://model.tp.java.main/", "CityManagerPort");

    /**
     * The main method
     * @param args
     * The args use by the method
     * @throws Exception
     * Can throw an Exception
     */
	public static void main(String args[]) throws Exception {
        URL wsdlURL = new URL("http://127.0.0.1:8084/citymanager?wsdl");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        CityManagerService cityManager = service.getPort(PORT_NAME, CityManagerService.class);
        /**
        System.out.println(cityManager.getCities());
        City c = new City("Zanarkand", 16,64, "Hyrule");
        cityManager.addCity(c);
        cityManager.addCity(c);
        cityManager.removeCity(c);
        System.out.println(cityManager.getCities());
        **/
        // Scenario to test the new cityManager
        System.out.println(cityManager.getCities());
        cityManager.removeAllCities();
        System.out.println(cityManager.getCities());
        cityManager.addCity(new City("Rouen",49.443889,1.103333,"FR"));
        cityManager.addCity(new City("Mogadiscio",2.333333,48.85,"SOM"));
        cityManager.addCity(new City("Rouen",49.443889,1.103333,"FR"));
        cityManager.addCity(new City("Bihorel",49.455278,1.116944,"FR"));
        cityManager.addCity(new City("Londres",51.504872,-0.07857,"EN"));
        cityManager.addCity(new City("Paris",48.856578,2.351828,"FR"));
        cityManager.addCity(new City("Paris", 43.2, -80.38333, "CAN"));
        System.out.println(cityManager.getCities());
        cityManager.addCity(new City("Villers-Bocage",49.083333,-0.65,"FR"));
        cityManager.addCity(new City("Villers-Bocage",50.021858,2.326126,"FR"));
        System.out.println(cityManager.getCities());
        cityManager.removeCity(new City("Villers-Bocage",49.083333,-0.65,"FR"));
        System.out.println(cityManager.getCities());
        cityManager.removeCity(new City("Londres",51.504872,-0.07857,"EN"));
        cityManager.removeCity(new City("Londres",51.504872,-0.07857,"EN"));
        /**
         * We use a try catch here because :
         * If we have an exception we just want to warn the user and not stop the application
         * If we want we can create more exception and use it properly
         */
        try {
            System.out.println(cityManager.searchExactPosition(new Position(49.443889,1.103333)));
        } catch(CityNotFound cnf) {
            System.out.println("Warning : City not found at that Position");
        }

        try {
            System.out.println(cityManager.searchExactPosition(new Position(49.083333,-0.65)));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found at that Position");
        }

        try {
            System.out.println(cityManager.searchExactPosition(new Position(43.2,-80.38)));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found at that Position");
        }

        try {
            System.out.println(cityManager.searchNear(new Position(48.85, 2.34)));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found around that Position");
        }

        try {
            System.out.println(cityManager.searchNear(new Position(42,64)));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found around that Position");
        }
        try {
            System.out.println(cityManager.searchNear(new Position(49.95,1.11)));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found around that Position");
        }

        try {
            System.out.println(cityManager.searchFor("Mogadiscio"));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found !");
        }

        try {
            System.out.println(cityManager.searchFor("Paris"));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found !");
        }

        try {
            System.out.println(cityManager.searchFor("Hyrule"));
        } catch (CityNotFound cnf) {
            System.out.println("Warning : City not found !");
        }

        cityManager.removeAllCities();
        System.out.println(cityManager.getCities());
    }
}
