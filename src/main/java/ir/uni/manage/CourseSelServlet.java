package ir.uni.manage;

import db.CourseSel;
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


@WebServlet(name = "CourseSelServlet", urlPatterns = {"/manage/course/select"})
public class CourseSelServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title></title></head>");
            out.println("<body>");
            out.println("<h1>Hello,CourseSelServlet world!</h1>");  // says Hello
            out.println("<p>This is Text</p>");
            out.println("</body></html>");
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();


        try {
            String stCode = req.getParameter("stCode");
            String cCode = req.getParameter("cCode");
            String action = req.getParameter("action");

            if (stCode == null || cCode == null  | action == null ||
                    stCode.isBlank() || cCode.isBlank()) {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "fail")
                        .add("message", "Missing or empty parameters")
                        .build();
                out.print(errorResponse);
                return;
            }
            CourseSel cs = new CourseSel(Integer.parseInt(stCode),  Integer.parseInt(cCode));
            if(action.equals("save")){
                int state = CourseSelDAO.save(cs);
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Course selected successfully")
                            .build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Failed to select course")
                            .build();
                    out.print(errorResponse);
                }
            }else if(action.equals("update")){
                String oldId = req.getParameter("oldId");
                int state = CourseSelDAO.update(cs, Integer.parseInt(oldId));
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Course updated successfully")
                            .build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Failed to update course")
                            .build();
                    out.print(errorResponse);
                }
            }else{
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "fail")
                        .add("message", "Invalid action")
                        .build();
                out.print(errorResponse);
            }

        } catch (Exception e) {
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("status", "fail")
                    .add("message", "An error occurred while adding or updating the course")
                    .add("details", e.getMessage())
                    .build();
            out.print(errorResponse);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try{
            String stCode = req.getParameter("stCode");
            String cCode = req.getParameter("cCode");
            int stId = Integer.parseInt(stCode);
            int cId = Integer.parseInt(cCode);
            CourseSel c = CourseSelDAO.getSelCourseByIds(stId,cId);
            if (c != null) {
                int state = CourseSelDAO.delete(c.getCourseSelId());
                if (state > 0) {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Record deleted successfully!")
                            .build();
                    out.print(errorResponse);

                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Sorry! unable to delete record")
                            .build();
                    out.print(errorResponse);
                }
            } else {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "fail")
                        .add("message", "Record not found")
                        .build();
                out.print(errorResponse);
            }
        }catch (Exception e){
            e.printStackTrace();
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("status", "fail")
                    .add("message", "An error occurred while deleting the record")
                    .add("details", e.getMessage())
                    .build();
            out.print(errorResponse);
        }
    }
}