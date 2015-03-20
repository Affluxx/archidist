package tp.rest;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import tp.model.City;
import tp.model.CityManagerService;

/**
 * This class represent myClient with its
 * <ul>
 *     <li></li>
 * </ul>
 *
 */
public class MyClient {
	private Service service;
	private JAXBContext jc;

    private static final QName SERVICE_NAME =
            new QName("http://model.tp/", "CityManagerService");
    private static final QName PORT_NAME =
            new QName("http://model.tp/", "CityManagerPort");


	public static void main(String args[]) throws Exception {
        URL wsdlURL = new URL("http://127.0.0.1:8084/citymanager?wsdl");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        CityManagerService cityManager = service.getPort(PORT_NAME, CityManagerService.class);
        System.out.println(cityManager.getCities());
        City c = new City("Zanarkand", 16,64, "Hyrule");
        //boolean a = cityManager.addCity(c);
        //System.out.println(a);
        //cityManager.addCity(c);
        boolean b = cityManager.removeCity(c);
        System.out.println(b);
        System.out.println(cityManager.getCities());
    }
}
