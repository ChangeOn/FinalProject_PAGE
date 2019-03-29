package com.page.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.page.model.dto.PageVO;
import com.page.model.dto.PageWithUserVO;
import com.page.model.dto.Page_CreateVO;
import com.page.user.dto.UserVO;

@Repository
public class PageDAOImpl implements PageDAO {

    private static final String NAMESPACE = "com.page.mappers.page.pageMapper";

    private final SqlSession sqlSession;
    
    @Inject
    public PageDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void savePageContent(PageVO page_vo) throws Exception {
		
		sqlSession.insert(NAMESPACE + ".savePageContent", page_vo);
	}

	@Override
	public PageVO loadPageContent(PageVO page_vo) throws Exception {
		
		return sqlSession.selectOne(NAMESPACE + ".loadPageContent", page_vo);
	}

	@Override
	public void connectUserWithPage(Page_CreateVO page_create_vo) throws Exception {
		
		sqlSession.insert(NAMESPACE + ".connectUserWithPage", page_create_vo); 
	}

	@Override
	public int checkOverlabPageName(PageWithUserVO page_with_user_vo) throws Exception {
		
		return sqlSession.selectOne(NAMESPACE + ".checkOverlabPageName", page_with_user_vo);
	}

	@Override
	public List<PageVO> searchUserPages(UserVO user_vo) throws Exception {
		
		return sqlSession.selectList(NAMESPACE + ".searchUserPages", user_vo);
	}

}
