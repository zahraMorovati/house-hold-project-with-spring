package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.dao.interfaces.ManagerDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.model.entity.Customer;
import ir.maktab.data.model.entity.Manager;
import ir.maktab.data.model.entity.Specialist;
import ir.maktab.data.model.enums.UserType;
import ir.maktab.service.interfaces.UserService;

public class UserServiceImpl implements UserService {

    CustomerDao customerDao;
    SpecialistDao specialistDao;
    ManagerDao managerDao;

    @Override
    public void saveUserByType(UserType userType, String name, String family, String email, String password) {
        switch (userType) {
            case SPECIALIST: {
                Specialist specialist = Specialist.SpecialistBuilder.aSpecialist()
                        .setName(name)
                        .setFamily(family)
                        .setEmail(email)
                        .setPassword(password).build();
                specialistDao.save(specialist);
            }
            break;
            case CUSTOMER: {
                Customer customer = Customer.CustomerBuilder.aCustomer()
                        .setName(name)
                        .setFamily(family)
                        .setEmail(email)
                        .setPassword(password).build();
                customerDao.save(customer);
            }
            break;
            case MANAGER: {
                Manager manager = Manager.builder()
                        .setName(name)
                        .setFamily(family)
                        .setEmail(email)
                        .setPassword(password).build();
                managerDao.save(manager);
            }
            break;
        }
    }
}
