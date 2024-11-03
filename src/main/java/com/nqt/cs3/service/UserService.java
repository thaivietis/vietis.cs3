package com.nqt.cs3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqt.cs3.domain.User;
import com.nqt.cs3.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // SAVE
    public User save(User user) {
        return userRepository.save(user);
    }

    // GET
    public User findById(long id) {
        if (!userRepository.findById(id).isPresent()) {
            return null;
        }
        return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    // UPDATE
    public User update(User user) {
        User currentUser = this.userRepository.findById(user.getId()).get();
        if (currentUser != null) {
            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(user.getPassword());
            return userRepository.save(currentUser);
        }
        return null;
    }

    // DELETE
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
