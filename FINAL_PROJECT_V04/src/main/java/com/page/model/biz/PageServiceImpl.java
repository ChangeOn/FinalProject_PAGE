package com.page.model.biz;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.page.model.dao.PageDAO;
import com.page.model.dto.PageVO;
import com.page.model.dto.PageWithUserVO;
import com.page.model.dto.Page_CreateVO;
import com.page.user.dto.UserVO;

@Service
public class PageServiceImpl implements PageService {

	private final PageDAO pageDAO;
    @Inject
    public PageServiceImpl(PageDAO pageDAO) {
    	
        this.pageDAO = pageDAO;
    }
	
	@Override
	public void savePageContent(PageVO page_vo) throws Exception {
		
		pageDAO.savePageContent(page_vo);
	}

	@Override
	public PageVO loadPageContent(PageWithUserVO page_with_user_vo) throws Exception {
		
		return pageDAO.loadPageContent(page_with_user_vo);
	}

	@Override
	public void connectUserWithPage(Page_CreateVO page_create_vo) throws Exception {
		
		pageDAO.connectUserWithPage(page_create_vo);
	}

	@Override
	public int checkOverlabPageName(PageWithUserVO page_with_user_vo) throws Exception {
		
		return pageDAO.checkOverlabPageName(page_with_user_vo);
	}

	@Override
	public List<PageVO> searchUserPages(UserVO user_vo) throws Exception {
			
		return pageDAO.searchUserPages(user_vo);
	}

	@Override
	public void updatePageContent(PageWithUserVO page_with_user_vo) throws Exception {
		
		pageDAO.updatePageContent(page_with_user_vo);
	}

}
