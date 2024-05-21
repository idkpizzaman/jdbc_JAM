package com.koreaIT.JAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;

public class Main {
	public static void main(String[] args) {
		
		System.out.println("== 프로그램 시작 ==");
		
		int id = 0;
		String cmd;
		String title;
		String content;
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		List<Article> articles = new ArrayList<>();
		
		while(true) {System.out.println("명령어: ");
		cmd = sc.nextLine().trim();
		
		if(cmd.equals("article write")) {
			System.out.println("제목: ");
			title = sc.nextLine().trim();
			System.out.println("내용: ");
			content = sc.nextLine().trim();
			
			id++; 
			
			System.out.println(id + "번 게시물이 작성되었습니다.");
			
			Article article = new Article(id, title, content);
			articles.add(article);
			
		} else if(cmd.equals("article list")) {			
			for(int i = articles.size() - 1; i  >= 0; i--) {
				if(articles.size() == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					continue;
				}
				Article article = articles.get(i);
				System.out.println("  번호  |  제목  ");
				System.out.println(article.id + "번 | " + article.title);

			}
		}
	}
		
	}	
}
