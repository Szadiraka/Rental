package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Role;
import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<Role,Long> {
    Role findByName(String name);

    Iterable<Role> findByNameNot(String name);
}
