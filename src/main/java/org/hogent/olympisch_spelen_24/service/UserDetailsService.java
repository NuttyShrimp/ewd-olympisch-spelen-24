package org.hogent.olympisch_spelen_24.service;

import lombok.NoArgsConstructor;
import org.hogent.olympisch_spelen_24.domain.Role;
import org.hogent.olympisch_spelen_24.domain.User;
import org.hogent.olympisch_spelen_24.repository.UserRepository;
import org.hogent.olympisch_spelen_24.utils.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;

import java.util.Collection;
import java.util.Collections;

@Service
@NoArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}
