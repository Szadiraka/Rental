package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IMessageRepository extends CrudRepository<Message,Long> {
    Iterable<Message> findBySenderIsLockedOrRecipientIsLocked(boolean isLocked1, boolean isLocked2);

    Iterable<Message> findAllByMessageType_IdAndRecipient_Id(long messageTypeId, long userId);
}
