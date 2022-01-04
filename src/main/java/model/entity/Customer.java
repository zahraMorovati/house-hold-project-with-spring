package model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import model.enums.UserState;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != 0 && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    public static final class CustomerBuilder {
        private Customer customer;

        private CustomerBuilder() {
            customer = new Customer();
        }

        public static CustomerBuilder aCustomer() {
            return new CustomerBuilder();
        }

        public CustomerBuilder setOrders(List<Order> orders) {
            customer.setOrders(orders);
            return this;
        }

        public CustomerBuilder setId(int id) {
            customer.setId(id);
            return this;
        }

        public CustomerBuilder setName(String name) {
            customer.setName(name);
            return this;
        }

        public CustomerBuilder setFamily(String family) {
            customer.setFamily(family);
            return this;
        }

        public CustomerBuilder setEmail(String email) {
            customer.setEmail(email);
            return this;
        }

        public CustomerBuilder setPassword(String password) {
            customer.setPassword(password);
            return this;
        }

        public CustomerBuilder setState(UserState state) {
            customer.setState(state);
            return this;
        }

        public CustomerBuilder setRegistrationDate(Date RegistrationDate) {
            customer.setRegistrationDate(RegistrationDate);
            return this;
        }

        public CustomerBuilder setBalance(double balance) {
            customer.setBalance(balance);
            return this;
        }

        public CustomerBuilder but() {
            return aCustomer().setOrders(customer.getOrders()).setId(customer.getId()).setName(customer.getName()).setFamily(customer.getFamily()).setEmail(customer.getEmail()).setPassword(customer.getPassword()).setState(customer.getState()).setRegistrationDate(customer.getRegistrationDate()).setBalance(customer.getBalance());
        }

        public Customer build() {
            return customer;
        }
    }
}
