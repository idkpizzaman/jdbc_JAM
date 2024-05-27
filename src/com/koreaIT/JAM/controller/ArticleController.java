package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;
import com.koreaIT.JAM.session.Session;

public class ArticleController extends Controller{
	
	private ArticleService articleService;
	
	public ArticleController(Connection connection, Scanner sc) {
		this.articleService = new ArticleService(connection);
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
			break;
		case "detail":
			showDetail(cmd);
			break;
		case "modify":
			doModify(cmd);
			break;
		case "delete":
			doDelete(cmd);
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			break;
		}
	}
	
	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String content = sc.nextLine();
		
		int id = articleService.doWrite(Session.getLoginedMemberId(), title, content);
        
		System.out.printf("%d번 게시물이 작성되었습니다\n", id);
		
	}
	
	public void showList() {
		System.out.println("== 게시물 목록 ==");
		List<Article> articles = articleService.showList();
			
		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다");
			return;
		}
			
		System.out.println("	번호	|		제목		|		작성일		|	작성자	");
	
		for (Article article : articles) {
			System.out.printf("	%d	|		%s		|	%s	|	%s\n", article.id, article.title, article.regDate, article.writerName);
		}
		
	}
	
	public void showDetail(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
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
		
		System.out.printf("번호: %d\n" , article.id);
		System.out.printf("제목: %s\n" , article.title);
		System.out.printf("내용: %s\n" , article.content);
		System.out.printf("작성자: %s\n" , article.writerName);
		System.out.printf("작성일: %s\n" , article.regDate);
		System.out.printf("수정일: %s\n" , article.updateDate);
	}
	
	public void doModify(String cmd) {		
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다.");
			return;
		}
		
		Article article = articleService.getArticleById(id);
		
		if (article == null) {
			System.out.printf("%d번 게시물이 존재하지 않습니다.", id);
			return;
		}
		
		if (article.memberId != Session.getLoginedMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다.");
			return;
		}
		
		System.out.println("== 게시물 수정 ==");										
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String content = sc.nextLine();
        
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
		
		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.println("삭제할 게시물이 없습니다.");
			return;
		}
		
		if (article.memberId != Session.getLoginedMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다.");
			return;
		}

		articleService.doDelete(id);
        
        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}
}
