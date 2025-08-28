package jsh.todolist.service;

import jsh.todolist.domain.Member;
import jsh.todolist.dto.LoginRequestDto;
import jsh.todolist.dto.SignUpRequestDto;
import jsh.todolist.jwt.JwtUtill;
import jsh.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtill jwtUtill;

    @Transactional
    public void signUp(SignUpRequestDto requestDto) {
        if (memberRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 이름입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Member member = new Member(requestDto.getUsername(), encodedPassword);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto requestDto) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 2. 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 비밀번호가 일치하면 jwt 토큰 발금
        return jwtUtill.generateToken(member.getUsername());
    }
}
