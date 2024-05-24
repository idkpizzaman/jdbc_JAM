package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;

public class ArticleController extends Controller{
	
	private ArticleService articleService;
	
	public ArticleController(Connection connection, Scanner sc) {
		this.articleService = new ArticleService(connection);
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
			showDetail(cmd);
		case "modify":
			doModify(cmd);
		case "delete":
			doDelete(cmd);
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
		}
	}
	
	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String content = sc.nextLine();
		
		int id = articleService.doWrite(title, content);
        
		System.out.printf("%d번 게시물이 작성되었습니다\n", id);
		
	}
	
	public void showList() {
		System.out.println("== 게시물 목록 ==");
		List<Article> articles = articleService.showList();
			
		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다");
		}
			
		System.out.println("	번호	|		제목			|	작성일	");
		for (Article article : articles) {
			System.out.printf("	%d	|		%s		|	%s\n", article.getId(), article.getTitle(), article.getRegDate());
		}
		
	}
	
	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다.");
			return;
		}
		
		Article article = articleService.showDetail(id);
		
		if (article == null) {
			System.out.println(id + "번 게시물이 없습니다.");
			return;
		}
		
		System.out.println("== 게시물 자세히보기 ==");
		
		System.out.println("번호: " + id);
		System.out.println("제목: " + article.getTitle());
		System.out.println("내용: " + article.getContent());
		System.out.println("작성일: " + article.getRegDate());
		System.out.println("수정일: " + article.getUpdateDate());
	}
	
	public void doModify(String cmd) {		
		int id = ArticleService.getCmdnum(cmd);

		System.out.println("== 게시물 수정 ==");										
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String content = sc.nextLine();
   
        int articleCount = articleService.getArticleCount(id);
        
        if(articleCount == 0) {
        	System.out.println("수정할 게시물이 없습니다.");
        	return;
        }
        
        articleService.doModify(id, title, content);
        
        System.out.println(id + "번 게시물이 수정되었습니다\n");
        
	}
	
	public void doDelete(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
		System.out.println("== 게시물 삭제 ==");
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다.");
			return;
		}
		
		int articleCount = articleService.getArticleCount(id);

		if (articleCount == 0) {
			System.out.println("삭제할 게시물이 없습니다.");
			return;
		}

		articleService.doDelete(id);
        
        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}
}
