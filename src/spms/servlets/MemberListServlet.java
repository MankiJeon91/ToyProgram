package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost/studydb", "study", "study");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select MNO, MNAME, EMAIL, CRE_DATE" + "from MEMBERS" + "order by MNO ASC");

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<html><head><title>회원목록</title></head>");
			writer.println("<body><h1>회원목록</h1>");
			while (rs.next()) {
				writer.println(rs.getInt("MNO") + "," + rs.getString("MNAME") + "," + rs.getString("EMAIL") + ","
						+ rs.getDate("CRE_DATE") + "<br>");
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
