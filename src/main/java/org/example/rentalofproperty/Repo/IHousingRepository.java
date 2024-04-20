package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.HousingType;
import org.springframework.data.repository.CrudRepository;

public interface IHousingRepository extends CrudRepository<HousingType,Long> {
}
