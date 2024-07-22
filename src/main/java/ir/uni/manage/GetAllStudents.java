
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Reza Sabzi
 */
@WebServlet(name = "GetAllStudents", urlPatterns = {"/getstudents"})
public class GetAllStudents extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

        response.setContentType("application/json;charset=UTF-8");
        
        JsonArrayBuilder jsonArrayB = Json.createArrayBuilder();


        List<Student> stList = StudentDAO.getAllStudents();
        for (Student st : stList) {
            ArrayList<CourseSel> cslist = CourseSelDAO.getCourseSelForStCode(st.getStCode());
            JsonArrayBuilder jSelCourseArr = Json.createArrayBuilder();
            for(CourseSel cs: cslist){
                CoursePresentation cp = CoursePresDAO.getCoursePresById(cs.getCoursePresId());
                Instructor i = InstructorDAO.getInstructorById(cp.getInstructor().getInsCode());
                Course c = CourseDAO.getCourseById(cp.getCourse().getCourseId());
                JsonObject stObj = Json.createObjectBuilder()
                        .add("coursePresId", cp.getCoursePresId())
                        .add("course", Json.createObjectBuilder()
                                .add("courseId", c.getCourseId())
                                .add("title", c.getTitle())
                                .add("unitNumbers", c.getUnitNumbers()).build())
                        .add("instructor", Json.createObjectBuilder()
                                .add("insCode", i.getInsCode())
                                .add("firstName", i.getFirstName())
                                .add("lastName", i.getLastName())
                                .add("gender", i.getGender()).build())
                        .build();

                jSelCourseArr.add(stObj);
            }
            JsonObject stObj = Json.createObjectBuilder()
                    .add("stCode", st.getStCode())
                    .add("firstName", st.getFirstName())
                    .add("lastName", st.getLastName())
                    .add("gender", st.getGender())
                    .add("selectedCourses", jSelCourseArr)
                    .build();

            jsonArrayB.add(stObj);
        }
        JsonArray jsArr = jsonArrayB.build();
        
         JsonObject obj = Json.createObjectBuilder()
                .add("students", jsArr)
                .build();
         
         response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
//        out.println(obj);

        out.print(obj);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
