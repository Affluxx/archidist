package tp.model;

import javax.jws.WebService;
import java.util.List;
@WebService
public interface CityManagerService {
    boolean addCity(City city);
    boolean removeCity(City city);
    List<City> getCities();
    public List<City> searchFor(String name);
    public List<City> searchNear(Position p);
    public City searchExactPosition(Position position) throws CityNotFound;
}