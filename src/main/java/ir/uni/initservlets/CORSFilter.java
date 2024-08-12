package ir.uni.initservlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/manage/*", "*.html"})
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
//        // Check if the user is logged in
        if (session == null || session.getAttribute("admin") == null) {
            // If not logged in, redirect to login.jsp
            System.out.println("test");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } else {
            // If logged in, continue with the requested resource
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
