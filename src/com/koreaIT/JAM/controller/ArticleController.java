package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleController extends Controller{
	private Connection connection;
	
	public ArticleController(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
	}
	
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch(methodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
		case "detail":
			showDetail();
		case "modify":
			doModify();
		case "delete":
			doDelete();
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
		}
	}
	
	private void doWrite() {
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
		
	}
	
	private void showList() {
	        
	    	List<Article> articles = new ArrayList<>();
	    	
	    	SecSql sql = new SecSql();
	    	sql.append("SELECT * FROM article");
	    	sql.append(" ORDER BY id DESC;");
	    	
	    	List<Map<String, Object>> articleListMap = DBUtil.selectRows(connection, sql);
	    	// Map 은 키-밸류로 이루어져 있음
	    	// 일단 받기 위해서 밸류값을 Object 로 받고, 나중에 활용할 때 형변환을 할 수 있게끔 함
	    	// Key = 받아올 리스트의 컬럼명, Object = 안에 들어있는 값
	    	
	    	for(Map<String, Object> articleMap : articleListMap) {
	    		// 맵 형태의 리스트의 articleMap을 articleListMap으로 순환해서 반복문 실행
	    		articles.add(new Article(articleMap));
	    	}

			System.out.println("== 게시물 목록 ==");
			
			if (articles.size() == 0) {
				System.out.println("게시물이 존재하지 않습니다");
			}
			
			System.out.println("	번호	|		제목			|	작성일	");
			for (Article article : articles) {
				System.out.printf("	%d	|		%s		|	%s\n", article.getId(), article.getTitle(), article.getRegDate());
			}
			
		}
	
	private void doModify() {		        			       

		System.out.println("== 게시물 수정 ==");										
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String content = sc.nextLine();
		
		Article foundArticle = new Article(null);
		
		SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append(" SET updateDate = NOW()");
        sql.append(", title = ?" + title);
        sql.append(", content = ?" + content);
        sql.append(" WHERE id = ?" + foundArticle.getId() + ";");
        
        int articleCount = DBUtil.selectRowIntValue(connection, sql);
        
        if(articleCount == 0) {
        	System.out.println("수정할 게시물이 없습니다.");
        	return;
        }
        
        System.out.println(foundArticle.getId() + "번 게시물이 수정되었습니다\n");
        
	}
	
	private void showDetail() {
		System.out.println("== 게시물 자세히보기 ==");
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		SecSql sql = new SecSql();					
		sql.append("SELECT *");
		sql.append(" FROM article");
		sql.append("WHERE id = ?" + id);	
		
		Map<String, Object> articleMap = DBUtil.selectRow(connection, sql);
		
		if (articleMap.isEmpty()) {
			System.out.println(id + "번 게시물이 없습니다.");
			return;
		}
		
		Article article = new Article(articleMap);
		
		System.out.println("번호: " + id);
		System.out.println("제목: " + article.getTitle());
		System.out.println("내용: " + article.getContent());
		System.out.println("작성일: " + article.getRegDate());
		System.out.println("수정일: " + article.getUpdateDate());
        
		DBUtil.update(connection, sql);

	}
	
	private void doDelete() {
		Article foundarticle = new Article(null);
		
		System.out.println("== 게시물 삭제 ==");
		
		SecSql sql = new SecSql();

		if (foundarticle.getId() == 0) {
			System.out.println("삭제할 게시물이 없습니다.");
			return;
		}

		sql.append("DELETE FROM article");
		sql.append(" WHERE id = ?" + foundarticle.getId());
		
//		DBUtil.update(connection, sql);
//		위나 아래나 하는 일은 똑같지만 좀더 쉽게 이해할 수 있도록 메소드 이름을 맞게 변경
		DBUtil.delete(connection, sql);
        
        System.out.printf("%d번 게시물이 삭제되었습니다\n", foundarticle.getId());
	}
}
