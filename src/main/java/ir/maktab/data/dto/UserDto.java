package ir.maktab.data.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Builder(setterPrefix = "set")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    @NotBlank(message = "please enter your name!")
    private String name;
    @NotBlank(message = "please enter your name!")
    private String family;
    @Email(message = "email not valid!")
    @NotBlank(message = "please enter your email!")
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9]{6}", message = "password must include numbers and alphabet with length 6!")
    private String password;
    private double balance;

    private String userType;
    private CommonsMultipartFile image;





}
