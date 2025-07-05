package vn.com.unit.ep2p.filter;

import java.io.IOException;

//import javax.servlet.Filter;
import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//import org.springframework.stereotype.Component;
//import javax.servlet.http.HttpServletResponse;

//@WebFilter(urlPatterns = {"/*" })
@Component
public class CacheRequestFilter extends OncePerRequestFilter implements Ordered {

	/*
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

//		request.getContentType();

//		if (CommonStringUtil.isBlank(request.getContentType()) || !request.getContentType().contains("multipart/form-data")) {
//			RequestWrapper requestWrapper= new RequestWrapper((HttpServletRequest) request);
//
//			chain.doFilter(requestWrapper, response);
//		}

		RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);

		chain.doFilter(requestWrapper, response);

	}
	*/
	
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 8;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);

		filterChain.doFilter(requestWrapper, response);	
	}

}
