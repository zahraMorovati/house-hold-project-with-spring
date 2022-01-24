package ir.maktab.data.entity;

import ir.maktab.data.enums.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Specialist extends User {

    @Lob
    @Column(length = 300_000,columnDefinition = "BLOB")
    private byte[] image;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "specialist",cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Specialist that = (Specialist) o;
        return getId() != 0 && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    public static final class SpecialistBuilder {
        private Specialist specialist;

        private SpecialistBuilder() {
            specialist = new Specialist();
        }

        public static SpecialistBuilder aSpecialist() {
            return new SpecialistBuilder();
        }

        public SpecialistBuilder setImage(byte[] image) {
            specialist.setImage(image);
            return this;
        }

        public SpecialistBuilder setOrders(List<Order> orders) {
            specialist.setOrders(orders);
            return this;
        }

        public SpecialistBuilder setId(int id) {
            specialist.setId(id);
            return this;
        }

        public SpecialistBuilder setName(String name) {
            specialist.setName(name);
            return this;
        }

        public SpecialistBuilder setFamily(String family) {
            specialist.setFamily(family);
            return this;
        }

        public SpecialistBuilder setEmail(String email) {
            specialist.setEmail(email);
            return this;
        }

        public SpecialistBuilder setPassword(String password) {
            specialist.setPassword(password);
            return this;
        }

        public SpecialistBuilder setState(UserState state) {
            specialist.setState(state);
            return this;
        }

        public SpecialistBuilder setRegistrationDate(Date RegistrationDate) {
            specialist.setRegistrationDate(RegistrationDate);
            return this;
        }

        public SpecialistBuilder setBalance(double balance) {
            specialist.setBalance(balance);
            return this;
        }

        public SpecialistBuilder but() {
            return aSpecialist().setImage(specialist.getImage()).setOrders(specialist.getOrders()).setId(specialist.getId()).setName(specialist.getName()).setFamily(specialist.getFamily()).setEmail(specialist.getEmail()).setPassword(specialist.getPassword()).setState(specialist.getState()).setRegistrationDate(specialist.getRegistrationDate()).setBalance(specialist.getBalance());
        }

        public Specialist build() {
            return specialist;
        }
    }
}
