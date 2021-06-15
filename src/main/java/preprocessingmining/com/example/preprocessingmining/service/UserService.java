package preprocessingmining.com.example.preprocessingmining.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.User;
import preprocessingmining.com.example.preprocessingmining.repository.UserRepository;

import java.io.Serializable;
import java.util.UUID;

@Service
public class UserService implements Serializable {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByID(@NotNull String userId) {
        return userRepository.findById(userId)
                .orElse(null);
    }

    public User findUserByMail(@NotNull String userMail) {
        return userRepository.findByMail(userMail);
    }

    public User save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
