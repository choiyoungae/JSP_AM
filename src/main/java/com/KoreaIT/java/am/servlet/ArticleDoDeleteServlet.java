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

@WebServlet("/article/delete")
public class ArticleDoDeleteServlet extends HttpServlet {

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
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			// 현재 로그인된 사용자와 게시글 작성자가 같은 사람인지 확인

			HttpSession session = request.getSession();
			
			Map<String, Object> loginedMemberRow = null;
			
			if(session.getAttribute("loginedMemberLoginId") != null) {
				int loginedMemberId = (int)session.getAttribute("loginedMemberId");
				
				SecSql sql = SecSql.from("SELECT * FROM `member`");
				sql.append("WHERE id = ?", loginedMemberId);
				
				loginedMemberRow = DBUtil.selectRow(conn, sql);
			}
			
			SecSql sql = SecSql.from("SELECT memberId");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			int articleMemberId = DBUtil.selectRowIntValue(conn, sql);

			if(loginedMemberRow == null) {
				response.getWriter().append("<script>alert('로그인이 필요한 기능입니다.'); history.back();</script>");
				return;
			}
			
			if((int)loginedMemberRow.get("id") != articleMemberId) {				
				response.getWriter().append("<script>alert('본인이 작성한 글만 가능합니다.'); history.back();</script>");
				return;
			}
			
			// 게시글 삭제 진행

			sql = SecSql.from("DELETE");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(conn, sql);
			
			response.getWriter().append(String.format("<script>alert('%d번 글이 삭제되었습니다.'); location.replace('list');</script>", id));
			
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