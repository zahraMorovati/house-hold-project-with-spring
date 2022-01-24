package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Specialist;

public class UserMapper {

    public static Customer toCustomer(UserDto userDto){
        return Customer.CustomerBuilder.aCustomer()
                .setName(userDto.getName())
                .setFamily(userDto.getFamily())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword()).build();
    }

    public static Specialist toSpecialist(UserDto userDto){
        return  Specialist.SpecialistBuilder.aSpecialist()
                .setName(userDto.getName())
                .setFamily(userDto.getFamily())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword()).build();
    }

    public static UserDto toUserDto(String name,String family,String email){
        return UserDto.builder()
                .setName(name)
                .setFamily(family)
                .setEmail(email).build();
    }

    public static UserDto toUserDto(String name,String family,String email,double balance){
        return UserDto.builder()
                .setName(name)
                .setFamily(family)
                .setBalance(balance)
                .setEmail(email).build();
    }


}
