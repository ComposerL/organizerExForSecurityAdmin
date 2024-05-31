package com.office.myorganizeradmin.member;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

	final private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	// 회원 리스트 조회
	@GetMapping("/memberList")
	public String memberList(Model model) {
		log.info("memberList()");
		
		String nextPage = "member/member_list";
		
		ArrayList<MemberDto> memberDtos = memberService.getMemberList();
		
		model.addAttribute("memberDtos",memberDtos);
		
		return nextPage;
	}
	
	
	// 회원 롤(권한) 변경
	@PutMapping("/memberModifyForRole")
	@ResponseBody
	public Object memberModifyForRole(@RequestBody Map<String, String> map) {
		log.info("memberModifyForRole()");			
		
		return memberService.memberModifyForRole(map);
	}
	
}
