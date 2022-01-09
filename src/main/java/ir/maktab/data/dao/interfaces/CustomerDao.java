package ir.maktab.data.dao.interfaces;


import ir.maktab.data.model.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CustomerDao extends PagingAndSortingRepository<Customer, Integer> {


    List<Customer> findCustomerById(int id);

    List<Customer> findCustomerByEmail(String email);

    List<Customer> findCustomerByEmailAndPassword(String email , String password);

    @Transactional
    @Modifying
    @Query(value = "update Customer c set c.name=:name,c.family=:family,c.email=:email,c.password=:password,c.balance=:balance where c.id=:id")
    void update(@Param("name") String name,@Param("family") String family,@Param("email") String email,@Param("password") String password,@Param("balance") double balance, @Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "update Customer c set c.password=:newPassword where c.email=:email")
    void updatePasswordByEmail(@Param("newPassword") String newPassword, @Param("email") String email);

    List<Customer> findCustomerByNameOrFamilyOrEmail(String name,String family,String email);
}
