package tp.model;

import javax.jws.WebService;
import java.util.List;
@WebService
public interface CityManagerService {
    boolean addCity(City city);
    boolean removeCity(City city);
    List<City> getCities();
}