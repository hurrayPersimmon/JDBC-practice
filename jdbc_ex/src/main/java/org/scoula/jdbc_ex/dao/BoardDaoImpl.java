package org.scoula.jdbc_ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.BoardVO;

public class BoardDaoImpl implements BoardDao {

    private final Connection conn = JDBCUtil.getConnection();

    private final String BOARD_INSERT =
        "insert into board(title, content, writer) values(?, ?, ?)";

    private final String BOARD_LIST =
        "select no, title, content, writer, reg_date, update_date from board order by no desc";

    private final String BOARD_GET =
        "select no, title, content, writer, reg_date, update_date from board where no = ?";

    private final String BOARD_UPDATE =
        "update board set title = ?, content = ?, writer = ? where no = ?";

    private final String BOARD_DELETE =
        "delete from board where no = ?";

    private BoardVO map(ResultSet rs) throws SQLException {
        BoardVO board = new BoardVO();

        board.setNo(rs.getInt("NO"));
        board.setTitle(rs.getString("TITLE"));
        board.setContent(rs.getString("CONTENT"));
        board.setWriter(rs.getString("WRITER"));

        if (rs.getTimestamp("REG_DATE") != null) {
            board.setRegDate(rs.getTimestamp("REG_DATE").toLocalDateTime());
        }

        if (rs.getTimestamp("UPDATE_DATE") != null) {
            board.setUpdateDate(rs.getTimestamp("UPDATE_DATE").toLocalDateTime());
        }

        return board;
    }


    @Override
    public int create(BoardVO board) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(BOARD_INSERT)) {

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getWriter());

            return pstmt.executeUpdate();
        }
    }

    @Override
    public List<BoardVO> getList() throws SQLException {
        List<BoardVO> boardList = new ArrayList<>();

        try (
            PreparedStatement pstmt = conn.prepareStatement(BOARD_LIST);
            ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                BoardVO board = map(rs);
                boardList.add(board);
            }
        }

        return boardList;
    }

    @Override
    public Optional<BoardVO> get(int no) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(BOARD_GET)) {

            pstmt.setInt(1, no);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int update(BoardVO board) throws SQLException {
        //        "update board set title = ?, content = ?, writer = ? where no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(BOARD_UPDATE)) {

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getWriter());
            pstmt.setInt(4, board.getNo());

            return pstmt.executeUpdate();
        }
    }

    @Override
    public int delete(int no) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(BOARD_DELETE)) {

            pstmt.setInt(1, no);

            return pstmt.executeUpdate();
        }
    }
}
