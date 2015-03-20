package tp.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlRootElement;
// Doc ok

/**
 * This class represent a city manager, it can  
 * <ul>
 * 	<li>add a city</li>
 * 	<li>remove a city</li>
 * 	<li>return the list of cities</li>	
 * 	<li>search a city with a given name</li>
 *  <li>search a city at a position</li>
 * 	<li>return the list of cities near 10km of the given position</li>
 * </ul>
 *
 */
@WebService(endpointInterface = "tp.model.CityManagerService", serviceName = "CityManagerService")
public class CityManager implements CityManagerService {
    // An attribute for the cities in the manager
	private List<City> cities;

    /**
     * Create a new CityManager with no arguments.
     */
	public CityManager() {
		this.cities = new LinkedList<City>();
	}
    // A method who return the list of cities

    /**
     * Return the list of cities
     * @return cities
     * The list of cities
     */
	public List<City> getCities() {
		return cities;
	}
    // A method who define the list of cities

    /**
     * Appends a new city at the end of the list
     * @param city
     * city to be appended to this list
     * @return true
     */
	public boolean addCity(City city){
		return cities.add(city);
	}
	// A method who remove a city of the list

    /**
     * Remove the first occurence of the city. If the list does not contain the city
     * the list stay unchanged
     * @param city
     * City to be removed from the list
     * @return true if the list contain the city
     */
	public boolean removeCity(City city){
		return cities.remove(city);
	}

}
