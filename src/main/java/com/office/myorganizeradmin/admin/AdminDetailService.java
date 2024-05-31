package com.office.myorganizeradmin.admin;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.office.myorganizeradmin.admin.mapper.IAdminMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminDetailService implements UserDetailsService{
	
	final private IAdminMapper iAdminMapper;
	
	public AdminDetailService(IAdminMapper iAdminMapper) {
		this.iAdminMapper = iAdminMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername()");
		
		AdminDto adminDto = iAdminMapper.getAdminById(username);
		
		if(adminDto.getA_role().equals("ADMIN_PENDING")) {
			throw new RuntimeException(String.format("%s은(는) 승인 대기중 입니다.", username));
		}
		
		return User
				.builder()
				.username(adminDto.getA_id())
				.password(adminDto.getA_pw())
				.roles(adminDto.getA_role())
				.build();
	}

}
