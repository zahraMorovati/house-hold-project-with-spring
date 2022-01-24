package ir.maktab.data.entity;

import ir.maktab.data.enums.OrderState;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.id.UUIDHexGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    @Column(unique = true,nullable = false,length = 10)
    private String orderCode;

    @OneToOne
    private SubService subService;

    private double suggestedPrice;

    private String explanations;

    @CreationTimestamp
    private Date registrationDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    @OneToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "order")
    @ToString.Exclude
    private List<Suggestion> suggestions;

    @ManyToOne(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    private Specialist specialist;

    @ManyToOne(cascade = {CascadeType.MERGE})
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
