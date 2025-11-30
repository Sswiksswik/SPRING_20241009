package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.model.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
}
List<Borad>findAll();
// @Repository
// public interface BlogRepository extends JpaRepository<Article, Long>{
// }