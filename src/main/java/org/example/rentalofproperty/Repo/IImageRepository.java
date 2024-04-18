package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Image;
import org.springframework.data.repository.CrudRepository;

public interface IImageRepository extends CrudRepository<Image,Long> {
}
