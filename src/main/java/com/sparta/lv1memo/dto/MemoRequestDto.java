package com.sparta.lv1memo.dto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MemoRequestDto {

    private String title;//제목
    private String username;//작성자명
    private String contents;//작성 내용
    private String password;//비밀번호
//    private LocalDateTime date;//작성 날짜




}
