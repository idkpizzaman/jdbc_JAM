package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.koreaIT.JAM.controller.ArticleController;
import com.koreaIT.JAM.controller.Controller;
import com.koreaIT.JAM.controller.MemberController;
import com.koreaIT.JAM.session.Session;

public class App {
    
	final String URL = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    final String USER = "root";
    final String PASSWORD = "";
	
	public void run() {
	    
		Scanner sc = new Scanner(System.in);
		
		Connection connection = null;
		
		System.out.println("== 프로그램 시작 ==");
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
			ArticleController articleController = new ArticleController(connection, sc);
			MemberController memberController = new MemberController(connection, sc);
			
			while (true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					System.out.println("프로그램을 종료합니다.");
					break;
				}
				
				if (cmd.length() == 0) {
					System.out.println("명령어를 입력해주세요.");
					continue;
				}
				
				String[] cmdBits = cmd.split(" ");
				
				if (cmdBits.length < 2) {
					System.out.println("존재하지 않는 명령어 입니다.");
					continue;
				}
				
				String controllerName = cmdBits[0];
				String methodName = cmdBits[1];
				
				switch(controllerName + "/" +methodName) {
				case "article/write":
				case "article/modify":
				case "article/delete":
				case "member/logout":
					if (Session.isLogined() == false) {
						System.out.println("로그인을 실행해주세요.");
						continue;
					} else {
						break;
					}
				case "member/join":
				case "member/login":
					if (Session.isLogined() == true) {
						System.out.println("로그아웃을 실행해주세요.");
						continue;
					} else {
						break;
					}
				}
				
				Controller controller = null;
				
				if (controllerName.equals("article")) {
					controller = articleController;
				} else if(controllerName.equals("member")) {
					controller = memberController;
				} else {
					System.out.println("존재하지 않는 명령어 입니다");
					continue;
				}
				
				controller.doAction(cmd, methodName);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
		
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
	}
}