package com.erbatyr.crud_app_springboot.repositories;

import com.erbatyr.crud_app_springboot.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
