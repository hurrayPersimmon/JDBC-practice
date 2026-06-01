package org.scoula.jdbc_ex;

import java.sql.SQLException;
import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.dao.BoardDao;
import org.scoula.jdbc_ex.dao.BoardDaoImpl;
import org.scoula.jdbc_ex.domain.BoardVO;

public class BoardMain {

    private static final BoardDao BOARD_DAO = new BoardDaoImpl();
    public static void main(String[] args) throws SQLException {
//        System.out.println(JDBCUtil.getConnection());

        BOARD_DAO.create(new BoardVO("금요일이다.", "내용","성훈"));

        for(BoardVO v : BOARD_DAO.getList()){
            System.out.println(v);
        }
        System.out.println(BOARD_DAO.get(15));

        System.out.println(BOARD_DAO.delete(10));
        BoardVO board = new BoardVO("월요일이다. ㅜㅜ", "내용","성훈");
        board.setNo(13);
        BOARD_DAO.update(board);



    }

}
