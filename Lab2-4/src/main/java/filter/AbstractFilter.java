package filter;

import entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractFilter implements Filter {

    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    protected User getUser(ServletRequest servletRequest) {
        HttpSession session = getSession(servletRequest);
        return (User) session.getAttribute(USER_ATTRIBUTE);
    }

    protected HttpSession getSession(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return request.getSession();
    }

}
