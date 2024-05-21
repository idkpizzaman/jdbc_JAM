package com.koreaIT.JAM.dto;

public class Article {
	public int id;
	public String title;
	public String content;

	public Article(int id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}
}
