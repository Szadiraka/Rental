package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Message;
import org.springframework.data.repository.CrudRepository;

public interface IMessageRepository extends CrudRepository<Message,Long> {
}
