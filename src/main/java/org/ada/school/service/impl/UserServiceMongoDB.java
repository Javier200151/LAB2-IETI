package org.ada.school.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ada.school.dto.UserDto;
import org.ada.school.model.User;
import org.ada.school.repository.UserDocument;
import org.ada.school.repository.UserRepository;
import org.ada.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceMongoDB implements UserService
{

    private final UserRepository userRepository;

    public UserServiceMongoDB(@Autowired UserRepository userRepository )
    {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        userRepository.save(new UserDocument(user.getId(), user.getName(), user.getEmail(), user.getLastName(), user.getCreatedAt()));
        return user;
    }

    @Override
    public User findById(String id) {
        UserDocument userDocument;
        userDocument=userRepository.findById(id).get();

        return new User(userDocument.getId(), userDocument.getName(), userDocument.getEmail(), userDocument.getLastName(), userDocument.getCreatedAt());
    }

    @Override
    public List<User> all() {
        List<User> users = new ArrayList<>();
        List<UserDocument> userDocuments = userRepository.findAll();

        userDocuments.forEach(userDocument -> {
                                                users.add(new User(userDocument.getId(), userDocument.getName(), userDocument.getEmail(), userDocument.getLastName(),userDocument.getCreatedAt()));
                                                });
        return users;
    }

    @Override
    public boolean deleteById(String id) {
        boolean wasDeleted;
        try{
            userRepository.deleteById(id);
            wasDeleted=true;
        }catch (Exception e){
            wasDeleted=false;
        }
        return wasDeleted;
    }

    @Override
    public User update(UserDto userDto, String id) {
        UserDocument userDocument = userRepository.findById(id).get();
        this.deleteById(id);
        userDocument.setName(userDto.getName());
        userDocument.setEmail(userDto.getEmail());
        userDocument.setLastName(userDto.getLastName());
        userRepository.save(userDocument);
        User newUser = new User(userDocument.getId(), userDocument.getName(), userDocument.getEmail(), userDocument.getLastName(),userDocument.getCreatedAt());
        return newUser;
    }
}