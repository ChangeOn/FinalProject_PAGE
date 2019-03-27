package com.page.user.dao;

import com.page.user.dto.LoginDTO;
import com.page.user.dto.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String NAMESPACE = "com.page.mappers.users.UserMapper";

    private final SqlSession sqlSession;

    @Inject
    public UserDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 로그인 처리
    @Override
    public UserVO login(LoginDTO loginDTO) throws Exception {
        return sqlSession.selectOne(NAMESPACE + ".login", loginDTO);
    }

    // 로그인 유지 처리
    @Override
    public void keepLogin(String user_id, String session_id, Date session_limit) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", user_id);
        paramMap.put("session_id", session_id);
        paramMap.put("session_limit", session_limit);

        sqlSession.update(NAMESPACE + ".keepLogin", paramMap);
    }

    // 세션키 검증
    @Override
    public UserVO checkUserWithSessionKey(String value) throws Exception {
        return sqlSession.selectOne(NAMESPACE + ".checkUserWithSessionKey", value);
    }

    @Override
    public void register(UserVO userVO) throws Exception {
        sqlSession.insert(NAMESPACE + ".register", userVO);
    }

    @Override
    public String getUserPw(String userId) throws Exception {

        return sqlSession.selectOne(NAMESPACE + ".getUserPw", userId);
    }

    @Override
    public void userInfoUpdate(UserVO userVO) throws Exception {
        sqlSession.update(NAMESPACE + ".userInfoUpdate", userVO);
    }

    @Override
    public void userPwUpdate(String userId, String newUserPw) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("newUserPw", newUserPw);
        sqlSession.update(NAMESPACE + ".userPwUpdate", paramMap);
    }

    @Override
    public void userImgUpdate(String userId, String userImg) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("userImg", userImg);
        sqlSession.update(NAMESPACE + ".userImgUpdate", paramMap);
    }
}
