package com.fastcampus.miniproject.config.auth;

import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

// http://localhost:8080/login 요청오면 동작한다.
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("[PrincipalDetailsService] - 로그인 요청");

//        Member member = memberRepository.findByLoginId(username).orElseThrow(NoSuchElementException::new);

        Member member = memberRepository.findByLoginId(username).orElseThrow();

        if (member == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        System.out.println("memberEntity" + member);
        return new PrincipalDetails(member);
    }
}

