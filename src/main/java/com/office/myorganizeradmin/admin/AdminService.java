package com.office.myorganizeradmin.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.myorganizeradmin.admin.mapper.IAdminMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {
	
	final static public int ADMIN_ALREADY = -1;
	final static public int ADMIN_SIGN_UP_FAIL = 0;
	final static public int ADMIN_SIGN_UP_SUCCESS = 1;
	
	final private IAdminMapper iAdminMapper;
	final private PasswordEncoder passwordEncoder;
	
	public AdminService(IAdminMapper iAdminMapper, PasswordEncoder passwordEncoder) {
		
		this.iAdminMapper = iAdminMapper;
		this.passwordEncoder = passwordEncoder;
		
	}
	
	//관리자 회원가입 확인
	public int adminSignUpConfirm(AdminDto adminDto) {
		log.info("adminSignUpConfirm()");
		
		//관리자 ID존재여부 확인
		boolean isAdmin = iAdminMapper.isAdmin(adminDto.getA_id());
		
		if(!isAdmin) {
			log.info("THIS ID ADMIN DOES NOT EXIST");
			
			//관리자 회원가입
			String encodedPW = passwordEncoder.encode(adminDto.getA_pw());
			adminDto.setA_pw(encodedPW);
			
			int result = iAdminMapper.adminSignUpConfirm(adminDto);
			
			if(result <= 0) {
				log.info("ADMIN SIGNUP FAIL");
				return ADMIN_SIGN_UP_FAIL;
			}else {
				log.info("ADMIN SIGNUP SUCCESS");
				return ADMIN_SIGN_UP_SUCCESS;
			}
			
		}else {
			log.info("THIS ID ADMIN EXIST");
			return ADMIN_ALREADY;
		}
		
	}
	
	//관리자 정보 조회
	public AdminDto getAdminById(String a_id) {
		log.info("getAdminById()");
		
		AdminDto loginedAdminDto = iAdminMapper.getAdminById(a_id);
		
		return loginedAdminDto;
	}
	
	//관리자 정보 수정 확인
	public int adminModifyConfirm(AdminDto adminDto) {
		log.info("adminModifyConfirm()");
		
		String encodedPW = passwordEncoder.encode(adminDto.getA_pw());
		adminDto.setA_pw(encodedPW);
		
		int result = iAdminMapper.adminModifyConfirm(adminDto);
		
		return result;
	}
	
	//관리자 회원 탈퇴
	public int adminDeleteConfirm(String a_id) {
		log.info("adminDeleteConfirm()");
		
		int result = iAdminMapper.adminDeleteConfirm(a_id);
		
		return result;
	}
	
	//관리자 목록 조회
	public ArrayList<AdminDto> getAdminList() {
		log.info("getAdminList()");
		
		return iAdminMapper.getAdminList();
	}
	
	//관리자 롤 변경
	public Object adminModifyForRole(Map<String, String> map) {
		log.info("adminModifyForRole()");
		
		Map<String, Object> responseMap = new HashMap<>();
		
		int resultModifyForRole = iAdminMapper.adminModifyForRole(map);
		
		responseMap.put("resultModifyForRole", resultModifyForRole);
		
		return responseMap;
	}
	
}
