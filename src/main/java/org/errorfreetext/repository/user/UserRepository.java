package org.errorfreetext.repository.user;

import org.errorfreetext.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);
}
