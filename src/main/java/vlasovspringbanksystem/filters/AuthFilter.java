package vlasovspringbanksystem.filters;

import org.springframework.stereotype.Component;
import vlasovspringbanksystem.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    private final String INDEX_PAGE = "/WEB-INF/view/index.jsp";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User userFromSession = (User) session.getAttribute("user");
        if (request.getRequestURI().startsWith("/changelang") || (request.getRequestURI().contains("/logout"))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (userFromSession != null) {
                if (request.getRequestURI().startsWith("/userpage")) {
                    if (userFromSession.getRole().getUserRoleValue().equals("user"))
                        filterChain.doFilter(servletRequest, servletResponse);
                    else
                        response.sendRedirect("/adminpage");
                } else if (request.getRequestURI().startsWith("/adminpage")) {
                    if (userFromSession.getRole().getUserRoleValue().equals("admin"))
                        filterChain.doFilter(servletRequest, servletResponse);
                    else
                        response.sendRedirect("/userpage");
                }
            } else {
                String login = request.getParameter("login");
                String password = request.getParameter("password");

                if (login != null && password != null) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
                }
            }
        }
    }
}