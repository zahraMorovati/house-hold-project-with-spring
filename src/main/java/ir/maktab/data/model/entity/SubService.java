package ir.maktab.data.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Service service;
    @Column(unique = true,length = 30)
    private String subServiceName;

    @Lob
    private String explanations; //توضیحات
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Specialist> specialistList = new ArrayList<>();
}
