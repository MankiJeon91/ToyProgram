package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 필터적용을 위해 주석처리
		 * request.setCharacterEncoding("UTF-8");
		 */
		try {
			ServletContext sc = this.getServletContext();			
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
			Member member = new Member().setEmail(request.getParameter("email")).setPassword(request.getParameter("password")).setName(request.getParameter("name"));
			memberDao.insert(member);
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title></head>");
			out.println("<body>");
			out.println("<p>등록성공</p>");
			out.println("</body></html>");
			
			response.addHeader("Refresh", "1;url=list");

		} catch (Exception e) {
			throw new ServletException(e);
		} 
	}

}
