package ir.uni.manage;

import db.CourseSelDAO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "SetGradeServlet", urlPatterns = {"/manage/course/grade"})
public class SetGradeServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String grade = request.getParameter("grade");
            String id = request.getParameter("courseSelId");
            if (grade == null || id == null ||
                    grade.isBlank() || id.isBlank()) {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "fail")
                        .add("message", "Missing or empty parameters")
                        .build();
                out.print(errorResponse);
                return;
            }
            int state = CourseSelDAO.updateGrade(Integer.parseInt(id), Double.parseDouble(grade));
            if (state > 0) {
                JsonObject successResponse = Json.createObjectBuilder()
                        .add("status", "success")
                        .add("message", "Course grade updated successfully")
                        .build();
                out.print(successResponse);
            } else {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "fail")
                        .add("message", "Failed to update course grade")
                        .build();
                out.print(errorResponse);
            }
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