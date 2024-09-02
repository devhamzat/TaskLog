package org.hae.tasklogue.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hae.tasklogue.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return applicationUserRepository.findApplicationUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
