package com.websocket.chat;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.websocket.file.biz.FileBiz;
import com.websocket.file.dto.FileDto;
import com.websocket.handler.ChatWebSocketHandler;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes({"userid", "pageno", "username"})
public class ChatController {
	
	private Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	private FileBiz biz;	
	
	@RequestMapping("/test.do")
	public String viewPage() {
		return "test";
	}
	
	@RequestMapping("/web/chat.do")
	public String viewChatPage(Model model, HttpServletRequest request) {
		
		logger.info("chat.do RUN! / Run Time: " + new Date());
		String userid = "TESTUSER";
		int pageno = 3;
		//UserInfoDto login = (UserInfoDto)request.getSession().getAttribute("login");
		/*(login==null) {
			return "redirect:/loginNull.do";
		}*/
		model.addAttribute("userid", userid );
		model.addAttribute("username", "개나리" );
		model.addAttribute("pageno", pageno );
		
		return "chat";
	}
	
	@RequestMapping("/paint.do")
	public String viewPaintPage() {
		return "paint";
	}

	@RequestMapping(value = "/fileupload.do")
    public @ResponseBody Map<String , Object> fileUp(MultipartHttpServletRequest multi) {
	
        // 저장 경로 설정
        String root = multi.getSession().getServletContext().getRealPath("/");
        String path = root+"resources/upload/";
        String newFileName = ""; // 업로드 되는 파일명
         
        // 저장 경로에 폴더 없을 시 생성
        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }
         
        Iterator<String> files = multi.getFileNames();
        
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        
        while(files.hasNext()){
            String uploadFile = files.next();
                         
            MultipartFile mFile = multi.getFile(uploadFile);
            String fileName = mFile.getOriginalFilename();
            logger.info("실제 파일 이름 : " +fileName);
            newFileName = System.currentTimeMillis()+"."
                    +fileName.substring(fileName.lastIndexOf(".")+1);
             
            try {
            	// 실제 경로로 파일저장
                mFile.transferTo(new File(path+newFileName));
                
                // DB 에 file 값 저장
                FileDto dto = new FileDto(0,fileName,newFileName);
                biz.FileInsert(dto);
                
                int fileno = biz.FileSelectFileno(newFileName);
                
                // websocket에 json값 전달
                jsonObject.put("type","filedata");
                jsonObject.put("fileName", fileName);
                jsonObject.put("newFileName", newFileName);
                jsonObject.put("fileno", fileno);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		        
        return jsonObject;
    }
	
	@RequestMapping(value="/download.do")
	@ResponseBody
	public byte[] fileDown(HttpServletRequest request, HttpServletResponse response, String filename, String name) throws IOException {
		String root = request.getSession().getServletContext().getRealPath("/");
        String path = root+"resources/upload/";        
		
		File file = new File(path+"/"+filename);
		
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		String fn = new String(file.getName().getBytes(), "8859_1");
				
		response.setHeader("Content-Disposition", "attachment;filename=\""+name+"\"");
		response.setContentLength(bytes.length);
		response.setContentType("image/jpeg");
		
		return bytes;		
	}
	
	@RequestMapping("/saveimage.do")
	public @ResponseBody Map<String , Object> ajax_canvasUpload(HttpServletRequest request, String dataurl) throws Throwable{
	  logger.info("page_canvasUpload > "+dataurl);
	  
	  // 저장 경로 설정
      String root = request.getSession().getServletContext().getRealPath("/");
      String path = root+"resources/imgupload/";
      
      logger.info("path > "+path);
      
	  String fullpath="";
      
	  String[] strParts=dataurl.split(",");
	  String rstStrImg=strParts[1];  //,로 구분하여 뒷 부분 이미지 데이터를 임시저장
	  
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_hhmmss"); 
	  String filenm = sdf.format(new Date()).toString()+"_img.png";// 업로드 되는 파일명

	  BufferedImage image=null;
	  byte[] byteImg;
	  byteImg = Base64.decodeBase64(rstStrImg);  //base64 디코더를 이용하여 byte 코드로 변환
	  ByteArrayInputStream bis= new ByteArrayInputStream(byteImg);
	  image= ImageIO.read(bis);   //BufferedImage형식으로 변환후 저장
	  bis.close();

	  fullpath=path+filenm;
	  File folderObj= new File(path);
	  if( !folderObj.isDirectory() ) folderObj.mkdir();
	  File outputFile= new File(fullpath);  //파일객체 생성	  
	  if( outputFile.exists() ) outputFile.delete();
	  ImageIO.write(image, "png", outputFile); //서버에 파일로 저장
	  
	  Map<String, Object> jsonObject = new HashMap<String, Object>(); // JSON 객체생성	  
	  jsonObject.put("type","copycanvas");
      jsonObject.put("data", fullpath);
	  
	  return jsonObject;
	 }
	
	@RequestMapping("/loadimage.do")
	@ResponseBody
	public String ajax_canvasLoad(HttpServletRequest request, String dataurl) throws Throwable{
	  //logger.info("page_canvasLoad > "+dataurl);	        
	  
	  // 이미지를 바이트형태로 변환 후 Base64형태로 변환
	  byte[] fileContent = FileUtils.readFileToByteArray(new File(dataurl));
	  String encodedString = "data:image/png;base64, ";
	  encodedString += Base64.encodeBase64String(fileContent);
	  
	  // 이미지 불러온 후 파일삭제
	  File deleteFile= new File(dataurl);
	  if( deleteFile.exists() ) deleteFile.delete();
	  
	  return encodedString;
	}


}