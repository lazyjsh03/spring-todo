package jsh.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jsh.todolist.dto.LoginRequestDto;
import jsh.todolist.dto.SignUpRequestDto;
import jsh.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "사용자 회원가입 및 로그인 처리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "유저이름과 비밀번호로 회원가입 진행")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto requestDto){
        memberService.signUp(requestDto);
        return ResponseEntity.ok("회원가입 완료.");
    }

    @Operation(summary = "로그인", description = "유저이름과 비밀번호로 로그인을 진행하고 JWT를 발급")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto){
        String token = memberService.login(requestDto);
        return ResponseEntity.ok(token);
    }
}
