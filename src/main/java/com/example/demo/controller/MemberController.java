package com.example.demo.controller;

import java.util.UUID;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.domain.Member;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 가입 페이지 연결
    @GetMapping("/join_new")
    public String join_new() {
        return "join_new"; // join_new.html
    }

    // 회원 가입 저장
    @PostMapping("/api/members")
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end"; // join_end.html
    }

    // 로그인 체크 (기본 버전)
    @PostMapping("/api/login_check")
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model, HttpSession session) {
        try {
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID로 세션 생성
            session.setAttribute("userId", sessionId); // 세션에 userId 저장
            model.addAttribute("member", member); // 모델에 회원 정보 추가
            return "member_info"; // 로그인 성공 시 회원 정보 페이지
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage()); // 에러 메시지 추가
            return "member_login"; // 로그인 페이지로 이동
        }
    }



    // ORIG: 세션/쿠키 초기화 후 새 세션 생성 방식의 로그인 처리 (원본 보존)
    @PostMapping("/api/login_check") // 아이디, 패스워드 로그인 체크
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model, HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
            if (session != null) {
                session.invalidate(); // 기존 세션 무효화
                Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID 초기화
                cookie.setPath("/"); // 쿠키 경로
                cookie.setMaxAge(0); // 쿠키 삭제(0으로 설정)
                response.addCookie(cookie); // 응답으로 쿠키 전달
            }
            session = request2.getSession(true); // 새로운 세션 생성
            // 이후 memberService.loginCheck 호출 등 로그인 성공 처리 필요
        } catch (Exception ex) {
            // 예외 처리(원본에는 상세 처리가 없음)
        }
    }
}

@GetMapping("/member_login") // 로그인 페이지 연결
public String member_login() {
return "login"; // .HTML 연결
}
@PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
public String checkMembers(@ModelAttribute AddMemberRequest request, Model model) {
try {
    Member member = memberService.loginCheck(request.getEmail(), request.getPassword()); // 패스워드 반환
    model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
} catch (IllegalArgumentException e) {
    model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
    return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
}
}

//     String email = request.getEmail(); // 이메일 얻기
//     session.setAttribute("userId", sessionId); // 아이디 이름 설정
//     session.setAttribute("email", email); // 이메일 설정
//     model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
//     String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
//     String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인

//     model.addAttribute("startNum", startNum);
//     model.addAttribute("email", email); // 로그인 사용자(이메일)

    
//     @GetMapping("/join_new")
//     public String join_new() { return "join_new"; }
//     @PostMapping("/api/members")
//     public String addmembers(@ModelAttribute AddMemberRequest request) { memberService.saveMember(request); return "join_end"; }
    
// }