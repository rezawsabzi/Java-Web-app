/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ir.uni.manage;

import db.Course;
import db.CourseDAO;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Reza Sabzi
 */
@WebServlet(name = "CoursesServlet", urlPatterns = {"/course"})
public class CoursesServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        JsonArrayBuilder jAB = Json.createArrayBuilder();
        List<Course> clist = CourseDAO.getAllCourses();
        
        for(Course c : clist){
                JsonObject jObj = Json.createObjectBuilder()
                        .add("courseId", c.getCourseId())
                        .add("title" , c.getTitle())
                        .add("unitNumber" , c.getUnitNumbers()).build();
                jAB.add(jObj);
        }
        JsonArray jArr = jAB.build();
        
        JsonObject obj = Json.createObjectBuilder()
                .add("courses",jArr ).build();
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        
        out.print(obj);
               

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();


        try {
            String courseId = req.getParameter("courseId");
            String title = req.getParameter("title");
            String unitNumbers = req.getParameter("unitNumbers");
            String action = req.getParameter("action");

            if (courseId == null || title == null || unitNumbers == null | action == null ||
                    courseId.isBlank() || title.isBlank() || unitNumbers.isBlank()) {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "fail")
                        .add("message", "Missing or empty parameters")
                        .build();
                out.print(errorResponse);
                return;
            }
            Course co = new Course(Integer.parseInt(courseId), title, Byte.parseByte(unitNumbers));
            if(action.equals("save")){
                int state = CourseDAO.save(co);
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Course added successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("courseId", co.getCourseId())
                                    .add("title", co.getTitle())
                                    .add("unitNumbers", co.getUnitNumbers())
                            ).build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Failed to add course")
                            .build();
                    out.print(errorResponse);
                }
            }else if(action.equals("update")){
                String oldId = req.getParameter("oldId");
                int state = CourseDAO.update(co, Integer.parseInt(oldId));
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Course updated successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("courseId", co.getCourseId())
                                    .add("title", co.getTitle())
                                    .add("unitNumbers", co.getUnitNumbers())
                            ).build();
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
            String InsId = req.getParameter("id");
            int id = Integer.parseInt(InsId);

            Course c = CourseDAO.getCourseById(id);
            if (c != null) {
                int state = CourseDAO.delete(c.getCourseId());
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
                    .add("message", "An error occurred while deleting the course")
                    .add("details", e.getMessage())
                    .build();
            out.print(errorResponse);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
