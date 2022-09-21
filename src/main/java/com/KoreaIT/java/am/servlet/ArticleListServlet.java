package com.KoreaIT.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.config.Config;
import com.KoreaIT.java.am.exception.SQLErrorException;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		// DB 연결

		Connection conn = null;

		String driverName = Config.getDBDriverClassName();

		try {
			Class.forName(driverName);

		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다.");
			System.out.println("프로그램을 종료합니다.");
			return;
		}

		try {
			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassword());
			
			int itemsInAPage = 15;
			int page = 1;
			if(request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			int limitFrom = (page - 1) * itemsInAPage;
			
			SecSql articleCountSql = SecSql.from("SELECT COUNT(*)");
			articleCountSql.append("FROM article");
			
			int articleCountNum = DBUtil.selectRowIntValue(conn, articleCountSql);
			int pageCountNum = (int)Math.ceil((double)articleCountNum / itemsInAPage);

			// 게시글 작성자 포함한 게시글 보내기
			SecSql sql = SecSql.from("SELECT A.*, M.name AS writer");
			sql.append("FROM article AS A");
			sql.append("INNER JOIN `member` AS M");
			sql.append("ON A.memberId = M.id");
			sql.append("ORDER BY A.id DESC");
			sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

			List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
			
			HttpSession session = request.getSession();
			
			boolean isLogined = false;
			int loginedMemberId = -1;
			Map<String, Object> loginedMemberRow = null;
			
			if(session.getAttribute("loginedMemberLoginId") != null) {
				isLogined = true;
				loginedMemberId = (int)session.getAttribute("loginedMemberId");
				
				sql = SecSql.from("SELECT * FROM `member`");
				sql.append("WHERE id = ?", loginedMemberId);
				
				loginedMemberRow = DBUtil.selectRow(conn, sql);
			}
			
			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("loginedMemberRow", loginedMemberRow);

			request.setAttribute("articleRows", articleRows);
			request.setAttribute("pageCountNum", pageCountNum);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLErrorException e) {
			e.getOrigin().printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}