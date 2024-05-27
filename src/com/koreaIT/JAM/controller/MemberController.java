package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Member;
import com.koreaIT.JAM.service.MemberService;
import com.koreaIT.JAM.session.Session;

public class MemberController extends Controller{
	
	private MemberService memberService;
	
	public MemberController(Connection connection, Scanner sc) {
		this.memberService = new MemberService(connection);
		this.sc = sc;
	}

	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch(methodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
		}
	}

	public void doJoin() {
		String loginId = null;
		String loginPwChk = null;
		String name = null;
		String loginPw = null;										
		
		System.out.println("== 회원가입 ==\n");

		while (true) {
			
			System.out.println("아이디를 입력하세요: ");
			loginId = sc.nextLine();
			
			if (loginId.length() == 0) {
				System.out.println("아이디는 필수입력 정보입니다.");
				continue;
			}
			
			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
			
			if (isLoginIdDup) {
				System.out.printf("[ %s ]은(는) 이미 사용중인 아이디입니다.\n", loginId);
				continue;
			}
			
			System.out.printf("[ %s ]은(는) 사용가능한 아이디입니다.\n", loginId);
			break;
		}															

		while (true) {
			System.out.println("비밀번호를 입력하세요: ");
			loginPw = sc.nextLine();
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호는 필수 입력 정보입니다.");
				continue;				
			}
			
			System.out.println("비밀번호 확인: ");
			loginPwChk = sc.nextLine();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.println("이름을 입력하세요: ");
			name = sc.nextLine().trim();
			
			if (name.length() == 0) {
				System.out.println("이름은 필수 입력 정보입니다.");
				continue;
			}
			break;
		}
		
		memberService.doJoin(loginId, loginPw, name);

		System.out.println(loginId + "님! 가입을 환영합니다!");
		
	} 

	public void doLogin() {
		String loginId = null;
		String loginPw = null;
		Member member = null;
		
		System.out.println("== 로그인 ==");
		
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디는 필수 입력 정보입니다");
				continue;
			}
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호는 필수 입력 정보입니다");
				continue;
			}
			
			member = memberService.getMemberByLoginId(loginId);
			
			if (member == null) {
				System.out.printf("[ %s ] 은(는) 존재하지 않는 아이디입니다\n", loginId);
				continue;
			}
			
			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호가 일치하지 않습니다");
				continue;
			}
			break;
		}
		
		Session.login(member.id);
		System.out.printf("[ %s ] 회원님 환영합니다~\n", loginId);
	}

	public void doLogout() {
		
		if (Session.isLogined() == false) {
		}
		
		System.out.println("로그인 후 이용해주세요");
		return;
	}
}
