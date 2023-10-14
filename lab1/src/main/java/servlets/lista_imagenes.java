/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Max Pasten
 */
@WebServlet(name = "lista_imagenes", urlPatterns = {"/lista_imagenes"})
public class lista_imagenes extends HttpServlet {

    Connection connection = null;
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
        //processRequest(request, response);
        
        response.getWriter().append("Served at: ").append(request.getContextPath());
        
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
        //processRequest(request, response);
        System.out.println("In do post method of Display Image servlet.");
        String imageId=request.getParameter("imageId");
        int id=Integer.parseInt(imageId);

        //getting database connection (jdbc code)
        int imgId=0;
        String imgFileName=null;
        try {
            String query;
            PreparedStatement statement;

            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/imageTutorial","root","your password");
            //Statement stmt;
            //String query="select * from image";
            //stmt=connection.createStatement();
            //ResultSet rs=stmt.executeQuery(query);
            query = "SELECT * FROM PR2.IMAGE ";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();    
            //out.println("Esto pasa en el BACKEND");
            System.out.println("QUERY EJECUTADO");
            while(rs.next()){


                System.out.println("Id image = " + rs.getString("id"));
                System.out.println("id: " + rs.getInt("id"));


                if(rs.getInt("id")==id){
                        System.out.println("Entra en el IF");
                        imgId=rs.getInt("id");
                        imgFileName=rs.getString("filename");
                        
                        System.out.println("Id = " + rs.getString("id"));
                        System.out.println("Titulo = " + rs.getString("filename"));
                        
                }
            }

        }
        catch (Exception e)
        {
                System.out.println(e);
        }

        RequestDispatcher rd;
        request.setAttribute("id",String.valueOf(imgId));  //valueOf
        request.setAttribute("img",imgFileName);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + imgFileName);
        rd=request.getRequestDispatcher("listaImagenes.jsp");
        rd.forward(request, response);
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
