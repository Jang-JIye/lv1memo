package com.sparta.lv1memo.repository;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class MemoRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Memo save(Memo memo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //DB 저장
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

        return memo;
    }


    public List<MemoResponseDto> findAll() {
        //DB 저장
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

    public void update(Long id, MemoRequestDto requestDto) {

        String sql = "UPDATE memo SET title = ?, username = ?, contents = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getUsername(), requestDto.getContents(), requestDto.getPassword(), id);

    }


    public void deleteMemo(Long id, MemoRequestDto requestDto) {
        String sql = "DELETE FROM memo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Memo findById(Long id) {
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
