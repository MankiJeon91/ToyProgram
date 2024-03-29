package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"), sc.getInitParameter("password"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT MNO, MNAME, EMAIL, CRE_DATE FROM MEMBERS ORDER BY MNO ASC");

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<html><head><title>회원목록</title></head>");
			writer.println("<body><h1>회원목록</h1>");
			
			writer.println("<p><a href='add'>신규 회원</a></p>");
			
			while (rs.next()) {
				writer.println(rs.getInt("MNO") + "," +
						"<a href='update?no=" + rs.getInt("MNO") + "'>" + rs.getString("MNAME") + "</a>,"	//<a href='update?no=1'>홍길동</a>형식 
						+ rs.getString("EMAIL") + ","
						+ rs.getDate("CRE_DATE") + "<a href='delete?no=" + rs.getInt("MNO") + "'>[삭제]</a><br>");
			}
			writer.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {}
			try {
				if(stmt != null)
					stmt.close();
			} catch (Exception e2) {}
			try {
				if(conn != null)
					conn.close();
			} catch (Exception e2) {}
		}
	}

}
