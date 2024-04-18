package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Country;
import org.springframework.data.repository.CrudRepository;

public interface ICountryRepository extends CrudRepository<Country,Long> {
    Country findByName(String name);
}
