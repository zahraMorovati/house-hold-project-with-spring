package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.entity.Customer;

public class CustomerMapper {

    public  static CustomerDto toCustomerDto(Customer customer){
        return CustomerDto.builder()
                .setName(customer.getName())
                .setFamily(customer.getFamily())
                .setEmail(customer.getEmail())
                .setState(customer.getState().name().toLowerCase())
                .setRegistrationDate(customer.getRegistrationDate().toString()).build();
    }
}
