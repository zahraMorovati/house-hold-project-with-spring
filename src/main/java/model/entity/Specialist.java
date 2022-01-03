package model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Specialist extends User {
    @Lob
    @Column(length = 300_000,columnDefinition = "BLOB")
    private byte[] image;
    @OneToMany
    @ToString.Exclude
    private List<Service> services = new ArrayList<>();
    private double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Specialist that = (Specialist) o;
        return super.getId()!= 0 && Objects.equals(super.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
