package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Housing;
import org.springframework.data.repository.CrudRepository;

public interface IHousingRepository extends CrudRepository<Housing,Long> {
}
