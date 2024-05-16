package org.hogent.olympisch_spelen_24.repository;

import org.hogent.olympisch_spelen_24.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
