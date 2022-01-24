package ir.maktab.data.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Builder(setterPrefix = "set")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false,length = 10)
    private String suggestionCode;

    @CreationTimestamp
    private Date date;

    private double suggestedPrice;

    private double workHour;

    @Temporal(value = TemporalType.TIME)
    private Date startTime;

    @OneToOne
    private Specialist specialist;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Suggestion that = (Suggestion) o;
        return id != 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
