package com.page.bookmark.model.biz;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.page.bookmark.model.dao.BookmarkDAO;
import com.page.bookmark.model.dto.BookmarkVO;
import com.page.bookmark.model.dto.BookmarkWithPageVO;
import com.page.user.dto.UserVO;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	private final BookmarkDAO bookmarkDAO;
	
	@Inject
	public BookmarkServiceImpl(BookmarkDAO bookmarkDAO) {
		super();
		this.bookmarkDAO = bookmarkDAO;
	}
	
	@Override
	public void saveBookmark(BookmarkVO bookmark_vo) throws Exception {
		
		bookmarkDAO.saveBookmark(bookmark_vo);
	}

	@Override
	public List<BookmarkVO> searchPageBookmarks(UserVO user_vo) throws Exception {
		
		return bookmarkDAO.searchPageBookmarks(user_vo);
	}

	@Override
	public BookmarkWithPageVO loadPageBookmark(BookmarkWithPageVO bookmark_with_page_vo) throws Exception {
		
		return bookmarkDAO.loadPageBookmark(bookmark_with_page_vo);
	}

}
