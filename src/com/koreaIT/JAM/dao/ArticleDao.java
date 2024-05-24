package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleDao {
	
	private Connection connection;

	public ArticleDao(Connection connection) {
		this.connection = connection;
	}

	public int doWrite(String title, String content) {
		
		SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append(" SET regDate = NOW()");
        sql.append(", updateDate = NOW()");
        sql.append(", title = ?", title);
    	sql.append(", content = ?", content);
    	
    	return DBUtil.insert(connection, sql);
	}

	public List<Map<String, Object>> showList() {
    	
    	SecSql sql = new SecSql();
    	sql.append("SELECT * FROM article");
    	sql.append(" ORDER BY id DESC;");
    	
    	// Map 은 키-밸류로 이루어져 있음
    	// 일단 받기 위해서 밸류값을 Object 로 받고, 나중에 활용할 때 형변환을 할 수 있게끔 함
    	// Key = 받아올 리스트의 컬럼명, Object = 안에 들어있는 값
    	
    	return DBUtil.selectRows(connection, sql);
	}

	public Map<String, Object> showDetail(int id) {
		
		SecSql sql = new SecSql();					
		sql.append("SELECT *");
		sql.append(" FROM article");
		sql.append("WHERE id = ?" + id);	
		
		return DBUtil.selectRow(connection, sql);
	}
	
	public void doModify(int id, String title, String content) {
		SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append(" SET updateDate = NOW()");
        sql.append(", title = ?" + title);
        sql.append(", content = ?" + content);
        sql.append(" WHERE id = ?" + id);
        
        DBUtil.update(connection, sql);
	}

	public void doDelete(int id) {
		
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append(" WHERE id = ?" + id);
		
//		DBUtil.update(connection, sql);
//		위나 아래나 하는 일은 똑같지만 좀더 쉽게 이해할 수 있도록 메소드 이름을 맞게 변경
		DBUtil.delete(connection, sql);		
	}

	public int getArticleCount(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRowIntValue(connection, sql);
	}
}
