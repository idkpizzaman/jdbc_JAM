package com.koreaIT.JAM.dto;

import java.util.Map;

public class Member {
	String loginId;
	String loginPw;
	String name;
	String loginPwChk;
	
	public Member(Map<String, Object> memberMap) {
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("`name`");
		this.loginPwChk = (String) memberMap.get("loginPwChk");
	}
}
