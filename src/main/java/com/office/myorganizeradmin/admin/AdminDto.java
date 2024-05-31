package com.office.myorganizeradmin.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

	private int a_no;
	private String a_id;
	private String a_pw;
	private String a_mail;
	private String a_phone;
	private String a_role;
	private String a_reg_date;
	private String a_mod_date;
	
}
