
package ir.uni.manage;

import db.Instructor;
import db.InstructorDAO;
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
@WebServlet(name = "InstructorsServlet", urlPatterns = {"/manage/instructor"})
public class InstructorServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
  
        JsonArrayBuilder jAB = Json.createArrayBuilder();
        List<Instructor> insList = InstructorDAO.getAllInstructors();
        
        for (Instructor instructor : insList) {
            JsonObject jObj = Json.createObjectBuilder()
                    .add("insCode", instructor.getInsCode())
                    .add("firstName", instructor.getFirstName())
                    .add("lastName" , instructor.getLastName())
                    .add("gender" , instructor.getGender()).build();
            jAB.add(jObj);
        }
        JsonArray jArr = jAB.build();
        JsonObject obj = Json.createObjectBuilder().add("instructors", jArr).build();
        
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
            String insCode = req.getParameter("insCode");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String gender = req.getParameter("gender");
            String action = req.getParameter("action");

            if (insCode == null || firstName == null || lastName == null || gender == null || action == null ||
                    insCode.isBlank() || firstName.isBlank() || lastName.isBlank() || gender.isBlank()) {
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", "error")
                        .add("message", "Missing or empty parameters")
                        .build();
                out.print(errorResponse);
                return;
            }
            Instructor ins = new Instructor(Integer.parseInt(insCode), firstName, lastName, Byte.parseByte(gender));
            if(action.equals("save")){
                int state = InstructorDAO.saveNewInstructor(ins);
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Instructor added successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("insCode", insCode)
                                    .add("firstName", firstName)
                                    .add("lastName", lastName)
                                    .add("gender", gender)
                            ).build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "faild")
                            .add("message", "Failed to add instructor")
                            .build();
                    out.print(errorResponse);
                }
            }else if(action.equals("update")){
                String oldId = req.getParameter("oldId");
                int state = InstructorDAO.update(ins, Integer.parseInt(oldId));
                if (state > 0) {
                    JsonObject successResponse = Json.createObjectBuilder()
                            .add("status", "success")
                            .add("message", "Instructor updated successfully")
                            .add("data", Json.createObjectBuilder()
                                    .add("insCode", insCode)
                                    .add("firstName", firstName)
                                    .add("lastName", lastName)
                                    .add("gender", gender)
                            ).build();
                    out.print(successResponse);
                } else {
                    JsonObject errorResponse = Json.createObjectBuilder()
                            .add("status", "fail")
                            .add("message", "Failed to update instructor")
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
                    .add("message", "An error occurred while adding or updating the instructor")
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

            Instructor i = InstructorDAO.getInstructorById(id);
            if (i != null) {
                int state = InstructorDAO.delete(i.getInsCode());
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
                    .add("message", "An error occurred while deletin the instructor")
                    .add("details", e.getMessage())
                    .build();
            out.print(errorResponse);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    } // </editor-fold>

}
