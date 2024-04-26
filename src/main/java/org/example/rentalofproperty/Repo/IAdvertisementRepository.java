package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Advertisement;
import org.springframework.data.repository.CrudRepository;

public interface IAdvertisementRepository extends CrudRepository<Advertisement,Long> {
    Iterable<Advertisement> findByLandLordIdAndIsDeletedFalseOrderByDateDesc(Long userId);
    Iterable<Advertisement> findByIsModeratedFalseAndIsDeletedFalse();
    Iterable<Advertisement> findByIsModeratedTrueAndIsDeletedFalse();
}

