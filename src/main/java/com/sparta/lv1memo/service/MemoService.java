package com.sparta.lv1memo.service;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import com.sparta.lv1memo.repository.MemoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MemoService {
    private final JdbcTemplate jdbcTemplate;

    public MemoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        //RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        //DB 저장
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);

        //Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }


    public List<MemoResponseDto> getMemos() {
        //DB 조회
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findAll();
    }


    public Memo getMemo(Long id) {
        //해당 메모가 DB에 존재하는지 확인
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo memo = memoRepository.findById(id);
        if (memo != null) {
            return memo;
        } else {
            throw new IllegalArgumentException("메모가 존재하지 않습니다.");
        }
    }

    public ResponseEntity<String> updateMemo(Long id, MemoRequestDto requestDto) {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if (memo != null) {
            //memo 수정
            memoRepository.update(id, requestDto);
            //수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
            if (requestDto.getPassword().equals(memo.getPassword())) {
//                return id;
                return ResponseEntity.ok("수정 성공!");

            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public ResponseEntity<String> deleteMemo(Long id, MemoRequestDto requestDto) {
        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if (memo != null) {
            // 삭제 시 비밀번호 확인
            if (requestDto.getPassword().equals(memo.getPassword())) {
                // memo 삭제
                memoRepository.deleteMemo(id, requestDto);

                return ResponseEntity.ok("삭제 성공!");
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }

    }


}
