package tp.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represent a city, it can
 * <ul>
 * 	<li>return the name of the City</li>
 * 	<li>return the position of the City</li>
 * 	<li>return the country of the City</li>
 * 	<li>set the name of the City</li>
 * 	<li>set the position of the City </li>
 * 	<li>set the country of the City</li>
 * 	<li>return a string representation of the City</li>
 * </ul>
 *
 */
@XmlRootElement
public class City {
    // Name of the city
	private String name;
    // Position of the city
	private Position location;
    // Country of the city
	private String country;
	
	/**
	 * Creates a city with its name, its latitude, its longitude and its country
	 * @param name the name of the city
	 * @param latitude the latitude of the city in WGS84
	 * @param longitude the longitude of the city in WGS84
	 * @param country the country of the city
	 */
	public City(String name, double latitude, double longitude, String country) {
		this.name = name;
		this.location = new Position(latitude,longitude);
		this.country = country;
	}

    /**
     * Creates a city with no parameter.
     */
	public City() {
	}

    /**
     * Return the position of the city
     * @return location
     * The location of the city
     */
	public Position getPosition() {
		return location;
	}

    /**
     * Set the Position of the city
     * @param position
     * The new position of the city
     */
	public void setPosition(Position position) {
		this.location = position;
	}

    /**
     * Return the name of the city
     * @return name
     * The name of the city
     */
    public String getName() {
		return name;
	}

    /**
     * Set the name of the city
     * @param name
     * The new name of the City
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Get the country of the city
     * @return country
     * The country of the city
     */
	public String getCountry() {
		return country;
	}

    /**
     * Set the country of the city
     * @param country
     * the new Country of the city
     */
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString(){
		final StringBuffer buffer = new StringBuffer();
		buffer.append(name).append(" in ").append(country).append(" at ").append(location);
		return buffer.toString();
	}
	
}
