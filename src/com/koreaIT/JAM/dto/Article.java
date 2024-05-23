package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	public int id;
	public String title;
	public String content;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.content = (String) articleMap.get("content");
	}
}
