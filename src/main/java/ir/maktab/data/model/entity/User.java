package ir.maktab.data.model.entity;

import lombok.*;
import ir.maktab.data.model.enums.UserState;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
@EqualsAndHashCode
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
    @Column(unique = true, nullable = false,length = 25)
    private String email;
    @Column(nullable = false, length = 8)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserState state;
    @CreationTimestamp
    private Date RegistrationDate;
    private double balance;

}
