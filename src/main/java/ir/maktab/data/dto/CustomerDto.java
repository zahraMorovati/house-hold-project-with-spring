package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

    private String name;
    private String family;
    private String email;
    private String state;
    private String registrationDate;

}
