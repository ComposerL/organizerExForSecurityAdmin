package com.office.myorganizeradmin.admin;

import java.util.ArrayList;
import java.util.Map;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	final private AdminService adminService;
	
	public AdminController(AdminService adminService) {
		
		this.adminService = adminService;
		
	}
	
	//관리자 가입 양식
	@GetMapping("/adminSignUp")
	public String adminSignUp() {
		log.info("adminSignUp()");
		
		String nextPage = "admin/signup";
		
		return nextPage;
	}
	
	
	//관지자 가입 확인
	@PostMapping("/adminSignUpConfirm")
	public String adminSignUpConfirm(AdminDto adminDto) {
		log.info("adminSignUpConfirm()");
		
		String nextPage = "admin/signup_ok";
		
		int result = adminService.adminSignUpConfirm(adminDto);
				
		if(result == AdminService.ADMIN_ALREADY || result == AdminService.ADMIN_SIGN_UP_FAIL) {
			nextPage = "admin/signup_ng";
		}
		
		return nextPage;
	}
		
	
	//관리자 로그인 양식
	@GetMapping("/adminSignIn")
	public String adminSignIn() {
		log.info("adminSignIn()");
		
		String nextPage = "admin/signin";
		
		return nextPage;
	}
	
	
	//관리자 로그인 확인 by spring security
	@GetMapping("/adminSignInResult")
	public String adminSignInResult(@RequestParam("logined") Boolean logined,@RequestParam(value =  "errorMsg", required = false) String errorMsg,Model model) {
		log.info("adminSignInResult()");
		
		String nextPage = "admin/signin_ok";
		
		if(!logined) {
			nextPage = "admin/signin_ng";
			model.addAttribute("errorMsg",errorMsg);
		}
		
		return nextPage;
	}
	
	
	//관리자 정보 수정 양식
	@GetMapping("/adminModify")
	public String adminModify(Model model) {
		log.info("adminModify()");
		
		String nextPage = "admin/modify";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		AdminDto loginedAdminDto = adminService.getAdminById(authentication.getName());
		
		model.addAttribute("loginedAdminDto",loginedAdminDto);
		
		return nextPage;
	}
	
	
	//관리자 정보 수정 확인
	@PostMapping("/adminModifyConfirm")
	public String adminModifyConfirm(AdminDto adminDto) {
		log.info("adminModifyConfirm()");
		
		String nextPage = "admin/modify_ok";
		
		int result = adminService.adminModifyConfirm(adminDto);
		
		if(result <= 0) {
			nextPage = "admin/modify_ng";
		}
		
		return nextPage;
	}
	
	
	//관리자 삭제 확인
	@GetMapping("/adminDeleteConfirm")
	public String adminDeleteConfirm(HttpServletRequest request, HttpServletResponse response) {
		log.info("adminDeleteConfirm()");
		
		String nextPage = "admin/delete_ok";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		int result = adminService.adminDeleteConfirm(authentication.getName());
		
		if(result <= 0) {
			nextPage = "admin/delete_ng";
			
		}else {
			
			if(authentication != null) {
				new SecurityContextLogoutHandler().logout(request, response, authentication);
			}
			
		}
		
		return nextPage;
	}
	
	//관리자 목록
	@GetMapping("/adminList")
	public String adminList(Model model) {
		log.info("adminList()");
		
		String nextPage = "admin/admin_list";
		
		ArrayList<AdminDto> adminDtos = adminService.getAdminList();
		model.addAttribute("adminDtos",adminDtos);
		
		return nextPage;
	}
	
	
	// accessDeniedPage
	@GetMapping("/accessDeniedPage")
	public String accessDeniedPage() {
		log.info("accessDeniedPage()");
				
		return "admin/access_denied_page";
	}
	
	
	// 관리자 ROLL 변경
	@ResponseBody
	@PutMapping("/adminModifyForRole")
	public Object adminModifyForRole(@RequestBody Map<String, String> map) {
		log.info("adminModifyForRole()");
		
		return adminService.adminModifyForRole(map);
	}
	
}
