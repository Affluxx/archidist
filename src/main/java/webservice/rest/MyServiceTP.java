package webservice.rest;

import webservice.model.City;
import webservice.model.CityManager;
import webservice.model.CityNotFound;
import webservice.model.Position;

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
import java.text.ParseException;
@WebServiceProvider
/**
 * This class represent a MyServiceTP.
 *
 */
@ServiceMode(value=Service.Mode.MESSAGE)
public class MyServiceTP implements Provider<Source> {
    private CityManager cityManager = new CityManager();
    private JAXBContext jc;
    @javax.annotation.Resource(type=Object.class)
    protected WebServiceContext wsContext;
    /**
     * Construct a new MyServiceTP
     */
    public MyServiceTP(){
        try {
            jc = JAXBContext.newInstance(CityManager.class,City.class,Position.class);
        } catch(JAXBException je) {
            System.out.println("Exception " + je);
            throw new WebServiceException("Cannot create JAXBContext", je);
        }
        cityManager.addCity(new City("Rouen", 49.437994, 1.132965, "FR"));
    }
    /**
     * Return a Source and invoke a http method
     * @param source : object who can be need for some method (opt)
     * @return : the result of the execution
     */
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
                } catch (CityNotFound cityNotFound) {
                    cityNotFound.printStackTrace();
                }
            throw new WebServiceException("Unsupported method:" +method);
        } catch(JAXBException je) {
            throw new WebServiceException(je);
        }
    }
    /**
     * Return a new source after add a city
     * @param source
     * The source we want to use to add the city
     * @param mc
     * The message to send to the user
     * @return
     * A new source with the city
     * @throws JAXBException
     * Can throw a JAXBException if the source creation fail
     */
    private Source put(Source source, MessageContext mc) throws JAXBException {
    // TODO DONE à compléter
    // add a city in the citymanager
        Unmarshaller u = jc.createUnmarshaller();
        City city =(City)u.unmarshal(source);
        cityManager.addCity(city);
        return new JAXBSource(jc, cityManager);
    }
    /**
     * Return a source after delete a city
     * @param source
     * The souce we want to use to delete the city
     * @param mc
     * The message to send to the user
     * @return
     * A new source without the city
     * @throws JAXBException
     * Can throw a JAXBException if the source creation fail
     * @throws ParseException
     * Can throw a ParseException
     * @throws CityNotFound
     * Can throw a CityNotFound if we can not found the city to delete
     *
     */
    private Source delete(Source source, MessageContext mc) throws JAXBException, ParseException, CityNotFound {
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
    /**
     * Return a source
     * @param source
     * The source we work on
     * @param mc
     * the message to send
     * @return
     * Return a new source
     * @throws JAXBException
     * Throw a JAXBException if the source creation fail
     */
    private Source post(Source source, MessageContext mc) throws JAXBException {
    // * rechercher une ville à partir de sa position
        Unmarshaller u = jc.createUnmarshaller();
        Position position=(Position)u.unmarshal(source);
        CityManager cities = new CityManager();
        String path = (String) mc.get(MessageContext.PATH_INFO);
        System.out.println("path : " + path);
        if (path != null && path.equals("near")) {
            for(City c : cityManager.searchNear(position)){
                cities.addCity(c);
            }
            return new JAXBSource(jc, cities);
        } else {
            try {
                cities.addCity(cityManager.searchExactPosition(position));
            } catch (CityNotFound cityNotFound) {
                cityNotFound.printStackTrace();
            }
            return new JAXBSource(jc, cities);
        }
        /*Object message;
        try {
        } catch (CityNotFound cnf) {
        // TODO: retourner correctement l'exception
        // We don't understand what we have to do here
        message = cnf;
        }*/
    // TODO DONE à compléter
    // * rechercher les villes proches de cette position si l'url de post contient le mot clé "near"
    }
    /**
     * Return a new source
     * @param mc
     * The message
     * @return
     * A new source
     * @throws JAXBException
     * Throw a JAXBException if the source creation fail
     */
    private Source get(MessageContext mc) throws JAXBException {
    // TODO DONE à compléter
    // * retourner seulement la ville dont le nom est contenu dans l'url d'appel
    // * retourner tous les villes seulement si le chemin d'accès est "all"
        String path = (String) mc.get(MessageContext.PATH_INFO);

        if (path.equals("all")) {
            return new JAXBSource(jc, cityManager);
        } else {
            CityManager cm = new CityManager();
            cm.setCities(cityManager.searchFor(path));
            return new JAXBSource(jc,cm);
        }
    }
    public static void main(String args[]) {
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING,
                new MyServiceTP());
        e.publish("http://127.0.0.1:8084/");
    // pour arrêter : e.stop();
    }
}
