package com.net.repositories;

import com.net.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
//@Repository(value="userRepository")
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String username);
    User getUserById(long Id);

    List<User> findAll();

    @Query (
        value = "(SELECT SUM(points) FROM (SELECT COUNT(topic.id_user) AS points FROM topic WHERE topic.id_user = :id" +
                " UNION ALL SELECT 2 * COUNT(answer.id_user) AS points FROM answer WHERE answer.id_user = :id UNION ALL " +
                "SELECT 3 * COUNT(answer.id_user) AS points FROM answer WHERE answer.id_user = :id AND answer.useful = 1) t)",
        nativeQuery = true
    )
    Long getPoints(@Param("id") Long id);
}
