package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class App {
	
	private final String URL = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    private final String USER = "root";
    private final String PASSWORD = "";
    
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		System.out.println("== 프로그램 시작 ==");
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
			while (true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					break;
				}
				
				if(cmd.equals("member join")) {
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
						
						SecSql sql = new SecSql();
						sql.append("SELECT COUNT(id) < 0");
						sql.append("FROM `member`");
						sql.append("WHERE loginId = ?", loginId);
						
						boolean isLoginIdDup = DBUtil.selectRowBooleanValue(connection, sql);
						
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
					
					SecSql sql = new SecSql();
					sql.append("INSERT INTO `member` ");
					sql.append("SET regDate = NOW()");
					sql.append(", updateDate = NOW()");
					sql.append(", loginId = ?", loginId);
					sql.append(", loginPw = ?", loginPw);
					sql.append(", `name` = ?", name);										
					
					DBUtil.insert(connection, sql);

					System.out.println(name + "님! 가입을 환영합니다!");
					
				} else if (cmd.equals("article write")) {
					System.out.println("== 게시물 작성 ==");
					
					System.out.printf("제목 : ");
					String title = sc.nextLine();
					System.out.printf("내용 : ");
					String content = sc.nextLine();

			        SecSql sql = new SecSql();
			        sql.append("INSERT INTO article");
			        sql.append(" SET regDate = NOW()");
			        sql.append(", updateDate = NOW()");
			        sql.append(", title = ?", title);
		        	sql.append(", content = ?", content);;

			        int id = DBUtil.insert(connection, sql);
			        
					System.out.printf("%d번 게시물이 작성되었습니다\n", id);
					
				} else if (cmd.equals("article list")) {
			        
			    	List<Article> articles = new ArrayList<>();
			    	
			    	SecSql sql = new SecSql();
			    	sql.append("SELECT * FROM article");
			    	sql.append(" ORDER BY id DESC;");
			    	
			    	List<Map<String, Object>> articleListMap = DBUtil.selectRows(connection, sql);
			    	// Map 은 키-밸류로 이루어져 있음
			    	// 일단 받기 위해서 밸류값을 Object로 받고, 나중에 활용할 때 형변환을 할 수 있게끔 함
			    	// Key = 받아올 리스트의 컬럼명, Object = 안에 들어있는 값
			    	
			    	for(Map<String, Object> articleMap : articleListMap) {
			    		// 맵 형태의 리스트의 articleMap을 articleListMap으로 순환해서 반복문 실행
			    		articles.add(new Article(articleMap));
			    	}

					System.out.println("== 게시물 목록 ==");
					
					if (articles.size() == 0) {
						System.out.println("게시물이 존재하지 않습니다");
						continue;
					}
					
					System.out.println("	번호	|		제목			|	작성일	");
					for (Article article : articles) {
						System.out.printf("	%d	|		%s		|	%s\n", article.id, article.title, article.regDate);
					}
					
				} else if (cmd.startsWith("article modify ")) {
					int id = Integer.parseInt(cmd.split(" ")[2]);			        			       

					System.out.println("== 게시물 수정 ==");										
					System.out.printf("수정할 제목 : ");
					String title = sc.nextLine();
					System.out.printf("수정할 내용 : ");
					String content = sc.nextLine();
					
					SecSql sql = new SecSql();
			        sql.append("UPDATE article");
			        sql.append(" SET updateDate = NOW()");
			        sql.append(", title = ?" + title);
			        sql.append(", content = ?" + content);
			        sql.append(" WHERE id = ?" + id + ";");
			        
			        int articleCount = DBUtil.selectRowIntValue(connection, sql);
			        
			        if(articleCount == 0) {
			        	System.out.println("수정할 게시물이 없습니다.");
			        	continue;
			        }
			        
			        System.out.printf("%d번 게시물이 수정되었습니다\n", id);
			        
				} else if (cmd.startsWith("article detail ")) {
					System.out.println("== 게시물 자세히보기 ==");
					
					int id = Integer.parseInt(cmd.split(" ")[2]);
					
					SecSql sql = new SecSql();					
					sql.append("SELECT *");
					sql.append(" FROM article");
					sql.append("WHERE id = ?" + id);	
					
					Map<String, Object> articleMap = DBUtil.selectRow(connection, sql);
					
					if (articleMap.isEmpty()) {
						System.out.println(id + "번 게시물이 없습니다.");
						continue;
					}
					
					Article article = new Article(articleMap);
					
					System.out.println("번호: " + id);
					System.out.println("제목: " + article.title);
					System.out.println("내용: " + article.content);
					System.out.println("작성일: " + article.regDate);
					System.out.println("수정일: " + article.updateDate);
			        
					DBUtil.update(connection, sql);

				} else if (cmd.startsWith("article delete ")) {
					int id = Integer.parseInt(cmd.split(" ")[2]);
					
					System.out.println("== 게시물 삭제 ==");
					
					SecSql sql = new SecSql();

					if (id == 0) {
						System.out.println("삭제할 게시물이 없습니다.");
						continue;
					}
		
					sql.append("DELETE FROM article");
					sql.append(" WHERE id = ?" + id);
					
//					DBUtil.update(connection, sql);
//					위나 아래나 하는 일은 똑같지만 좀더 쉽게 이해할 수 있도록 메소드 이름을 맞게 변경
					DBUtil.delete(connection, sql);
			        
			        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
		}
	}
}