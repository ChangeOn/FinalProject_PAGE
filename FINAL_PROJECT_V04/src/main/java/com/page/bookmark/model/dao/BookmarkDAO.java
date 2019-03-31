package com.page.bookmark.model.dao;

import java.util.List;

import com.page.bookmark.model.dto.BookmarkVO;
import com.page.bookmark.model.dto.BookmarkWithPageVO;
import com.page.user.dto.UserVO;

public interface BookmarkDAO {
	
	//북마크 생성
	void saveBookmark(BookmarkVO bookmark_vo) throws Exception;
	//북마크 조회
	List<BookmarkVO> searchPageBookmarks(UserVO user_vo) throws Exception;
	//특정 북마크 조회
	BookmarkWithPageVO loadPageBookmark(BookmarkWithPageVO bookmark_with_page_vo) throws Exception;

}
