package com.sparta.lv1memo.entity;

import com.sparta.lv1memo.dto.MemoRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Memo {
    @Id
    private Long id;
    private String title;//제목
    private String username;//작성자명
    private String contents;//작성 내용
    private String password;//비밀번호
    private LocalDateTime date;//작성 날짜

    public Memo(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
        this.date = LocalDateTime.now();
    }

    public void update(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @Id
//    public Long getId() {
//        return id;
//    }
}
