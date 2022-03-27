package com.shopme.admin.user;

import com.shopme.common.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);

    Integer countByRecid(Integer recid);

    @Query("UPDATE User u SET u.enable = ?2 WHERE u.recid = ?1")
    @Modifying
    void updateEnableStatus(Integer recid, boolean enable);
}
