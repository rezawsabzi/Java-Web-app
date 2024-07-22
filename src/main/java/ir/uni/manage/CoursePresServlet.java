package ir.uni.manage;

import db.*;
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

@WebServlet(name = "CoursesPresServlet", urlPatterns = {"/coursepres"})
public class CoursePresServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        JsonArrayBuilder jAB = Json.createArrayBuilder();
        List<CoursePresentation> cplist = CoursePresDAO.getAllCoursesPres();

        for(CoursePresentation cp : cplist){
            Course c = CourseDAO.getCourseById(cp.getCourse().getCourseId());
            Instructor i = InstructorDAO.getInstructorById(cp.getInstructor().getInsCode());
            JsonObject jObj = Json.createObjectBuilder()
                    .add("coursePresId", cp.getCoursePresId())
                    .add("course" , Json.createObjectBuilder()
                            .add("courseId", c.getCourseId())
                            .add("title", c.getTitle())
                            .add("unitNumber", c.getUnitNumbers())
                            .build())
                    .add("instructor" , Json.createObjectBuilder()
                            .add("insCode", i.getInsCode())
                            .add("firstName", i.getFirstName())
                            .add("lastName", i.getLastName())
                            .add("gender", i.getGender())
                            .build())
                    .build();
            jAB.add(jObj);
        }
        JsonArray jArr = jAB.build();

        JsonObject obj = Json.createObjectBuilder()
                .add("status", "success")
                .add("coursesPreses",jArr ).build();

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
}