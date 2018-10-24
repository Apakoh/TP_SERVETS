/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import DAOEx.DAOBis;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;


/**
 *
 * @author Spard
 */
@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {
    
    private DataSource myDataSource;
    private DAO dao;
    private DAOBis daobis;
    private String nomPays;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            nomPays = request.getParameter("STATE");
            this.myDataSource = DataSourceFactory.getDataSource();
            this.dao = new DAO(myDataSource);
            this.daobis = new DAOBis(myDataSource);
            List<CustomerEntity> liste=null;
            List<String> listePays = null;
            
            try 
            {
                liste = dao.customersInState(nomPays);
                listePays=daobis.stateList();
            } catch (DAOException ex) 
            {
                
            }
           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");
            out.println("<Style> table, th, td {\n" +
                        "   border: 1px solid black;\n" +
                        "}");
            out.println("</Style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
            
            out.println("<form method=\"get\">");
                out.println("<select name=\"STATE\">");
                    for (String pays : listePays) 
                    {
                        out.print("<option value=\"");
                        out.print(pays);
                        out.println("\">");
                        out.println(pays);
                        out.println("</option>");
                    }
               out.println("</select>");

               out.println("<button type=\"submit\">Valider</button>");
            out.println("</form>");
            
            out.println("<br />");
            
            out.println("<table>");
                out.println("<thead>");
                    out.println("<tr>");
                        out.println("<th>Id</th>");
                        out.println("<th>Name</th>)");
                        out.println("<th>Addresse</th>)");
                    out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                    for (CustomerEntity client : liste) 
                    {
                       out.println("<tr>");
                            out.println("<td>");
                                out.println(client.getCustomerId());
                            out.println("</td>");
                            out.println("<td>");
                                out.println(client.getName());
                            out.println("</td>");
                            out.println("<td>");
                                out.println(client.getAddressLine1());
                            out.println("</td>");
                       out.println("</tr>");
                    }
                out.println("</tbody>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
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
