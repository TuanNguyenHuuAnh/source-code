//package vn.com.unit.ep2p.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * CORSFilter Class
// * Cross Domain (REST API Call)
// * @author pham
// *
// */
//public class CORSFilter implements Filter {
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//	}
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//	    HttpServletResponse response = (HttpServletResponse) res;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "X-Authorization, Origin, X-Requested-With, Content-Type, Accept, Cache-Control, X-Csrf-Token");
//        response.setHeader("Cache-control", "no-store");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("X-Content-Type-Options", "nosniff");
//        chain.doFilter(req, res);
//	}
//
//	@Override
//	public void destroy() {
//	}
//
//}
