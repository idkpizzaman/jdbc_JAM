package com.koreaIT.JAM.dto;

public class Article {
	public int id;
	public String title;
	public String content;
	public String regDate;
	public String updateDate;

	public Article(int id, String regDate, String updateDate, String title, String content) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.content = content;
	}
}
