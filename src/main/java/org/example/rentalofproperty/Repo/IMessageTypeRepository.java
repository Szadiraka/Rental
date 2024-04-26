package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.MessageType;
import org.springframework.data.repository.CrudRepository;

public interface IMessageTypeRepository  extends CrudRepository<MessageType,Long> {

    MessageType findByName(String name);
}
