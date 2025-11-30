package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "Board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content; // 초기값 설정 제거
    
    // 추가하려던 필드들을 클래스 내부로 통합
    @Column(name = "user", nullable = false)
    private String user;
    
    @Column(name = "newdate", nullable = false)
    private String newdate;
    
    @Column(name = "count", nullable = false)
    private String count;
    
    @Column(name = "likec", nullable = false)
    private String likec;


    public void update(String title, String content) { 
        this.title = title;
        this.content = content;
    
    }
    
    // update 메소드에 추가된 필드를 반영하여 오버로드 (선택적)
    public void update(String title, String content, String user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
