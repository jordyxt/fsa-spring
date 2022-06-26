package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import es.tfm.fsa.domain.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Qualifier("fsa.users")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserPersistence userPersistence;

    @Autowired
    public UserDetailsServiceImpl(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userPersistence.readByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found. " + username));
        return this.userBuilder(user.getUsername(), user.getPassword(), new Role[]{Role.AUTHENTICATED}, user.getActive());
    }

    private org.springframework.security.core.userdetails.User userBuilder(String username, String password, Role[] roles,
                                                                           boolean active) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.withPrefix()));
        }
        return new org.springframework.security.core.userdetails.User(username, password, active, true,
                true, true, authorities);
    }
}
