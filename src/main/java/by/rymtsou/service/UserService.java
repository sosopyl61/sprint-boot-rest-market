package by.rymtsou.service;

import by.rymtsou.model.User;
import by.rymtsou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public Optional<List<User>> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Optional<User> createUser(User user) {
        Optional<Long> userId = userRepository.createUser(user);
        if (userId.isPresent()) {
            return getUserById(userId.get());
        }
        return Optional.empty();
    }

    public Optional<User> updateUser(User user) {
        Boolean result = userRepository.updateUser(user);
        if (result) {
            return getUserById(user.getId());
        }
        return Optional.empty();
    }

    public Optional<User> deleteUser(Long id) {
        Boolean result = userRepository.deleteUser(id);
        if (result) {
            return getUserById(id);
        }
        return Optional.empty();
    }
}
