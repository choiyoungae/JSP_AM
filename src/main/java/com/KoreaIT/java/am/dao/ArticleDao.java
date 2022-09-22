package com.KoreaIT.java.am.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.dto.Article;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArticleDao {
	
	private Connection conn;
	
	public ArticleDao(Connection conn) {
		this.conn = conn;
	}
	
	public int getTotalCount() {

		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article");
		
		int articleCountNum = DBUtil.selectRowIntValue(conn, sql);
		
		return articleCountNum;
	}

	public List<Article> getArticles(int itemsInAPage, int limitFrom) {
		
		SecSql sql = SecSql.from("SELECT A.*, M.name AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("ORDER BY A.id DESC");
		sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

		List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
		
		List<Article> articles = new ArrayList<>();
		
		for(Map<String, Object> articleRow : articleRows) {
			articles.add(new Article(articleRow));
		}
		
		return articles;
	}

}
