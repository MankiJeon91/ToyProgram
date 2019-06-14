package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

	FilterConfig config;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextFilter)
			throws IOException, ServletException {
		/* servlet이 실행되기 전에 해야 할 작업 */
		request.setCharacterEncoding(config.getInitParameter("encoding"));
		
		/* 다음 필터를 호출. 더 이상 필터가 없다면 servlet의 service()가 호출됨. */
		nextFilter.doFilter(request, response);
		
		/* servlet을 실행한 후, 클라이언트에게 응답하기 전에 해야할 작업 */
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
