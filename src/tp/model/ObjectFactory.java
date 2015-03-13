package tp.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
// Doc ok


@XmlRegistry
public class ObjectFactory {

	private final static QName Position_QNAME = new QName("m2GIL:rest:tp", "Position");
	private final static QName City_QNAME = new QName("m2GIL:rest:tp", "City");
    private final static QName CityManager_QNAME = new QName("m2GIL:rest:tp", "CityManager");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for this package
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link City }
     * @return A new City
     */
    public City createCity() {
        return new City();
    }

    /**
     * Create an instance of {@link Position }
     * @return A new Position
     */
    public Position createPosition() {
        return new Position();
    }

    /**
     * Create an instance of (@link CityManager }
     * @return a new CityManager
     */
    public CityManager createCityManager() {
        return new CityManager();
    }
    
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link City }{@code >}
     * @param value
     * The city
     * @return
     * Return a new JAXBElement
     */
    @XmlElementDecl(namespace = "m2GIL:rest:tp", name = "City")
    public JAXBElement<City> createCity(City value) {
        return new JAXBElement<City>(City_QNAME, City.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement } {@code <} {@link Position} {@code >}
     * @param value
     * The positon
     * @return
     * Return a new JAXBElement
     */
    @XmlElementDecl(namespace = "m2GIL:rest:tp", name = "Position")
    public JAXBElement<Position> createPosition(Position value) {
        return new JAXBElement<Position>(Position_QNAME, Position.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement } {@code <} {@link CityManager} {@code >}
     * @param value
     * The CityManager
     * @return
     * Return a new JAXBElement
     */
    @XmlElementDecl(namespace = "m2GIL:rest:tp", name = "CityManager")
    public JAXBElement<CityManager> createCityManager(CityManager value) {
        return new JAXBElement<CityManager>(CityManager_QNAME, CityManager.class, null, value);
    }
    
}
