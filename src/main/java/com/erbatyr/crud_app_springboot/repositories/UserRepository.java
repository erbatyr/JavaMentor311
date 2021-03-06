package com.erbatyr.crud_app_springboot.repositories;

import com.erbatyr.crud_app_springboot.models.Role;
import com.erbatyr.crud_app_springboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.email = :email")
    User loadUserByUsername(@Param("email") String username);

//    @Transactional
//    @Modifying
//    @Query("update User u set u.firstName = :firstName, u.lastName = :lastName, u.email = :email, u.password = :password, u.roles = :roles where u.id = :id")
//    void editUserData(@Param("id") Long id, @Param("email") String username, @Param("password") String password, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("roles") Set<Role> roles);
}