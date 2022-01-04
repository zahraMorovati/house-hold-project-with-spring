package model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
@Builder(setterPrefix = "set")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;
    private String cityState; //استان
    private String plaque;
    private String explanations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return id != 0 && Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
