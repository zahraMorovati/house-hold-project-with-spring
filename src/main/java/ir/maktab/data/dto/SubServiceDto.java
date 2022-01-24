package ir.maktab.data.dto;

import ir.maktab.data.entity.Service;
import ir.maktab.data.entity.Specialist;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubServiceDto {

    private Service service;
    private String subServiceName;
    private double price;
    private String explanations;
    private List<Specialist> specialists = new ArrayList<>();
}
