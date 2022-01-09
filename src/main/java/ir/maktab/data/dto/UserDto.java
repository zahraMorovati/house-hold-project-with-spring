package ir.maktab.data.dto;

import lombok.Data;
import ir.maktab.data.model.enums.UserState;
@Data
public class UserDto {
    private String name;
    private String family;
    private String email;
    private double balance;
    private UserState state;
}
