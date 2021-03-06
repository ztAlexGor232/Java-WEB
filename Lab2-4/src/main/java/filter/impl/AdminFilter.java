package filter.impl;

import entity.User;
import filter.AbstractFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = { "/rooms" })
public class AdminFilter extends AbstractFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        User user = getUser(servletRequest);
        if (user == null) {
            throw new ServletException("User is not authenticated");
        }
        if (!user.isAdmin()) {
            throw new ServletException("User is not authorized");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

