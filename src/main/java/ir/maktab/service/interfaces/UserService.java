package ir.maktab.service.interfaces;

import ir.maktab.data.model.enums.UserType;

public interface UserService {

    void saveUserByType(UserType userType, String name , String family , String email , String password);
}
