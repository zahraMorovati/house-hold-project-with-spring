package model.entity;

import lombok.*;
import model.enums.UserState;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String family;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, length = 8)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserState state;
    @Temporal(TemporalType.TIMESTAMP)
    private Date RegistrationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != 0 && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
