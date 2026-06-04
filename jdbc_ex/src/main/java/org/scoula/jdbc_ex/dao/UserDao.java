package org.scoula.jdbc_ex.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.scoula.jdbc_ex.domain.UserVO;

public interface UserDao {

    int create(UserVO user) throws SQLException;

    List<UserVO> getList() throws SQLException;

    Optional<UserVO> get(String id) throws SQLException;

    int update(UserVO user) throws SQLException;

    int delete(String id) throws SQLException;


}
