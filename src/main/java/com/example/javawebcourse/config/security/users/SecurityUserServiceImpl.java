package com.example.javawebcourse.config.security.users;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
class SecurityUserServiceImpl implements SecurityUserService, UserDetailsService {

    private final SecurityUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public SecurityUser save(SecurityUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var created = userRepository.saveAndFlush(user); // flush so entity is audited
        eventPublisher.publishEvent(new SecurityUserCreatedEvent(created));
        return created;
    }

    @Override
    public SecurityUser update(SecurityUser user) {
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("User with id " + user.getId() + " not found");
        }
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public boolean isValidLogin(String username, String password) {
        return findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Optional<SecurityUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<SecurityUser> findById(Long id) {
        return userRepository.findById(id);
    }
}
