package org.scoula.jdbc_ex.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.scoula.jdbc_ex.domain.BoardVO;

public interface BoardDao {
    // 게시글 등록
    int create(BoardVO board) throws SQLException;

    // 게시글 전체 조회
    List<BoardVO> getList() throws SQLException;

    // 게시글 1건 조회
    Optional<BoardVO> get(int no) throws SQLException;

    // 게시글 수정
    int update(BoardVO board) throws SQLException;

    // 게시글 삭제
    int delete(int no) throws SQLException;

}
