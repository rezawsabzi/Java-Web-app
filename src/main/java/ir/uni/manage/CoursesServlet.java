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
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
