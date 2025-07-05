package vn.com.unit.ep2p.config;

import org.springframework.context.annotation.Configuration;
import vn.com.unit.ep2p.utils.SqlSafeUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/api/*",filterName = "sqlFilter")
@Configuration
public class SqlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        ServletRequest request = servletRequest;
        ServletResponse response = servletResponse;
        //Get all request parameter names
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()){
            //Get parameter name
            String name = names.nextElement().toString();
            //Get the corresponding value of the parameter
            String[] values = request.getParameterValues(name);
            for (int i = 0; i < values.length; i++) {
                if (SqlSafeUtil.isSqlInjectionUnSafe(values[i])){
                    throw  new IOException ("the parameter in the request you sent contains illegal characters");
                }
            }
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
