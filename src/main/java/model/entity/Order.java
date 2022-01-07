package model.entity;

import lombok.*;
import model.enums.OrderState;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
@Builder(setterPrefix = "set")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @OneToOne
    private HomeService service;
    private double suggestedPrice;
    private String explanations;
    @CreationTimestamp
    private Date registrationDate;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;
    @OneToOne
    private Address address;
    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;
    @OneToOne
    private Suggestion suggestion;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Customer customer;
    @OneToOne
    private Comment comment;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return orderId != 0 && Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
