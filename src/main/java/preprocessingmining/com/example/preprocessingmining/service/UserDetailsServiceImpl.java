package preprocessingmining.com.example.preprocessingmining.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        var user = userService.findUserByMail(mail);
        if (user == null) {
            throw new UsernameNotFoundException(mail);
        }
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), Collections.emptyList());
    }
}
