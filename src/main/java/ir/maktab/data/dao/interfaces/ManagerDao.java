package ir.maktab.data.dao.interfaces;

import ir.maktab.data.entity.Manager;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ManagerDao extends PagingAndSortingRepository<Manager,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update Manager m set m.name=:name , m.family=:family , m.email=:email,m.password=:password where m.id=:id")
    void updateById(@Param("name") String name,@Param("family") String family , @Param("email") String email,@Param("password") String password,@Param("id")int id);

    @Transactional
    @Modifying
    @Query(value = "update Manager m set m.password=:password  where m.email=:email")
    void updatePasswordByEmail( @Param("password") String password,@Param("email")String email);

    List<Manager> findManagerByEmail(String email);

    //todo return pagable
    List<Manager> findManagerByNameOrFamilyOrEmail(String name,String family,String email);

    List<Manager> findManagerByEmailAndPassword(String email, String password);


}
