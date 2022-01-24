package ir.maktab.data.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "set")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private String name;
    private String family;
    private String email;
    private String password;
    private double balance;
    private String userType;





}
