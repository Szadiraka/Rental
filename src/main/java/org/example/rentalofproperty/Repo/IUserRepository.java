package org.example.rentalofproperty.Repo;

import org.example.rentalofproperty.Models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface IUserRepository extends CrudRepository<UserModel,Long> {
    UserModel findByMail(String mail);
    UserModel findByMailAndPassword(String mail,String password);
}
