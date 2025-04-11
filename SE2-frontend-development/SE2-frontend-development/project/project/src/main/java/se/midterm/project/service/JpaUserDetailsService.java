package se.midterm.project.service;

import se.midterm.project.model.MyUserDetail;
import se.midterm.project.model.User;
import se.midterm.project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(MyUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void save(User user) {
        userRepository.save(user);
    }

}