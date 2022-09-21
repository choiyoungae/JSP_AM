package com.KoreaIT.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

@WebServlet("/article/modify")
public class ArticleModifyServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		
		// 현재 로그인된 사용자와 게시글 작성자가 같은 사람인지 확인

		HttpSession session = request.getSession();

		int loginedMemberId = -1;
		
		if(session.getAttribute("loginedMemberLoginId") != null) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
		}
		
		if(loginedMemberId == -1) {
			response.getWriter().append("<script>alert('로그인이 필요한 기능입니다.'); history.back();</script>");
			return;
		}

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

			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = SecSql.from("SELECT memberId");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			int articleWriterId = DBUtil.selectRowIntValue(conn, sql);
			
			if(loginedMemberId != articleWriterId) {				
				response.getWriter().append("<script>alert('본인이 작성한 글만 가능합니다.'); history.back();</script>");
				return;
			}
			
			// 게시글 수정 진행
			
			sql = SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);

			request.setAttribute("articleRow", articleRow);
			request.setAttribute("id", id);
			request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);

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
		doGet(request, response);
	}

}