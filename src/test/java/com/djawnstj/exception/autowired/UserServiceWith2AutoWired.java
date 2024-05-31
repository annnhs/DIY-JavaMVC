package com.djawnstj.exception.autowired;

import com.djawnstj.mvcframework.annotation.AutoWired;
import com.djawnstj.mvcframework.annotation.Service;
import com.djawnstj.common.User;
import com.djawnstj.common.UserRepository;

import java.util.List;

@Service
public class UserServiceWith2AutoWired {
    private final UserRepository userRepository;

    @AutoWired
    public UserServiceWith2AutoWired() {
        this.userRepository = new UserRepository();
    }

    @AutoWired
    public UserServiceWith2AutoWired(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public void register(final String id, final String pw) {
        this.userRepository.save(id, new User(id, pw));
    }

    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }
}
