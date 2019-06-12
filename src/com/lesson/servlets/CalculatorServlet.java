package com.lesson.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		/*
		 * ServletRequest객체는 클라이언트의 요청 정보를 다룰 때 사용한다. getParameter()는 GET이나 POST 요청으로
		 * 들어오는 매개변수 값을 꺼낼 때 사용한다.
		 */
		int a = Integer.parseInt(request.getParameter("a"));
		int b = Integer.parseInt(request.getParameter("b"));
		
		/*
		 * ServletResponse객체는 응답과 관련된 기능을 제공한다. 클라이언트에게 출력하는 데이터의 인코딩 타입을 설정하고, 문자집합을
		 * 지정하고, 출력 데이터를 임시 보관하는 버퍼의 크기를 조정하거나, 데이터를 출력하기 위해 출력 스트림을 준비할 때 사용한다.
		 */
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("a=" + a + ", b=" + b + " 의 계산결과");
		writer.println("a + b = " + (a + b));
		writer.println("a - b = " + (a - b));
		writer.println("a * b = " + (a * b));
		writer.println("a / b = " + (a / b));
		writer.println("a % b = " + (a % b));
	}

}
