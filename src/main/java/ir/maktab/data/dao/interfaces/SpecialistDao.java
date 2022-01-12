package ir.maktab.data.dao.interfaces;

import ir.maktab.data.model.entity.Specialist;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface SpecialistDao extends PagingAndSortingRepository<Specialist, Integer> {


    List<Specialist> findSpecialistByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update Specialist s set s.name=:name,s.family=:family,s.email=:email,s.password=:password,s.balance=:balance where s.id=:id")
    void update(@Param("name") String name, @Param("family") String family, @Param("email") String email, @Param("password") String password, @Param("balance") double balance, @Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "update Specialist s set s.password=:newPassword where s.email=:email")
    void updatePasswordByEmail(@Param("newPassword") String newPassword, @Param("email") String email);

    List<Specialist> findSpecialistByNameOrFamilyOrEmail(String name,String family,String email);

    @Transactional
    @Modifying
    @Query(value = "update Specialist s set s.image=:image where s.id=:id")
    void updateSpecialistImage(@Param("image") byte[] image, @Param("id") int id);
}
