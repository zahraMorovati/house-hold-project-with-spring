package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubServiceEntityDto {

    private String name;

}
