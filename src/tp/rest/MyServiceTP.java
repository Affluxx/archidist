package tp.rest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;

import tp.model.City;
import tp.model.CityManager;
import tp.model.CityNotFound;
import tp.model.Position;

import java.text.NumberFormat;
import java.text.ParseException;

@WebServiceProvider
                   
@ServiceMode(value=Service.Mode.MESSAGE)
public class MyServiceTP implements Provider<Source> {
	
	/**
	 * Gère les villes
	 */
	private CityManager cityManager = new CityManager();
	
	private JAXBContext jc;
	
	@javax.annotation.Resource(type=Object.class)
	protected WebServiceContext wsContext;
	
	public MyServiceTP(){
		try {
            jc = JAXBContext.newInstance(CityManager.class,City.class,Position.class);
            
        } catch(JAXBException je) {
            System.out.println("Exception " + je);
            throw new WebServiceException("Cannot create JAXBContext", je);
        }
	}
	 
    public Source invoke(Source source) {
    	
        try{
            MessageContext mc = wsContext.getMessageContext();
            String path = (String)mc.get(MessageContext.PATH_INFO);
            String method = (String)mc.get(MessageContext.HTTP_REQUEST_METHOD);
            System.out.println("Got HTTP "+method+" request for "+path);
		    if (method.equals("GET")) 
	                return get(mc);
			if (method.equals("POST")) 
				    return post(source, mc);
           	if (method.equals("PUT")) 
					return put(source, mc); 
           	if (method.equals("DELETE"))
                try {
                    return delete(source, mc);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            throw new WebServiceException("Unsupported method:" +method);
        } catch(JAXBException je) {
            throw new WebServiceException(je);
        }
    }

	private Source put(Source source, MessageContext mc) throws JAXBException {
		// TODO DONE à compléter
		// * ajouter une ville passée en paramètre au citymanager
        Unmarshaller u = jc.createUnmarshaller();
        City city =(City)u.unmarshal(source);

        cityManager.addCity(city);

        return new JAXBSource(jc, cityManager);
	}

	private Source delete(Source source, MessageContext mc) throws JAXBException, ParseException {

		// TODO DONE à compléter
		// * effacer la ville passée en paramètre

        Unmarshaller u = jc.createUnmarshaller();
        String path = (String) mc.get(MessageContext.PATH_INFO);
        System.out.println("path : " + path);
        if(path != null && path.equals("all")){
            cityManager.clear();
        } else {
            String[] info = path.split("/");
            City city;
            city = new City(info[1],Double.parseDouble(info[3]),Double.parseDouble(info[2]), info[0]);
            System.out.println("city c : " + city.toString());
            cityManager.removeCity(city);
        }
        return new JAXBSource(jc, cityManager);
	}

	private Source post(Source source, MessageContext mc) throws JAXBException {
		// * rechercher une ville à partir de sa position
		Unmarshaller u = jc.createUnmarshaller();
		Position position=(Position)u.unmarshal(source);
        CityManager cities = new CityManager();
        String path = (String) mc.get(MessageContext.PATH_INFO);
        System.out.println("path : " + path);
        if (path != null && path.equals("near")) {
            for(City c :  cityManager.searchNear(position)){
                cities.addCity(c);
            }
            return new JAXBSource(jc, cities);
        } else {
            cities.addCity(cityManager.searchExactPosition(position));
            return new JAXBSource(jc, cities);
        }
		/*Object message;
		try {
		} catch (CityNotFound cnf) {
			// TODO: retourner correctement l'exception
			message = cnf;
		}*/

		// TODO DONE à compléter
		// * rechercher les villes proches de cette position si l'url de post contient le mot clé "near"

	}

	private Source get(MessageContext mc) throws JAXBException {
		// TODO DONE à compléter
		// * retourner seulement la ville dont le nom est contenu dans l'url d'appel
		// * retourner tous les villes seulement si le chemin d'accès est "all"

        String path = (String) mc.get(MessageContext.PATH_INFO);
        CityManager cities = new CityManager();
        System.out.println(path);
        if (path == null || path.equals("all")) {
            System.out.println("ALL");
            return new JAXBSource(jc, cityManager);
        } else {
            String[] info = path.split("/");
            for (String name : info){
                System.out.println("name : " + name);
                for (City c : cityManager.getCities()){
                    if(c.getName().equals(name)){
                        cities.addCity(c);
                    }
                }
            }
        }
        return new JAXBSource(jc, cities);
    }

	public static void main(String args[]) {
	      Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING,
	                                     new MyServiceTP());
	      e.publish("http://127.0.0.1:8084/");
	       // pour arrêter : e.stop();
	 }
}
