package com.rental.luca.security.registration.token;

import com.rental.luca.exception.RecordNotFoundException;
import com.rental.luca.generic.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ConfirmationTokenService extends GenericService {

    private final ConfirmationTokenRepository repository;
    public MongoTemplate mongoTemplate;

    public void saveConfirmationToken(ConfirmationToken token) {
        repository.save(token);
    }

    public ConfirmationToken findConfirmationTokenByToken(String token) {
        return repository.findConfirmationTokenByToken(token).orElseThrow(() -> new RecordNotFoundException("Token not found!"));
    }


    public void setConfirmedAt(String token) {
        Map<String, String> filters = new HashMap<>();
        filters.put("token", token);
        Query query = generateQuery(filters);
        ConfirmationToken confirmationToken = mongoTemplate.find(query, ConfirmationToken.class).get(0);
        if (confirmationToken != null) {
            confirmationToken.setConfirmedAt(LocalDateTime.now());
            repository.save(confirmationToken);
        }
    }
}
