
package ir.uni.manage;

import db.Student;
import db.StudentDAO;
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
@WebServlet(name = "GetAllStudents", urlPatterns = {"/GetAllStudents"})
public class GetAllStudents extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json;charset=UTF-8");
        
        JsonArrayBuilder jsonArrayB = Json.createArrayBuilder();
        List<Student> stList = StudentDAO.getAllStudents();
        for (Student st : stList) {
            JsonObject stObj = Json.createObjectBuilder()
                    .add("stCode", st.getStCode())
                    .add("firstName", st.getFirstName())
                    .add("lastName", st.getLastName())
                    .add("gender", st.getGender()).build();

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
