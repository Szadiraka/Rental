package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Status;
import org.springframework.data.repository.CrudRepository;

public interface IStatusRepository extends CrudRepository<Status,Long> {
    Status findByName(String name);
}
