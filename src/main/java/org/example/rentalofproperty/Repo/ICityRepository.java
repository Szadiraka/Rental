package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.City;
import org.springframework.data.repository.CrudRepository;

public interface ICityRepository extends CrudRepository<City,Long> {
    City findByName(String name);

    Iterable<City> findAllByName(String name);
}
