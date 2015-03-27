package tp.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
@XmlRootElement
public class CityManager {
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
     * Set the list of cities
     * @param cities
     * the new list of cities
     */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	// A method who add a city in the list

    /**
     * Appends a new city at the end of the list
     * @param city
     * city to be appended to this list
     * @return true
     */
    // TODO : doc more
	public boolean addCity(City city){
        boolean found = false;
        for(City c : this.getCities()){
            System.out.println("city c : " + c.toString());
            if (c.getPosition().equals(city.getPosition())){
                found = true;
            }
        }
        if(found){
            System.out.println("city not added : already in");
            return true;
        } else {
            System.out.println("city added");
            return cities.add(city);

        }
	}
	// A method who remove a city of the list

    /**
     * Remove the first occurence of the city. If the list does not contain the city
     * the list stay unchanged
     * @param city
     * City to be removed from the list
     * @return true if the list contain the city
     */
    //TODO : doc more
	public boolean removeCity(City city) throws CityNotFound {
        City found = null;
        for(City c : this.getCities()){
            System.out.println("city c : " + c.toString());

            if (c.getPosition().equals(city.getPosition())){
                found = c;
            }
        }
        if(found == null){
            System.out.println("city not found : " + city.toString());
            throw new CityNotFound();
        } else {
            System.out.println("city found");

        }
        return cities.remove(found);
	}

    /**
     * Return a List of City with the name in parameter
     * of the city. (As we know, a cityName can be use in many countries).
     * Ex : Paris (in France) and Paris(in USA)
     * @param cityName
     * The name of the city
     * @return tmp
     * A list of cities.
     */
    //TODO : doc more
	public List<City> searchFor(String cityName){
		// TODO DONE: à compléter
		List<City> tmp = new ArrayList<City>();
		for (City c : cities){
			if(c.getName().equals(cityName)){
				tmp.add(c);
			}
		}
		return tmp;
	}

    /**
     * Return the city at the exact position if it exist
     * @param position
     * The position of the city
     * @return city
     * The city if it exist
     * @throws CityNotFound
     * If the city don't exist we throw an exception
     */
	public City searchExactPosition(Position position) throws CityNotFound {
        // We look all city
		for(City city : getCities()){
            // If one city have the exact position we return it
			if (position.equals(city.getPosition())){
				return city;
			}
		}
        // If we don't find a result that mean we can not find a city at this position
		throw new CityNotFound();
	}
	
	/**
	 * TODO DONE: searchNear : une fonction qui retourne la liste des villes à dix klomètres d'une position
	 */
    /**
     * With the function searcNear you can have a list of cities around a position
     * @param p
     * the parameter is the position
     * @return tmp
     * The return value is the list of cities around the parameter p.
     */
	public List<City> searchNear(Position p){
		// At first we create a List to stock the result.
		List<City> tmp = new ArrayList<City>();

		//With the two variables we search all cities around the position
		for (City c : getCities()){
			//Here we have the test who validate the city
            if(p.distanceTo(c.getPosition()) < 10000){
				tmp.add(c);
			}
		}
		// And after the research we return the result.
		return tmp;
	}

    /**
     * clear the city list
     */
    public void clear(){
        cities.clear();
    }
}
