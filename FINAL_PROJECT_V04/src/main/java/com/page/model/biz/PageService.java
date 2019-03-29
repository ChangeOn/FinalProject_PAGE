package com.page.model.biz;

import com.page.model.dto.PageVO;
import com.page.model.dto.PageWithUserVO;
import com.page.model.dto.Page_CreateVO;

public interface PageService {
	
	void savePageContent(PageVO page_vo) throws Exception;
	PageVO loadPageContent(PageVO page_vo) throws Exception;
	void connectUserWithPage(Page_CreateVO page_create_vo) throws Exception;
	//유저별 페이지 이름 중복 확인
	int checkOverlabPageName(PageWithUserVO page_with_user_vo) throws Exception;
}
