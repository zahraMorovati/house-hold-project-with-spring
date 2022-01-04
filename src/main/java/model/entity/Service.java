package model.entity;

import lombok.*;
import model.enums.ServiceTypes;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.util.Objects;
@Builder(setterPrefix = "set")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(value = EnumType.STRING)
    private ServiceTypes service;
    @Column(unique = true,length = 25)
    private String subService;
    private double price;
    @Lob
    private String explanations; //توضیحات

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Service service = (Service) o;
        return id != 0 && Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
