package com.office.myorganizeradmin.config;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.office.myorganizeradmin.admin.AdminAccessDeniedHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Bean PasswordEncoder passwordEncoder() {
		log.info("passwordEncoder()");
		return new BCryptPasswordEncoder();
	}
	
	@Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable());
		
		http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(
							"/css/**",
							"/img/**",
							"/js/**",
							"/error/**",
							"/",
							"/admin/adminSignUp",
							"/admin/adminSignUpConfirm",
							"/admin/adminSignIn",
							"/admin/adminSignInConfirm",
							"/admin/adminSignInResult"
							).permitAll()
					.requestMatchers("/admin/adminList").hasRole("ADMIN_SUPER")
					.requestMatchers("/member/memberList").hasAnyRole("ADMIN_SUPER","ADMIN_APPROVED")
					.anyRequest().authenticated());
		
		http
			.exceptionHandling(exceptionConfig -> exceptionConfig
					.accessDeniedHandler(new AdminAccessDeniedHandler())
					);
		
		http
			.formLogin(login -> login
					.loginPage("/admin/adminSignIn")
					.loginProcessingUrl("/admin/adminSignInConfirm")
					.usernameParameter("a_id")
					.passwordParameter("a_pw")
					.successHandler((request,response,authentication) -> {
						log.info("ADMIN SIGN IN SUCCESS HANDLER");
						
						String targetURI = "/admin/adminSignInResult?logined=true";
						
						RequestCache requestCache = new HttpSessionRequestCache();
						SavedRequest savedRequest = requestCache.getRequest(request, response);
						if(savedRequest != null) {
							targetURI = savedRequest.getRedirectUrl();
							requestCache.removeRequest(request, response);
						}
						
						response.sendRedirect(targetURI);
					})
					.failureHandler((request,response,exception) ->{
						log.info("ADMIN SIGN IN FAIL HANDLER");
						
						String encodedValue = URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8.toString()); //에러메시지
						response.sendRedirect("/admin/adminSignInResult?logined=false&errorMsg="+encodedValue);
						
					}));
		
		http
			.logout(logout -> logout
					.logoutUrl("/admin/adminSignOutConfirm")
					.logoutSuccessHandler((request,response,authentication) -> {
						log.info("ADMIN SIGN OUT SUCCESS HANDLER");
						response.sendRedirect("/");
					}));
			
		
		return http.build();
	}
	
}
