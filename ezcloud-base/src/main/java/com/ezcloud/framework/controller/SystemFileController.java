package com.ezcloud.framework.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ezcloud.framework.common.FileInfo;
import com.ezcloud.framework.common.FileInfo.FileType;
import com.ezcloud.framework.common.FileInfo.OrderType;
import com.ezcloud.framework.service.system.SystemFileService;
import com.ezcloud.framework.util.FileUtil;
import com.ezcloud.framework.util.JsonUtils;
import com.ezcloud.framework.util.Message;
import com.ezcloud.framework.util.ResponseVO;
import com.ezcloud.framework.util.StringUtil;
import com.ezcloud.framework.vo.DataSet;

/**
 * Controller - 文件处理
 */
@Controller("frameworkSystemFileController")
public class SystemFileController extends BaseController {

	@Resource(name = "frameworkSystemFileService")
	private SystemFileService fileService;

	/**
	 * 上传
	 */
	@RequestMapping(value = "/upload/file/upload", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void upload(FileType fileType, MultipartFile file, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (!fileService.isValid(fileType, file)) {
			data.put("message", Message.warn("admin.upload.invalid"));
		} else {
			String url = fileService.upload(fileType, file, false);
			if (url == null) {
				data.put("message", Message.warn("admin.upload.error"));
			} else {
				data.put("message", SUCCESS_MESSAGE);
				data.put("url", url);
			}
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			JsonUtils.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 浏览
	 */
	@RequestMapping( value = "/upload/file/browser" )
	public @ResponseBody
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		return fileService.browser(path, fileType, orderType);
	}
	
	/**
	 * 浏览文件夹
	 */
	@RequestMapping( value = "/system/file/select-folder" )
	public @ResponseBody
	ResponseVO selectFolder( String path ) {
		System.out.println("path111------------------->>>"+path);
		ResponseVO ovo =new ResponseVO(0);
		if ( StringUtil.isEmptyOrNull(path) ) {
			path ="";
		}
		DataSet folders =FileUtil.listFolder(path);
		ovo.oForm.put("folder_data", folders);
		System.out.println("path22------------------->>>"+path);
		System.out.println("folders------------------->>>"+folders);
		return ovo;
	}
	
	/**
	 * 预览文件夹，根据指定路径，展示此路径自身以及它的上级路径的全部文件夹
	 */
	@RequestMapping( value = "/system/file/preview-folder" )
	public @ResponseBody
	ResponseVO previewFolder( String path ) {
		ResponseVO ovo =new ResponseVO(0);
		if ( StringUtil.isEmptyOrNull(path) ) {
			path ="";
		}
		HashMap<String ,Object> map =FileUtil.queryAllPreFolders(path);
		ovo.oForm.put("folder_data", map);
		System.out.println("------------------->>>");
		return ovo;
	}

}