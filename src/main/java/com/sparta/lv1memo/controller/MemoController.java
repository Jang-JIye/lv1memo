package com.sparta.lv1memo.controller;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
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

    //read
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemo() {
        List<MemoResponseDto> responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return responseList;
    }

    //update
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        if (memoList.containsKey(id)) {
            Memo memo = memoList.get(id);

            memo.update(requestDto);
            return memo.getId();
        }else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
    //delete
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        //해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
