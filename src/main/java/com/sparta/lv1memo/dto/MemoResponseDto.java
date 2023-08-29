package com.sparta.lv1memo.dto;

import com.sparta.lv1memo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private Long id;
    private String title;//제목
    private String username;//작성자명
    private String contents;//작성 내용
    private String password;//비밀번호
//    private LocalDateTime date;//작성 날짜
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.password = memo.getPassword();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }
}
