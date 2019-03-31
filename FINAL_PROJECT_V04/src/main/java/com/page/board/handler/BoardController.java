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
import com.page.user.dto.UserVO;


@Controller
public class BoardController {
	
	@Resource(name="boardBizImpl")
	private boardBiz bbiz;
	@Resource(name="ansBizImpl")
	private ansBiz abiz;
	
	@RequestMapping(value = "/boardlist", method= { RequestMethod.GET, RequestMethod.POST})
	public String listBoard(Model model,Criteria cri) {
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(bbiz.countBoardListTotal());
		
		List<boardDto> list = bbiz.listPaging(cri); 

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		
		return "/page/board/boardlist";
	}
	
	@RequestMapping(value="/searchTitle")
	public String searchTitle(Model model,SearchCriteria scri, String keyword) {
		
		scri.setSearchType("title");
		scri.setKeyword(keyword);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(bbiz.countTitleList(keyword));
		System.out.println("searchTitle 총 갯수 : "+pageMaker.getTotalCount());
		
		List<boardDto> list = bbiz.searchTitle(scri);
		
		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		
		return "/page/board/boardlist";
	}
	
	@RequestMapping(value="/searchContent")
	public String searchContent(Model model,SearchCriteria scri,String keyword) {
		
		scri.setKeyword("content");
		scri.setKeyword(keyword);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(bbiz.countContentList(keyword));
		System.out.println("searchContent 총 갯수 : "+pageMaker.getTotalCount());
		
		List<boardDto> list = bbiz.searchContent(scri);

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		
		
		return "/page/board/boardlist";
	}
	@RequestMapping(value="/searchId")
	public String searchId(Model model,SearchCriteria scri,String keyword) {
		
		scri.setSearchType("id");
		scri.setKeyword(keyword);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(bbiz.countIdList(keyword));
		System.out.println("searchID 총 갯수 : "+pageMaker.getTotalCount());
		
		List<boardDto> list = bbiz.searchId(scri);

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("boardlist",list);
		return "/page/board/boardlist";
	}
	
	
	
	@RequestMapping("/insertform")
	public String insertForm() {
		return "/page/board/insertBoard";
	}
	@ResponseBody
	@RequestMapping(value="/insertBoard", method=RequestMethod.GET)
	public String insertBoard(HttpServletRequest request,String title, String editor) {
		
		UserVO userVO = (UserVO)request.getSession().getAttribute("login");
		boardDto dto = new boardDto();
		dto.setId(userVO.getUser_id());
		dto.setTitle(title);
		dto.setContent(editor);
		int res = bbiz.insertBoard(dto);
		System.out.println("editor"+editor);
		System.out.println("title"+title);
	
    	if(res>0) {
			return "success";			
		}else {
			return "error";
		}
		
	}
	
	
	@RequestMapping("/selectOne")
	public String detailBoard(HttpServletRequest request,Model model,int boardseq) {
		
		UserVO userVO = (UserVO)request.getSession().getAttribute("login");
		boardDto dto = bbiz.selectOne(boardseq);
		
		dto.setBoardseq(boardseq);

		Criteria cri = new Criteria(1,5,boardseq);

		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(abiz.countAnsListTotal(boardseq));

		
		List<ansDto> listAnswer = abiz.ansPaging(cri);
		
		model.addAttribute("userVO",userVO);
		model.addAttribute("bdto",dto);
		model.addAttribute("listAnswer",listAnswer);
		model.addAttribute("pageMaker",pageMaker);

		return "/page/board/detailBoard";
	}
	
	@RequestMapping("/ansPaging")
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
		
		
		
		return "/page/board/detailBoard";
	}
	
	
	@RequestMapping(value="/updateform", method=RequestMethod.GET)
	public String updateForm(Model model,int boardseq) {

		boardDto dto = bbiz.selectOne(boardseq);
		dto.setBoardseq(boardseq);
		model.addAttribute("bdto",dto);
	
		return "/page/board/updateBoard";
	}
	@ResponseBody
	@RequestMapping(value="/updateBoard", method=RequestMethod.GET)
	public String updateBoard(@ModelAttribute boardDto bdto, int boardseq,String title,String content) {

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
	
	@RequestMapping("/deleteform")
	public String deleteBoard(int boardseq) {

		int res = bbiz.deleteBoard(boardseq);
		
		if(res>0) {
			return "redirect:boardlist"; 
		}else {
			return "redirect:deleteboard";
		}
		
	}
	@ResponseBody
	@RequestMapping(value="/insertAnswer", method=RequestMethod.GET)
	public String insertAnswer(@ModelAttribute ansDto adto,int boardseq,HttpServletRequest request) {
		UserVO userVO = (UserVO)request.getSession().getAttribute("login");
		
		adto.setId(userVO.getUser_id());
		abiz.insertAns(adto);
		 
		return "redirect:selectOne?boardseq="+boardseq;

	}
	
	@RequestMapping(value="/deleteAnswer")
	public String deleteAnswer(int ansno,int boardseq) {
	
		abiz.deleteAns(ansno);
		
		return "redirect:selectOne?boardseq="+boardseq;
	}

	@RequestMapping("/file_uploader_html5")
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response){
	    try {
	         //파일정보
	         String sFileInfo = "";
	         //파일명을 받는다 - 일반 원본파일명
	         String filename = request.getHeader("file-name");
	         System.out.println("filename : "+filename);
	         //파일 확장자
	         String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
	         //확장자를소문자로 변경
	         filename_ext = filename_ext.toLowerCase();
	         //파일 기본경로
	         String dftFilePath = request.getSession().getServletContext().getRealPath("/");
	         //파일 기본경로 _ 상세경로
	         String filePath = dftFilePath +"resources/" + File.separator+"upload" + File.separator;
	         File file = new File(filePath);
	         if(!file.exists()) {
	            file.mkdirs();
	         }
	         String realFileNm = "";
	         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	         String today= formatter.format(new java.util.Date());
	         realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
	         
	         String rlFileNm = filePath + realFileNm;
	         ///////////////// 서버에 파일쓰기 /////////////////
	         InputStream is = request.getInputStream();
	         OutputStream os=new FileOutputStream(rlFileNm);
	         
	         fileDto fdto = new fileDto();
	         fdto.setFilestream(realFileNm);
	         fdto.setFiletitle(filename);
	         
	         int res = bbiz.insertFile(fdto);
	         if(res == 0) {
	        	 System.out.println("파일 저장 안됨");
	         }
	         System.out.println("파일 저장 성공!!");
	         
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
	         ///////////////// 서버에 파일쓰기 /////////////////
	         // 정보 출력
	         sFileInfo += "&bNewLine=true";
	         // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
	         sFileInfo += "&sFileName="+ filename;
	         sFileInfo += "&sFileURL="+"/resources/upload/"+realFileNm;
	         PrintWriter print = response.getWriter();
	         print.print(sFileInfo);
	         print.flush();
	         print.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
}
