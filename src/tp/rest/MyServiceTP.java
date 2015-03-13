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
        cityManager.addCity(new City("Rouen",49.437994,1.132965,"FR"));
        cityManager.addCity(new City("Neuor",12,42,"RF"));
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
					return delete(source, mc); 
			throw new WebServiceException("Unsupported method:" +method);  
        } catch(JAXBException je) {
            throw new WebServiceException(je);
        }
    }

	private Source put(Source source, MessageContext mc) throws JAXBException {
		// TODO à compléter 
		// * ajouter une ville passée en paramètre au citymanager
		
		return new JAXBSource(jc, "todo");
	}

	private Source delete(Source source, MessageContext mc) {
		
		// TODO à compléter 
		// * effacer toute la liste de ville
		// * effacer la ville passée en paramètre
		
		return null;
	}

	private Source post(Source source, MessageContext mc) throws JAXBException {
		// * rechercher une ville à partir de sa position
		Unmarshaller u = jc.createUnmarshaller();
		Position position=(Position)u.unmarshal(source);
		Object message;
		try {
			if (mc.containsKey("near")){
				message = cityManager.searchNear(position);
			} else {
				message = cityManager.searchExactPosition(position);
			}
		} catch (CityNotFound cnf) {
			// TODO: retourner correctement l'exception
			message = cnf;
		}
		
		return new JAXBSource(jc, message);
		// TODO à compléter 
		// * rechercher les villes proches de cette position si l'url de post contient le mot clé "near"

	}

	private Source get(MessageContext mc) throws JAXBException {
		// TODO à compléter 
		// * retourner seulement la ville dont le nom est contenu dans l'url d'appel
		// * retourner tous les villes seulement si le chemin d'accès est "all"
		return new JAXBSource(jc, cityManager);
	}

	public static void main(String args[]) {
	      Endpoint e = Endpoint.create( HTTPBinding.HTTP_BINDING,
	                                     new MyServiceTP());
	      e.publish("http://127.0.0.1:8084/");
	       // pour arrêter : e.stop();
	 }
}
