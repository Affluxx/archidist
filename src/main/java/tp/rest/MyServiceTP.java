package main.java.tp.rest;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;

import main.java.tp.model.City;
import main.java.tp.model.CityManager;

/**
 * This class represent a MyServiceTP. It can :
 * <ul>
 *     <li></li>
 * </ul>
 */
@WebServiceProvider
                   
@ServiceMode(value=Service.Mode.MESSAGE)
public class MyServiceTP  {
	
	@javax.annotation.Resource(type=Object.class)
	protected WebServiceContext wsContext;

    /**
     * Construct a new MyServiceTP
     */
	public MyServiceTP() {
        // We create a new city manager and add a city
        CityManager cityManager = new CityManager();
        cityManager.addCity(new City("Rouen",50,50,"FR"));
        Endpoint.publish("http://127.0.0.1:8084/citymanager", cityManager);
    }

	public static void main(String args[]) throws InterruptedException {

        new MyServiceTP();
        Thread.sleep(15 * 60 * 1000);
        System.exit(0);
	 }
}
