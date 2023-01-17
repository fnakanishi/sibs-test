package org.example.service;

import org.apache.commons.lang3.StringUtils;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public Boolean existByName(String name) {
        return repository.existsByName(name);
    }

    private User save(User user) {
        return repository.save(user);
    }

    public User save(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return save(user);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User update(User user, String name, String email) {
        if (StringUtils.isNotBlank(name)) {
            user.setName(name);
        }
        if (StringUtils.isNotBlank(email)) {
            user.setEmail(email);
        }

        return save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
