package com.page.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.page.model.dto.PageVO;

@Repository
public class PageDAOImpl implements PageDAO {

    private static final String NAMESPACE = "com.page.mappers.page.pageMapper";

    private final SqlSession sqlSession;
    
    @Inject
    public PageDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
	
	@Override
	public void savePageContent(PageVO page_vo) {
		
		sqlSession.insert(NAMESPACE + ".savePageContent", page_vo);
	}

}
