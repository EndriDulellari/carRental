package com.rental.luca.security.authentication;

import com.rental.luca.exception.RecordNotFoundException;
import com.rental.luca.generic.GenericService;
import com.rental.luca.security.registration.token.ConfirmationToken;
import com.rental.luca.security.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService extends GenericService implements UserDetailsService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    public MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("User by email " + email + " not found!"));
    }

    public boolean isAuthenticated(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public String signUpUser(User user) {
        boolean userExists = repository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already taken!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        repository.save(user);

        //TODO: Send confirmation token
        String token = UUID.randomUUID().toString();
        // TODO: Change expiration token to 30
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(3000), user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableUser(String email) {

        Map<String, String> filters = new HashMap<>();
        filters.put("email", email);
        Query query = generateQuery(filters);
        User user = mongoTemplate.find(query, User.class).get(0);
        if (user != null) {
            user.setEnabled(true);
            user.setCredentialsNonExpired(true);
            repository.save(user);
        }
    }


    public User addUser(User user) {
        return repository.save(user);
    }
}
