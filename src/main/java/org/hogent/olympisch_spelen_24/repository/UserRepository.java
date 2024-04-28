package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
