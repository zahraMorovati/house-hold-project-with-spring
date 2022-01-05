package dao.interfaces;

import model.entity.HomeService;

import java.util.List;

public interface HomeServiceDao {
    void save(HomeService service);

    void delete(HomeService service);

    void update(HomeService service);

    List<HomeService> getServiceById(int id);

    List<HomeService> getAllServices();
}
