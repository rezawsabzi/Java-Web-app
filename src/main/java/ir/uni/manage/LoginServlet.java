package ir.uni.manage;

import db.Admin;
import db.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LoginServlet"  , urlPatterns = {"/login-servlet"} )
public class LoginServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username != null && password!= null &&!username.isEmpty() &&!password.isEmpty() ) {
                Admin admin = AdminDAO.validateLogin(username,password);
                if (admin != null) {
                    request.getSession().setAttribute("admin", admin);
                    response.sendRedirect("index.html");
                }else {
                    response.sendRedirect("login.jsp?error=2");
                }
            }

            else {
                response.sendRedirect("login.jsp?error=1");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}