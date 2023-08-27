package com.sparta.lv1memo.service;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

public class MemoService {
//    private final Map<Long, Memo> memoList = new HashMap<>();
//
//    public MemoResponseDto creatememo(MemoRequestDto requestDto) {
//
//        Memo memo = new Memo(requestDto);
//
//        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
//        memo.setId(maxId);
//
//        memoList.put(memo.getId(), memo);
//
//        MemoResponseDto responseDto = new MemoResponseDto(memo);
//
//        return responseDto;
//    }
//
//
//    public List<MemoResponseDto> getAllMemos() {
//        List<MemoResponseDto> responseList = memoList.values().stream().map(MemoResponseDto::new)
//                .sorted(Comparator.comparing(MemoResponseDto::getDate).reversed()).collect(Collectors.toList());
//
//        return responseList;
//    }
//
//
//    public Memo getMemo(Long id) {
//        if (memoList.containsKey(id)) {
//            Memo memo = memoList.get(id);
//            return memo;
//        } else {
//            throw new IllegalArgumentException("메모가 존재하지 않습니다.");
//        }
//    }
//
//
//    public Memo updateMemo(Long id, MemoRequestDto requestDto) {
//        if (memoList.containsKey(id)) {
//            Memo memo = memoList.get(id);
//            //수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
//            if (requestDto.getPassword().equals(memo.getPassword())) {
//
//                memo.update(requestDto);
//                return memo;
//            } else {
//                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            }
//
//        }else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//
//    }
//
//
//    public ResponseEntity<String> deleteMemo(Long id, MemoRequestDto requestDto) {
//        if (memoList.containsKey(id)) {
//            Memo memo = memoList.get(id);
//
//            // 삭제 시 비밀번호 확인
//            if (requestDto.getPassword().equals(memo.getPassword())) {
//                memoList.remove(id);
//                return ResponseEntity.ok("삭제 성공!");
//            } else {
//                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            }
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//    }
}
