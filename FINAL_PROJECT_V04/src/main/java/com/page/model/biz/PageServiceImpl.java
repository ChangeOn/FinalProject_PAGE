package com.page.model.biz;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.page.model.dao.PageDAO;
import com.page.model.dto.PageVO;
import com.page.model.dto.PageWithUserVO;
import com.page.model.dto.Page_CreateVO;

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
	public PageVO loadPageContent(PageVO page_vo) throws Exception {
		
		return pageDAO.loadPageContent(page_vo);
	}

	@Override
	public void connectUserWithPage(Page_CreateVO page_create_vo) throws Exception {
		
		pageDAO.connectUserWithPage(page_create_vo);
	}

	@Override
	public int checkOverlabPageName(PageWithUserVO page_with_user_vo) throws Exception {
		
		return pageDAO.checkOverlabPageName(page_with_user_vo);
	}

}
