package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Order;
import org.springframework.data.repository.CrudRepository;

public interface IOrderRepository extends CrudRepository<Order,Long> {
}
