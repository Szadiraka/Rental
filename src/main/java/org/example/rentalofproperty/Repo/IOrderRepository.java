package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.OrderModel;
import org.springframework.data.repository.CrudRepository;

public interface IOrderRepository extends CrudRepository<OrderModel,Long> {
    boolean existsByAdvertisementIdAndStatusId(Long advertisementId, Long statusId);
    Iterable<OrderModel> findByStatusId(Long statusId);
}
