package tp.rest;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;

import tp.model.City;
import tp.model.CityManager;


@WebServiceProvider
                   
@ServiceMode(value=Service.Mode.MESSAGE)
public class MyServiceTP  {
	
	/**
	 * Gère les villes
	 */
	
	@javax.annotation.Resource(type=Object.class)
	protected WebServiceContext wsContext;
	public MyServiceTP() {
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
