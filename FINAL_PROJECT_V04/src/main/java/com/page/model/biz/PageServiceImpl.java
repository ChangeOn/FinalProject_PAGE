package com.page.model.biz;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.page.model.dao.PageDAO;
import com.page.model.dto.PageVO;

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

}
