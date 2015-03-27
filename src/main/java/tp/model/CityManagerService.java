package main.java.tp.model;

import javax.jws.WebService;
import java.util.List;

/**
 * This interface represent a CityManagerService. It can:
 * <ul>
 *     <li>Add a city</li>
 *     <li>remove a city</li>
 *     <li>Return all city</li>
 *     <li> search a city with his name</li>
 *     <li> search a city with his exact position</li>
 *     <li> search all city around a position</li>
 *     <li> delete all city</li>
 * </ul>
 */
@WebService
public interface CityManagerService {
    /**
     * Add a city to the list
     * @param city
     * The city to add
     * @return
     * True if the city is add, false if it's not
     */
    boolean addCity(City city);

    /**
     * Remove a city from the list
     * @param city
     * the city to remove
     * @return
     * true if the city is remove, false if it's not
     */
    boolean removeCity(City city);

    /**
     * Return the list of cities
     * @return
     * The list of cities
     */
    List<City> getCities();

    /**
     * Search a city with his name
     * @param name
     * The name of the city
     * @return
     * A list of cities who have this name
     */
    public List<City> searchFor(String name) throws CityNotFound;

    /**
     * Search all city around a position
     * @param p
     * the position
     * @return
     * A list of cities around a positon
     */
    public List<City> searchNear(Position p) throws CityNotFound;

    /**
     * Search the ciy to the position
     * @param position
     * the position where we look
     * @return
     * The city
     * @throws CityNotFound
     * Return a CityNotFound if no city is at the position
     */
    public City searchExactPosition(Position position) throws CityNotFound;

    /**
     * Remove all city
     */
    public void removeAllCities();
}