package service;

import dao.HomeServiceDaoImpl;
import dao.SpecialistDaoImpl;
import model.entity.HomeService;
import model.entity.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceService {

    protected HomeServiceDaoImpl homeServiceDao;
    protected SpecialistDaoImpl specialistDao;

    public SpecialistDaoImpl getSpecialistDao() {
        return specialistDao;
    }

    @Autowired
    public void setSpecialistDao(@Qualifier("specialistDao") SpecialistDaoImpl specialistDao) {
        this.specialistDao = specialistDao;
    }

    public HomeServiceDaoImpl getHomeServiceDao() {
        return homeServiceDao;
    }

    @Autowired
    public void setHomeServiceDao(@Qualifier("HomeServiceDao") HomeServiceDaoImpl homeServiceDao) {
        this.homeServiceDao = homeServiceDao;
    }

    public void save(HomeService homeService){
        homeServiceDao.save(homeService);
    }

    public void update(HomeService homeService){
        homeServiceDao.update(homeService);
    }

    public void delete(HomeService homeService){
        homeServiceDao.delete(homeService);
    }

    public  HomeService findHomeServiceById(int id){
        List<HomeService> homeServiceList = homeServiceDao.getServiceById(id);
        if(homeServiceList.isEmpty())
            throw new RuntimeException("cannot find service by id!");
        else return homeServiceList.get(0);
    }

    public List<HomeService> findAllHomeServices(){
        List<HomeService> services = homeServiceDao.getAllServices();
        if(services.isEmpty())
            throw new RuntimeException("there is no home service");
        else return services;
    }

    public void addSpecialist(HomeService homeService , Specialist specialist){
        // check homeService exist
        if(!homeServiceDao.getServiceById(homeService.getId()).isEmpty()){
            // check specialist exist
            if(!specialistDao.getSpecialistById(specialist.getId()).isEmpty()){
                homeService.getSpecialistList().add(specialist);
                homeServiceDao.update(homeService);
            }else throw new RuntimeException("cannot find specialist!");
        }else throw new RuntimeException("cannot find homeService!");
    }

    public void removeSpecialist(HomeService homeService , Specialist specialist){
        // check homeService exist
        if(!homeServiceDao.getServiceById(homeService.getId()).isEmpty()){
            // check if specialist exist in homeService specialists list
            if(homeServiceDao.getSpecialistById(homeService.getId(),homeService)>0){
                // check specialist exist
                if(!specialistDao.getSpecialistById(specialist.getId()).isEmpty()){
                    homeService.getSpecialistList().remove(specialist);
                    homeServiceDao.update(homeService);
                }else throw new RuntimeException("cannot find specialist!");
            }else throw new RuntimeException("this specialist is not in this service list!");
        }else throw new RuntimeException("cannot find homeService!");
    }


}
