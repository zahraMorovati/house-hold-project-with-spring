package model.entity;

import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String family;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, length = 8)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Manager manager = (Manager) o;
        return id != 0 && Objects.equals(id, manager.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
