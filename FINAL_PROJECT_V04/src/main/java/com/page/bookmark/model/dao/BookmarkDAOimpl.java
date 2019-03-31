package com.page.bookmark.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.page.bookmark.model.dto.BookmarkVO;
import com.page.bookmark.model.dto.BookmarkWithPageVO;
import com.page.user.dto.UserVO;

@Repository
public class BookmarkDAOimpl implements BookmarkDAO {

    private static final String NAMESPACE = "com.page.mappers.bookmark.bookmarkMapper";

    private final SqlSession sqlSession;
    
    @Inject
	public BookmarkDAOimpl(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void saveBookmark(BookmarkVO bookmark_vo) throws Exception {
		
		sqlSession.insert(NAMESPACE + ".savePageContent", bookmark_vo);
	}

	@Override
	public List<BookmarkVO> searchPageBookmarks(UserVO user_vo) throws Exception {
		
		return sqlSession.selectList(NAMESPACE + ".searchPageBookmarks", user_vo);
	}

	@Override
	public BookmarkWithPageVO loadPageBookmark(BookmarkWithPageVO bookmark_with_page_vo) throws Exception {
		
		System.out.println((BookmarkWithPageVO)sqlSession.selectOne(NAMESPACE + ".loadPageBookmark", bookmark_with_page_vo));
		return sqlSession.selectOne(NAMESPACE + ".loadPageBookmark", bookmark_with_page_vo);
	}

}
