package spms.servlets;

import java.io.IOException;

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
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
						
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
	
			Member member;
			member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));
			if (member != null) {
				request.setAttribute("member", member);
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
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

		/*
		 * 필터적용을 위해 주석처리 request.setCharacterEncoding("UTF-8");
		 */

		try {
			ServletContext sc = this.getServletContext();
						
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
			Member member = new Member().setEmail(request.getParameter("email")).setName(request.getParameter("name")).setNo(Integer.parseInt(request.getParameter("no")));
			memberDao.update(member);			

			response.sendRedirect("list");
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}

}
