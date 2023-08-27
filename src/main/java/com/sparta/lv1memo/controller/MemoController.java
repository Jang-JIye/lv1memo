package com.sparta.lv1memo.controller;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    //create
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);

        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        memoList.put(memo.getId(), memo);

        MemoResponseDto responseDto = new MemoResponseDto(memo);

        return responseDto;
    }

    //readAll
    @GetMapping("/memos")
    public List<MemoResponseDto> getAllMemo() {
        List<MemoResponseDto> responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return responseList;
    }

    //read
    @GetMapping("/memos/{id}")
    public Memo getMemo(@PathVariable Long id) {
        if (memoList.containsKey(id)) {
            Memo memo = memoList.get(id);
            return memo;
        } else {
            throw new IllegalArgumentException("메모가 존재하지 않습니다.");
        }
    }

    //update
    @PutMapping("/memos/{id}")
    public Memo updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        if (memoList.containsKey(id)) {
            Memo memo = memoList.get(id);
            //수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
            if (requestDto.getPassword().equals(memo.getPassword())) {

                memo.update(requestDto);
                return memo;
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

        }else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
        //수정 시 비밀번호 확인
    }
    //delete
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        //해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            Memo memo = memoList.get(id);

            // 삭제 시 비밀번호 확인
            if (requestDto.getPassword().equals(memo.getPassword())) {
                memoList.remove(id);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
