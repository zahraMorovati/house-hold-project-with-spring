package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.enums.UserState;
import ir.maktab.exception.UserEceptions.DuplicatedEmailException;
import ir.maktab.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service
public class UserServiceImpl implements UserService {

    CustomerDao customerDao;
    SpecialistDao specialistDao;

    @Autowired
    public UserServiceImpl(CustomerDao customerDao, SpecialistDao specialistDao) {
        this.customerDao = customerDao;
        this.specialistDao = specialistDao;
    }

    @Override
    public void saveUserByType(UserDto userDto, CommonsMultipartFile image) {

        String userType = userDto.getUserType();

        if (userType.equalsIgnoreCase("specialist")) {
            saveSpecialistByUserDto(userDto,image);

        } else if (userType.equalsIgnoreCase("customer")) {
            saveCustomerByUserDto(userDto);
        }
    }

    private void saveCustomerByUserDto(UserDto userDto) {
        int result = customerDao.findCustomerByEmail(userDto.getEmail()).size();
        if (result <= 0) {
            Customer customer = getCustomer(userDto);
            customerDao.save(customer);
        } else throw new DuplicatedEmailException();
    }

    private void saveSpecialistByUserDto(UserDto userDto,CommonsMultipartFile image) {
        int results = specialistDao.findSpecialistByEmail(userDto.getEmail()).size();
        if (results <= 0) {
            Specialist specialist = getSpecialist(userDto);
            specialist.setImage(image.getBytes());
            specialistDao.save(specialist);
        } else throw new DuplicatedEmailException();
    }

    private Customer getCustomer(UserDto userDto) {
        return Customer.CustomerBuilder.aCustomer()
                .setName(userDto.getName())
                .setFamily(userDto.getFamily())
                .setState(UserState.NEW_USER)
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword()).build();
    }

    private Specialist getSpecialist(UserDto userDto) {
        return Specialist.SpecialistBuilder.aSpecialist()
                .setName(userDto.getName())
                .setFamily(userDto.getFamily())
                .setEmail(userDto.getEmail())
                .setState(UserState.NEW_USER)
                .setPassword(userDto.getPassword()).build();
    }
}
