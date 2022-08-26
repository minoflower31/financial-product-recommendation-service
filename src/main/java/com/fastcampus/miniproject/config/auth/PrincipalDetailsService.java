package com.fastcampus.miniproject.config.auth;

import com.fastcampus.miniproject.entity.EmptyEntity;
import com.fastcampus.miniproject.repository.EmptyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final EmptyRepository emptyRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        EmptyEntity targetMember = emptyRepository.findByName(name);
        return new PrincipalDetails(targetMember);
    }
}
