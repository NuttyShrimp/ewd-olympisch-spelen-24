package org.hogent.olympisch_spelen_24.utils;

import lombok.Getter;
import org.hogent.olympisch_spelen_24.domain.Role;
import org.hogent.olympisch_spelen_24.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal extends org.springframework.security.core.userdetails.User {
    User user;

    public UserPrincipal(User user) {
        super(user.getUsername(), user.getPassword(), convertAuthorities(user.getRole()));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> convertAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

}
