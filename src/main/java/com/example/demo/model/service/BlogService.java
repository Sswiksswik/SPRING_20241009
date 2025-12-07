package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page; // 추가됨
import org.springframework.data.domain.Pageable; // 추가됨
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
@SuppressWarnings("null")
@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
public class BlogService {
    
    private final BlogRepository blogRepository; // 블로그 리포지토리
    private final BoardRepository boardRepository; // 게시판 리포지토리 (상단으로 이동)

    // public List<Article> findAll() { // 게시판 전체 목록 조회
    //  return blogRepository.findAll();
    // }

    // 블로그 글 저장 (반환 타입을 Board에서 Article로 수정)
    public Article save(AddArticleRequest request) {
        // DTO가 없는 경우 이곳에 직접 구현 가능
        // public ResponseEntity<Article> addArticle(@RequestParam String title, @RequestParam String content) {
        // Article article = Article.builder()
        // .title(title)
        // .content(content)
        // .build();
        return blogRepository.save(request.toEntity());
    }

    // public Optional<Article> findById(Long id) { // 게시판 특정 글 조회
    // return blogRepository.findById(id);
    // }

    // 블로그 글 수정
    public void update(Long id, AddArticleRequest request) {
        Optional<Article> optionalArticle = blogRepository.findById(id); // 단일 글 조회
        optionalArticle.ifPresent(article -> { // 값이 있으면
            article.update(request.getTitle(), request.getContent()); // 값을 수정
            blogRepository.save(article); // 수정된 게시글 저장
        });
    }

    // @PutMapping("/api/article_edit/{id}")
    // public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    // blogService.update(id, request);
    // return "redirect:/article_list"; // 글 수정 이후 .html 연결
    // }

    // public void update(Long id, AddArticleRequest request) {
    //  Article article = blogRepository.findById(id)
    //          .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다. id=" + id));
    //  article.update(request.getTitle(), request.getContent());
    //  blogRepository.save(article);
    // }

    // 블로그 글 삭제
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    // 게시판 전체 목록 조회
    public List<Board> findAll() { 
        return boardRepository.findAll();
    }

    // 게시판 특정 글 조회 (실제 DB 조회로 수정)
    public Optional<Board> findById(Long id) { 
        return boardRepository.findById(id);
    }

    // 페이징 처리된 목록 조회
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 키워드 검색
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

}