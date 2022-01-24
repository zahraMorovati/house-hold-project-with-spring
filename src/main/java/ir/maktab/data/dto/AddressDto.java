package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {

    private String city;
    private String cityState;
    private String plaque;
    private String explanations;

}
