package filter.impl;

import controllers.Controller;
import filter.helper.Authenticator;
import filter.helper.AuthenticatorImpl;
import entity.User;
import filter.AbstractFilter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "ControllerFilter", urlPatterns = { "/controller" })
public class ControllerFilter extends AbstractFilter {

    private Authenticator authenticator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authenticator = new AuthenticatorImpl();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        User user = getUser(servletRequest);
        String commandName = servletRequest.getParameter(Controller.COMMAND_PARAMETER);
        if (authenticator.hasAuthority(user, commandName)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new ServletException("User or guest has no permission to command: " + commandName);
        }

    }

}
