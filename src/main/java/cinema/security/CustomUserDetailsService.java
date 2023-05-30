package cinema.security;

import cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cinema.model.User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        User.UserBuilder builder = User.withUsername(username);
        builder.password(user.getPassword());
        builder.authorities(user.getRoles().stream()
                .map(i -> i.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
