package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;

public class Main {
	
	private static final String URL = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
	private static final String USER = "root";
	private static final String PASSWORD = "";
    
	public static void main(String[] args) {
		
		System.out.println("== 프로그램 시작 ==");
		
		int number = 0;
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("명령어: ");
			String cmd = sc.nextLine().trim();
			
			if(cmd.equals("exit")) {
				break;
			}
		
			if(cmd.equals("article write")) {
				System.out.println("제목: ");
				String title = sc.nextLine().trim();
				System.out.println("내용: ");
				String content = sc.nextLine().trim();
				
				Connection connection = null;
				PreparedStatement pstmt = null;
				
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
					
				 String sql = "INSERT INTO article";
		         sql += " SET regDate = NOW()";
		         sql += ", updateDate = NOW()";
		         sql += ", title = '" + title + "'";
		         sql += ", content = '" + content + "';";
		            
		        pstmt = connection.prepareCall(sql);
		        pstmt.executeUpdate();
		            
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {
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
			}
			
				number++;
				System.out.println(number + "번 게시물이 작성되었습니다.");
			
			} else if(cmd.equals("article list")){
				
				Connection connection = null;
		    	PreparedStatement pstmt = null;
		    	ResultSet rs = null;
		        
		    	List<Article> articles = new ArrayList<>();
		    	
		        try {
		            connection = DriverManager.getConnection(URL, USER, PASSWORD);

		            String sql = "SELECT * FROM article";
		            sql += " ORDER BY id DESC;";
		            // sql 에 저장할 때 내림차순으로 저장하도록 설정
		            
		            pstmt = connection.prepareStatement(sql);
		            rs = pstmt.executeQuery();
		            // rs 에 저장될 때에는 압축본으로 저장되며 아래 while 문에서 하나하나 압출을 풀어서 출력해줘야함
		            
		            while (rs.next()) {
		            	// rs.next()의 역할: 책장을 넘기는 역할을 해줌, 마지막 장을 넘길 때 종료
		            	int id = rs.getInt("id");
		            	String regDate = rs.getString("regDate");
		            	String updateDate = rs.getString("updateDate");
		            	String title = rs.getString("title");
		            	String content = rs.getString("content");
		            	
		            	Article article = new Article(id, regDate, updateDate, title, content);
		            	articles.add(article);
		            	// while 문이 종료되기 전에 리스트에 저장
		            }
		            
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		        	if (rs != null) {
		        		try {
		        			rs.close();
		        		} catch (SQLException e) {
		        			e.printStackTrace();
		        		}
		        	}
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
		        }
				
				if(articles.size() == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				
				System.out.println("	번호	|	제목	");
				
				for(Article article : articles) {
					System.out.println(article.id + "	번	| " + article.title);
				}
			}
			
		} 
		
		sc.close();
		System.out.println("== 프로그램 끝 ==");
		
	}	
}
