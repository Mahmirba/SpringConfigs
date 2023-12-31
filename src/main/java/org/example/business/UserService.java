package org.example.business;

import lombok.AllArgsConstructor;
import org.example.exceptions.NotFoundException;
import org.example.model.UserRequest;
import org.example.model.UserResponse;
import org.example.repo.RoleEntity;
import org.example.repo.RoleRepository;
import org.example.repo.UserEntity;
import org.example.repo.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserEntity entity = new UserEntity();
        entity.setActive(true);
        entity.setDeleted(false);
        BeanUtils.copyProperties(request, entity);
        Optional<RoleEntity> roleEntity = roleRepository.findByName("Admin");
        if (roleEntity.isPresent())
            entity.getRoles().add(roleEntity.get());
        entity = userRepository.save(entity);
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public UserEntity findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
