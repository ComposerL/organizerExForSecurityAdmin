package com.office.myorganizeradmin.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.office.myorganizeradmin.member.mapper.IMemberMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberService {
	
	final private IMemberMapper iMemberMapper;
	
	public MemberService(IMemberMapper iMemberMapper) {
		this.iMemberMapper = iMemberMapper;
	}
	
	
	//회원 리스트 조회
	public ArrayList<MemberDto> getMemberList() {
		log.info("getMemberList()");
		
		return iMemberMapper.getMemberList();
	}

	//회원 롤 변경
	public Object memberModifyForRole(Map<String, String> map) {
		
		Map<String, Object> responseMap = new HashMap<>();
		
		int resultModifyForRole = iMemberMapper.memberModifyForRole(map);
		
		responseMap.put("resultModifyForRole", resultModifyForRole);
		
		return responseMap;
	}
	
}
