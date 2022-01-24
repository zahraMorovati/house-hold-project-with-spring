package ir.maktab.service.interfaces;

import ir.maktab.data.dto.UserDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface UserService {

    void saveUserByType(UserDto userDto, CommonsMultipartFile image);
}
