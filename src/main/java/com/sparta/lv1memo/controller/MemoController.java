package com.sparta.lv1memo.controller;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

//    private final Map<Long, Memo> memoList = new HashMap<>();

    private final JdbcTemplate jdbcTemplate;

    public MemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //create
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        //RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        //DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO memo (title, username, contents, password, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, memo.getTitle());
            preparedStatement.setString(2, memo.getUsername());
            preparedStatement.setString(3, memo.getContents());
            preparedStatement.setString(4, memo.getPassword());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(memo.getDate()));

            return preparedStatement;
        }, keyHolder);

        //DB Insert 후 받아온 기본키 확인하기
        Long id = keyHolder.getKey().longValue();
        memo.setId(id);

        //Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    //readAll
    @GetMapping("/memos")
    public List<MemoResponseDto> getAllMemo() {
        //DB 조회
        String sql = "SELECT * FROM memo ORDER BY date DESC ";

        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String title = rs.getNString("title");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                String password = rs.getString("password");
                Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                return new MemoResponseDto(id, title, username, contents, password, date);
            }

        });
    }


    //read
    @GetMapping("/memos/{id}")
    public Memo getMemo(@PathVariable Long id) {
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = findById(id);
        if (memo != null) {
            return memo;
        } else {
            throw new IllegalArgumentException("메모가 존재하지 않습니다.");
        }
    }

    //update
    @PutMapping("/memos/{id}")
    public ResponseEntity<String> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = findById(id);
        if (memo != null) {
            //memo 수정
            //수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
            if (requestDto.getPassword().equals(memo.getPassword())) {

                String sql = "UPDATE memo SET title = ?, username = ?, contents = ?, password = ? WHERE id = ?";
                jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getUsername(), requestDto.getContents(), requestDto.getPassword(), id);

//                return id;
                return ResponseEntity.ok("수정 성공!");

            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
        //수정 시 비밀번호 확인
    }

    //delete
    @DeleteMapping("/memos/{id}")
    public ResponseEntity<String> deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        //해당 메모가 DB에 존재하는지 확인
        Memo memo = findById(id);
        if (memo != null) {
            // 삭제 시 비밀번호 확인
            if (requestDto.getPassword().equals(memo.getPassword())) {
                // memo 삭제
                String sql = "DELETE FROM memo WHERE id = ?";
                jdbcTemplate.update(sql, id);

                return ResponseEntity.ok("삭제 성공!");
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private Memo findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM memo WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Memo memo = new Memo();
                memo.setTitle(resultSet.getString("title"));
                memo.setUsername(resultSet.getString("username"));
                memo.setContents(resultSet.getString("contents"));
                memo.setPassword(resultSet.getString("password"));
                return memo;
            } else {
                return null;
            }
        }, id);
    }
}
