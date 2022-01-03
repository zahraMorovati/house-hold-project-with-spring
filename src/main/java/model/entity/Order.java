package model.entity;

import lombok.*;
import model.enums.OrderState;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Service service;
    private double suggestedPrice;
    private String explanations;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;
    @OneToOne
    private Address address;
    @Enumerated(value = EnumType.STRING)
    private OrderState state;
    @OneToOne
    private Suggestion suggestion;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != 0 && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
