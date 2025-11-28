package com.example.demo.controller; // 현재 폴더 위치
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

// import lombok.RequiredArgsConstructor;

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
        List<Article> list = blogService.findAll();
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
        
        List<Article> boards = blogService.findAll();
        model.addAttribute("articles", boards);
        return "board_list";
    }

@GetMapping("/article_edit/{id}")
public String article_edit(Model model, @PathVariable Long id) { // 파라미터 정리
    Optional<Article> list = blogService.findById(id); //선택한게시판글
    if (list.isPresent()) {
        model.addAttribute("article", list.get()); 
    } else {
        return "error";
    }
        return "redirect:/article_list"; // .HTML 연결
        }
        
    //     	@PutMapping("/api/article_edit/{id}")
	// public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
	// blogService.update(id, request);
	// return "redirect:/article_list"; // 글 수정 이후 .html 연결
	// }
    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        // 💡 저장 후 목록 페이지로 리다이렉트
        return "redirect:/article_list";
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
@ControllerAdvice
    public static class GlobalExceptionControllerAdvice {
        
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
            
            
            return "error_page/article_error";
        }
    }
}