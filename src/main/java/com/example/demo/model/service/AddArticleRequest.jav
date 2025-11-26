package com.example.demo.model.service;
import lombok.*; // 어노테이션 자동 생성
import com.example.demo.model.domain.Article;
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성
public class AddArticleRequest {
    private String title;
    private String content;
    public Article toEntity(){ // Article 객체 생성
        return Article.builder()
            .title(title)
            .content(content)
            .build();
        }
}

package com.example.demo.model.service;

import com.example.demo.model.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(this.title, this.content);
    }
}
