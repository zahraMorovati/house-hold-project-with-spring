package ir.maktab.data.dto;

import ir.maktab.data.entity.*;
import ir.maktab.data.enums.OrderState;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private String orderCode;
    private String  subService;
    private double suggestedPrice;
    private String explanations;
    private Date registrationDate;
    private String startDate;
    private String address;
    private String orderState;
    private String specialist;
    private String customer;
    private String comment;
    private double point;
    private String city;
    private String cityState; //استان
    private String plaque;
    private String addressExplanations;
    private String specialistEmail;

}
