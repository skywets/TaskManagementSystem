package com.example.taskmanagementsystem.services.serviceImpl;

import com.example.taskmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        var email = userRepository.findByEmail(userEmail).orElseThrow(
                ()->new UsernameNotFoundException("There is no user with that name!"));
        return email;
    }
}
