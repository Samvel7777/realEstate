package com.example.realestate.service;

import com.example.realestate.model.CurrentUser;
import com.example.realestate.model.User;
import com.example.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(s);
        if (!byEmail.isPresent()) {
            throw new UsernameNotFoundException("user with " + s + " dose not exits");
        }
        return new CurrentUser(byEmail.get());
    }
}
