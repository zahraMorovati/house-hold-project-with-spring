package ir.maktab.data.model.entity;

import lombok.*;
import ir.maktab.data.model.enums.OrderState;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
    private int id;
    @OneToOne
    private SubService subService;
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
    @OneToMany
    @ToString.Exclude
    private List<Suggestion> suggestions;
    @OneToOne
    private Specialist specialist;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Customer customer;
    @OneToOne
    private Comment comment;


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
