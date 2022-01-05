package dto;

import lombok.Data;
import model.enums.UserState;
@Data
public class UserDto {
    private String name;
    private String family;
    private String email;
    private double balance;
    private UserState state;
}
