package com.page.board.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.page.board.model.biz.ansBiz;
import com.page.board.model.biz.boardBiz;
import com.page.board.model.dto.Criteria;
import com.page.board.model.dto.PageMaker;
import com.page.board.model.dto.SearchCriteria;
import com.page.board.model.dto.ansDto;
import com.page.board.model.dto.boardDto;
import com.page.board.model.dto.fileDto;


@Controller
public class BoardController {
	
	@Resource(name="boardBizImpl")
	private boardBiz bbiz;
	@Resource(name="ansBizImpl")
	private ansBiz abiz;
	
	@RequestMapping(value = "/boardlist.do", method= { RequestMethod.GET, RequestMethod.POST})
	public String listBoard(Model model,Criteria cri) {
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(bbiz.countBoardListTotal());
		
		List<boardDto> list = bbiz.listPaging(cri); 
		
		System.out.println(list.get(0));
		System.out.println("���������� ������ �Խñ��� ���� : "+cri.getPerPageNum());
		System.out.println("Ư�� �������� �Խñ� ���� �� ��ȣ : "+cri.getPageStart());
		System.out.println("���� ������ ��ȣ : "+cri.getPage());

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		
		return "boardlist";
	}
	
	@RequestMapping(value="/searchTitle.do")
	public String searchTitle(Model model,SearchCriteria scri, String keyword) {
		
		scri.setSearchType("title");
		scri.setKeyword(keyword);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(bbiz.countTitleList(keyword));
		System.out.println("searchTitle �� ���� : "+pageMaker.getTotalCount());
		
		List<boardDto> list = bbiz.searchTitle(scri);
		
		System.out.println(list.get(0).getTitle());
		System.out.println(list.get(0).getBoardseq());
		System.out.println(list.get(0).getContent());
		System.out.println(list.get(0).getId());
		System.out.println("keyword"+scri.getKeyword());
		System.out.println("���� ������ ��ȣ"+scri.getPage());
		System.out.println("�Խñ� ���� �� ��ȣ"+scri.getPageStart());
		System.out.println("�� �������� ������ �Խñ��� ����"+scri.getPerPageNum());
		System.out.println("�˻���� ���� :"+ pageMaker.getTotalCount());
		
		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		
		return "boardlist";
	}
	
	@RequestMapping(value="/searchContent.do")
	public String searchContent(Model model,SearchCriteria scri,String keyword) {
		
		scri.setKeyword("content");
		scri.setKeyword(keyword);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(bbiz.countContentList(keyword));
		System.out.println("searchContent �� ���� : "+pageMaker.getTotalCount());
		
		List<boardDto> list = bbiz.searchContent(scri);
		
		System.out.println("keyword"+scri.getKeyword());
		System.out.println("���� ������ ��ȣ"+scri.getPage());
		System.out.println("�Խñ� ���� �� ��ȣ"+scri.getPageStart());
		System.out.println("�� �������� ������ �Խñ��� ����"+scri.getPerPageNum());
		System.out.println("�˻���� ���� :"+ pageMaker.getTotalCount());
		
		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		
		
		return "boardlist";
	}
	@RequestMapping(value="/searchId.do")
	public String searchId(Model model,SearchCriteria scri,String keyword) {
		
		scri.setSearchType("id");
		scri.setKeyword(keyword);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(bbiz.countIdList(keyword));
		System.out.println("searchID �� ���� : "+pageMaker.getTotalCount());
		
		List<boardDto> list = bbiz.searchId(scri);
		
		System.out.println("keyword"+scri.getKeyword());
		System.out.println("���� ������ ��ȣ"+scri.getPage());
		System.out.println("�Խñ� ���� �� ��ȣ"+scri.getPageStart());
		System.out.println("�� �������� ������ �Խñ��� ����"+scri.getPerPageNum());
		System.out.println("�˻���� ���� :"+ pageMaker.getTotalCount());

		
		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		return "boardlist";
	}
	
	
	
	@RequestMapping("/insertform.do")
	public String insertForm() {
		return "insertBoard";
	}
	@ResponseBody
	@RequestMapping(value="/insertBoard.do", method=RequestMethod.GET)
	public String insertBoard(String title, String editor) {
		
		System.out.println("����"+title);
		System.out.println("������ ����"+editor);
		
		boardDto dto = new boardDto();
		dto.setTitle(title);
		dto.setContent(editor);
		int res = bbiz.insertBoard(dto);
		
	
    	if(res>0) {
			return "success";			
		}else {
			return "error";
		}
		
	}
	
	
	@RequestMapping("/selectOne.do")
	public String detailBoard(Model model,int boardseq) {
		
		boardDto dto = bbiz.selectOne(boardseq);
		
		dto.setBoardseq(boardseq);
		
		
		System.out.println("***************��� ����¡***************");
		Criteria cri = new Criteria(1,5,boardseq);

		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(abiz.countAnsListTotal(boardseq));
		
		System.out.println("���� �Խù��� ��� ���� : "+pageMaker.getTotalCount());
		System.out.println("���� �Խù� ��ȣ:"+cri.getBoardseq());
		System.out.println("���� �Խù� �������� ���̴� ��� �� :"+cri.getPerPageNum());
		System.out.println("���� ������"+pageMaker.getStartPage());
		System.out.println(cri.getPageStart());
		System.out.println("�� ������"+pageMaker.getEndPage());
		
		List<ansDto> listAnswer = abiz.ansPaging(cri);
		
		model.addAttribute("bdto",dto);
		model.addAttribute("listAnswer",listAnswer);
		model.addAttribute("pageMaker",pageMaker);
		
		System.out.println("�����Ϸ� ������!!!");
		return "detailBoard";
	}
	
	@RequestMapping("/ansPaging.do")
	public String detailAnsPaging(Model model,Criteria cri, int boardseq) {
		cri = new Criteria(cri.getPage(),5,boardseq);
		boardDto dto = bbiz.selectOne(boardseq);
		dto.setBoardseq(boardseq);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(abiz.countAnsListTotal(boardseq));
		
		List<ansDto> listAnswer = abiz.ansPaging(cri);
		
		model.addAttribute("bdto",dto);
		model.addAttribute("listAnswer",listAnswer);
		model.addAttribute("pageMaker",pageMaker);
		
		
		
		return "detailBoard";
	}
	
	
	@RequestMapping(value="/updateform.do", method=RequestMethod.GET)
	public String updateForm(Model model,int boardseq) {
		System.out.println("update form === boardseq"+boardseq);
		boardDto dto = bbiz.selectOne(boardseq);
		dto.setBoardseq(boardseq);
		model.addAttribute("bdto",dto);
	
		return "updateBoard";
	}
	@ResponseBody
	@RequestMapping(value="/updateBoard.do", method=RequestMethod.GET)
	public String updateBoard(@ModelAttribute boardDto bdto, int boardseq,String title,String content) {
		System.out.println("updateBoard!!!!!!!!!!");
		System.out.println(boardseq);
		System.out.println(title);
		System.out.println(content);
		bdto.setBoardseq(boardseq);
		bdto.setTitle(title);
		bdto.setContent(content);
		int res = bbiz.updateBoard(bdto);

    	if(res>0) {
			return "success";			
		}else {
			return "error";
		}
	}
	
	@RequestMapping("/deleteform.do")
	public String deleteBoard(int boardseq) {
		System.out.println("delete"+boardseq);
		int res = bbiz.deleteBoard(boardseq);
		
		if(res>0) {
			return "redirect:boardlist.do"; 
		}else {
			return "redirect:deleteboard.do";
		}
		
	}
	@ResponseBody
	@RequestMapping(value="/insertAnswer.do", method=RequestMethod.GET)
	public String insertAnswer(@ModelAttribute ansDto adto,int boardseq) {
		System.out.println("��� ���߰��� �Ѿ�ͽ� ����");
		
		abiz.insertAns(adto);
		 
		return "redirect:selectOne.do?boardseq="+boardseq;

	}
	
	@RequestMapping(value="/deleteAnswer.do")
	public String deleteAnswer(int ansno,int boardseq) {
		System.out.println("������ �Ѿ�ͽ�");
		
		abiz.deleteAns(ansno);
		
		return "redirect:selectOne.do?boardseq="+boardseq;
	}

	@RequestMapping("/file_uploader_html5.do")
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response){
	    try {
	         //��������
	         String sFileInfo = "";
	         //���ϸ��� �޴´� - �Ϲ� �������ϸ�
	         String filename = request.getHeader("file-name");
	         //���� Ȯ����
	         String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
	         //Ȯ���ڸ��ҹ��ڷ� ����
	         filename_ext = filename_ext.toLowerCase();
	         //���� �⺻���
	         String dftFilePath = request.getSession().getServletContext().getRealPath("/");
	         //���� �⺻��� _ �󼼰��
	         String filePath = dftFilePath + "resources/" + File.separator + "upload" + File.separator;
	         File file = new File(filePath);
	         if(!file.exists()) {
	            file.mkdirs();
	         }
	         String realFileNm = "";
	         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	         String today= formatter.format(new java.util.Date());
	         realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
	         
	         String rlFileNm = filePath + realFileNm;
	         ///////////////// ������ ���Ͼ��� /////////////////
	         InputStream is = request.getInputStream();
	         OutputStream os=new FileOutputStream(rlFileNm);
	         
	         fileDto fdto = new fileDto();
	         fdto.setFilestream(realFileNm);
	         fdto.setFiletitle(filename);
	         
	         int res = bbiz.insertFile(fdto);
	         if(res == 0) {
	        	 System.out.println("���� ���� �ȵ�");
	         }
	         
	         int numRead;
	         byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
	         while((numRead = is.read(b,0,b.length)) != -1){
	            os.write(b,0,numRead);
	         }
	         if(is != null) {
	            is.close();
	         }
	         os.flush();
	         os.close();
	         ///////////////// ������ ���Ͼ��� /////////////////
	         // ���� ���
	         sFileInfo += "&bNewLine=true";
	         // img �±��� title �Ӽ��� �������ϸ����� ��������ֱ� ����
	         sFileInfo += "&sFileName="+ filename;
	         sFileInfo += "&sFileURL="+"resources/upload/"+realFileNm;
	         PrintWriter print = response.getWriter();
	         print.print(sFileInfo);
	         print.flush();
	         print.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	
	
	

	
}
