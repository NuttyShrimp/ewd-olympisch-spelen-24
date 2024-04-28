package org.hogent.olympisch_spelen_24.config;

import org.hogent.olympisch_spelen_24.domain.Role;
import org.hogent.olympisch_spelen_24.domain.User;
import org.hogent.olympisch_spelen_24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitDataConfig implements CommandLineRunner {
    private BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User(1L, "user", pwEncoder.encode("password"), Role.USER);
        User admin = new User(2L, "admin", pwEncoder.encode("admin"), Role.ADMIN);

        userRepository.saveAll(List.of(user, admin));
    }
}
