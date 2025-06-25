package com.pritam.journalApp.service;

import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class UserService {


    @Autowired
    private UserRepository ur;

//    @Autowired
//    private PasswordEncoder passwordEncoder;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            ur.save(user);
            return true;

        } catch (Exception e) {

            log.error("Error while saving new user {} : {} " ,user.getUserName() , e.getMessage());
            return false;
        }
    }

    public void saveUser(User user) {
        ur.save(user);
    }

    public List<User> getAll() {
        return ur.findAll();
    }

    public Optional<User> getById(ObjectId id) {
        return ur.findById(id);
    }

    public void deleteId(ObjectId id) {
        ur.deleteById(id);
    }

    public void deleteByUserName(String userName) {
        ur.deleteByUserName(userName);
    }

    public User getByUserName(String userName) {
        return ur.findByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        ur.save(user);
    }
}
/*
* CONTROLLER -->
*           SERVICE -->
*               REPOSITORY .
*
*/
