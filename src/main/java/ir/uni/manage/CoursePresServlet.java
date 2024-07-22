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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();


        try {
            String courseId = req.getParameter("courseId");
            String coursePresId = req.getParameter("coursePresId");
            String insCode = req.getParameter("insCode");
            String action = req.getParameter("action");

            if (courseId == null || coursePresId == null || insCode == null | action == null ||
                    courseId.isBlank() || coursePresId.isBlank() || insCode.isBlank()) {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "fail")
                        .add("message", "Missing or empty parameters")
                        .build();
                out.print(errorResponse);
                return;
            }
            Course c = new Course();
            c.setCourseId(Integer.parseInt(courseId));
            Instructor i = new Instructor();
            i.setInsCode(Integer.parseInt(insCode));
            CoursePresentation cp = new CoursePresentation(Integer.parseInt(coursePresId),c , i);
            if(action.equals("save")){
                int state = CoursePresDAO.save(cp);
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "CoursePres added successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("coursePresId", cp.getCoursePresId())
                                    .add("courseId", cp.getCourse().getCourseId())
                                    .add("insCode", cp.getInstructor().getInsCode())
                            ).build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Failed to add coursePres")
                            .build();
                    out.print(errorResponse);
                }
            }else if(action.equals("update")){
                String oldId = req.getParameter("oldId");
                int state = CoursePresDAO.update(cp, Integer.parseInt(oldId));
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Course Pres updated successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("coursePresId", cp.getCoursePresId())
                                    .add("courseId", cp.getCourse().getCourseId())
                                    .add("insCode", cp.getInstructor().getInsCode())
                            ).build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Failed to update coursePres")
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
            e.printStackTrace();
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

            CoursePresentation c = CoursePresDAO.getCoursePresById(id);
            if (c != null) {
                int state = CoursePresDAO.delete(c.getCoursePresId());
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
}