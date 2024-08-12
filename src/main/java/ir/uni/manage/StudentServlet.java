package ir.uni.manage;

import db.Student;
import db.StudentDAO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "StudentServlet", urlPatterns = {"/manage/student"})
public class StudentServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title></title></head>");
            out.println("<body>");
            out.println("<h1>Hello,StudentServlet world!</h1>");  // says Hello
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
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String gender = req.getParameter("gender");
            String action = req.getParameter("action");

            if (stCode == null || firstName == null || lastName == null || gender == null || action == null ||
                    stCode.isBlank() || firstName.isBlank() || lastName.isBlank() || gender.isBlank()) {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "error")
                        .add("message", "Missing or empty parameters")
                        .build();
                out.print(errorResponse);
                return;
            }
            Student st = new Student(Integer.parseInt(stCode), firstName, lastName, Byte.parseByte(gender));
            if(action.equals("save")){
                int state = StudentDAO.save(st);
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Student added successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("insCode", stCode)
                                    .add("firstName", firstName)
                                    .add("lastName", lastName)
                                    .add("gender", gender)
                            ).build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "faild")
                            .add("message", "Failed to add student")
                            .build();
                    out.print(errorResponse);
                }
            }else if(action.equals("update")){
                String oldId = req.getParameter("oldId");
                int state = StudentDAO.update(st, Integer.parseInt(oldId));
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Student updated successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("insCode", stCode)
                                    .add("firstName", firstName)
                                    .add("lastName", lastName)
                                    .add("gender", gender)
                            ).build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Failed to update student")
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
                    .add("message", "An error occurred while adding or updating the student")
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
            String stId = req.getParameter("id");
            int id = Integer.parseInt(stId);

            Student st = StudentDAO.getStudentById(id);
            if (st != null) {
                int state = StudentDAO.delete(st.getStCode());
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
                        .add("message", "User not found")
                        .build();
                out.print(errorResponse);
            }
        }catch (Exception e){
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("status", "fail")
                    .add("message", "An error occurred while deleting the student")
                    .add("details", e.getMessage())
                    .build();
            out.print(errorResponse);
        }
    }

}