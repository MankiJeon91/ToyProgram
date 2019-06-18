package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Connection conn = null; Statement stmt = null; ResultSet rs = null;
		 */

		try {
			ServletContext sc = this.getServletContext();
			Connection conn = (Connection) sc.getAttribute("conn");
			
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			
			request.setAttribute("members", memberDao.selectList());
			/*
			 * ServletContext에 저장된 DBConnection 사용하기 위해 주석처리
			 * Class.forName(sc.getInitParameter("driver")); conn =
			 * DriverManager.getConnection(sc.getInitParameter("url"),
			 * sc.getInitParameter("username"), sc.getInitParameter("password"));
			 */

			/*
			 * conn = (Connection) sc.getAttribute("conn"); stmt = conn.createStatement();
			 * rs = stmt.
			 * executeQuery("SELECT MNO, MNAME, EMAIL, CRE_DATE FROM MEMBERS ORDER BY MNO ASC"
			 * );
			 */

			response.setContentType("text/html;charset=UTF-8");

			/*
			 * List<Member> members = new ArrayList<Member>();
			 * 
			 * while (rs.next()) { members.add(new
			 * Member().setNo(rs.getInt("MNO")).setName(rs.getString("MNAME"))
			 * .setEmail(rs.getString("EMAIL")).setCreatedDate(rs.getDate("CRE_DATE"))); }
			 * 
			 * // request에 회원 목록 데이터를 보관한다. request.setAttribute("members", members);
			 */

			// JSP로 출력을 위임한다.
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
			// 이제 해당 JSP와 serlvet은 request와 response를 공유한다.
			rd.include(request, response);

			/*
			 * JSP를 적용하기 위해 삭제 PrintWriter writer = response.getWriter();
			 * writer.println("<html><head><title>회원목록</title></head>");
			 * writer.println("<body><h1>회원목록</h1>");
			 * 
			 * writer.println("<p><a href='add'>신규 회원</a></p>");
			 * 
			 * while (rs.next()) { writer.println(rs.getInt("MNO") + "," +
			 * "<a href='update?no=" + rs.getInt("MNO") + "'>" + rs.getString("MNAME") +
			 * "</a>," //<a href='update?no=1'>홍길동</a>형식 + rs.getString("EMAIL") + "," +
			 * rs.getDate("CRE_DATE") + "<a href='delete?no=" + rs.getInt("MNO") +
			 * "'>[삭제]</a><br>"); } writer.println("</body></html>");
			 */
		} catch (Exception e) {
			/*
			 * // 오류 시 오류 화면으로 대체 throw new ServletException(e);
			 */

			// 에러 발생 시, Error.jsp로 forwarding

			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);

		}
	}

}
