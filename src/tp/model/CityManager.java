package tp.model;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
// Doc ok

/**
 * This class represent a city manager, it can
 * <ul>
 * <li>add a city</li>
 * <li>remove a city</li>
 * <li>return the list of cities</li>
 * <li>search a city with a given name</li>
 * <li>search a city at a position</li>
 * <li>return the list of cities near 10km of the given position</li>
 * </ul>
 */
@WebService(endpointInterface = "tp.model.CityManagerService", serviceName = "CityManagerService")
public class CityManager implements CityManagerService {
    /**
     * TODO Tel to Steeven :
     * Ce block est la pour les infos que j'aurai besoin de te donner.
     * J'ai mis en place l'exception pour ce tp, c'est bien plus simple.
     * J'ai ajouter le removeall.
     */
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
     *
     * @return cities
     * The list of cities
     */
    public List<City> getCities() {
        return cities;
    }
    // A method who define the list of cities

    /**
     * Appends a new city at the end of the list
     *
     * @param city city to be appended to this list
     * @return true
     */
    public boolean addCity(City city) {
        return cities.add(city);
    }
    // A method who remove a city of the list

    /**
     * Remove the first occurence of the city. If the list does not contain the city
     * the list stay unchanged
     *
     * @param city City to be removed from the list
     * @return true if the list contain the city
     */
    public boolean removeCity(City city) {
        // the remove method from the list is not the good way to do the remove part
        // So we look all cities in the CityManager
        for (City cit : getCities()) {
            // We compare the name of the city with the equalsIgnoreCase method
            // because it compare String and not Object
            // And we also compare the Position. Position redefine the equals method so we use this one
            if (cit.getName().equalsIgnoreCase(city.getName()) && cit.getPosition().equals(city.getPosition())) {
                //If we have a city we remove this one and return the value
                return cities.remove(cit);
            }
        }
        // If we don't find a city we return false
        return false;
    }

    /**
     * Return the City a the exact position
     *
     * @param position The position of the City
     * @return city
     * The City if it exist
     * @throws CityNotFound If the City  don't exist an exception is throw
     */
    public City searchExactPosition(Position position) throws CityNotFound {
        // We look all city in our list
            for (City c : getCities()) {
                // If we found a city at position we return it
                // We use the equals method of Position because it is redefine
                if (c.getPosition().equals(position)) {
                    return c;
                }
            }
        throw new CityNotFound();
    }

    /**
     * Return the list of cities around the position
     *
     * @param p The position to look around
     * @return cities
     * Return the list of cities around the position
     */
    public List<City> searchNear(Position p) throws CityNotFound {
        //We create a list for the result
        List<City> tmp = new ArrayList<City>();
        // We look all cities in our list
        for (City c : getCities()) {
            // We compare the distance between p and the city
            if (p.distanceTo(c.getPosition()) < 10000) {
                // If the result is under 10000 we add the city
                tmp.add(c);
            }
        }
        // If the list is vide we return an exception
        if (tmp.isEmpty()) {
            throw new CityNotFound();
        }
        // We return the list
        return tmp;
    }

    /**
     * Return the list of cities where getName() == name
     *
     * @param name The name of the city whe look for
     * @return cities
     * Return the list of cities
     */
    public List<City> searchFor(String name) throws CityNotFound {
        // First step we create an empty list
        List<City> tmp = new ArrayList<City>();
        // We check every city in our list
        for (City c : getCities()) {
            // If a city have the same name we add it in tmp
            if (c.getName().equalsIgnoreCase(name)) {
                tmp.add(c);
            }
        }
        // If the list is emptywe throw an exception
        if (tmp.isEmpty()) {
            throw new CityNotFound();
        }
        // Finaly we return the tmp list
        return tmp;
    }

    /**
     * Delete all cities in the list
     */
    public void removeAllCities() {
        cities.clear();
    }


}
