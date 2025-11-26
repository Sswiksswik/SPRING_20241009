package com.example.demo.controller; // 현재 폴더 위치

/*
 * 참고 (이동됨):
 * 아래의 컨트롤러 엔드포인트 메서드들은 프로젝트 곳곳에 분산되어 있던 것을
 * 이 파일의 BlogController 클래스 하나로 통합했습니다. 사용자가 요청하신 대로
 * 원본은 삭제하지 않고 원래 위치에 주석으로 보존해 두었습니다.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService; // injected service



    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> list = blogService.findAll();
        model.addAttribute("articles", list);
        return "article_list";
    }

    // simple board_list (kept for compatibility) — original no-arg variant
    @GetMapping("/board_list")
    public String board_list(Model model) {
        List<Board> list = blogService.findAll();
        model.addAttribute("articles", list);
        return "board_list";
    }

    // more feature-rich board_list with pagination/keyword/session check
    @GetMapping(value = "/board_list", params = {"page", "keyword"})
    public String board_list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/member_login";
        }
        System.out.println("세션 userId: " + userId);
        
        List<Board> boards = blogService.findAll();
        model.addAttribute("articles", boards);
        return "board_list";
    }

    @GetMapping("/article_edit/{id}")
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Article> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("article", list.get());
        } else {
            return "error";
        }
        return "article_edit";
    }

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list"; // 글 수정 이후 페이지
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }
}