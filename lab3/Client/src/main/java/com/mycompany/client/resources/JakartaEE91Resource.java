package com.mycompany.client.resources;

import jakarta.mail.Part;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import utils.constants;
import utils.dbConnection;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.mail.MessagingException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    private final dbConnection db;
    
    public JakartaEE91Resource() throws ClassNotFoundException, SQLException {
        db = new dbConnection();
    }
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, 
            @FormParam("password") String password) throws SQLException, ClassNotFoundException {
        try {
            if (db.isLoginCorrect(username, password) != null) {
                return Response.ok()
                               .build();
            }
            else return Response.status(Response.Status.NOT_FOUND)
                                .build();
        } catch (SQLException | ClassNotFoundException ex) {
            return Response.status()
        }
        
    }
    
    /**
    * POST method to register a new image – File is not uploaded
    * @param title
    * @param description
    * @param keywords
    * @param author
    * @param creator
    * @param capt_date
    * @param filename
    * @param filePart
    * @return
    */
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerImage (@FormParam("title") String title, 
            @FormParam("description") String description, 
            @FormParam("keywords") String keywords, 
            @FormParam("author") String author, 
            @FormParam("creator") String creator, 
            @FormParam("capture") String capt_date, 
            @FormParam("filename") String filename, 
            @FormParam("filePart") Part filePart) {
        
            String error;
            int code;
            
            try {
                String format = filePart.getContentType();
                if(!format.equals("image/png") && !format.equals("image/jpg") && !format.equals("image/jpeg")) {
                    error="invalidFileFormat";
                    code=406;
                }
                else {
                    // Nombre de Archivo
                    String imageFileName= filePart.getFileName();
                    String uploadPath=constants.IMAGESDIR+imageFileName;
                    try{
                        FileOutputStream fos=new FileOutputStream(uploadPath);
                        InputStream is=filePart.getInputStream();

                        byte[] data=new byte[is.available()];
                        is.read(data);
                        fos.write(data);
                        fos.close();
                        db.registerImage(title, description, keywords, author, creator, capt_date, capt_date, filename);                          
                        db.closeDb();
                        
                        return Response.status(201)
                                       .build();

                    } catch (SQLException ex) {
                        error="sqlError";
                        code=502;
                    } catch (MessagingException ex) {
                        error="fileStreamError";
                        code=502;
                    } catch (IOException ex) {
                        error="ioFileError";
                        code=502;
                    }
               }
            } catch (MessagingException ex) {
                error="fileStreamError";
                code=502;
            }
            JsonObject json = Json.createObjectBuilder().add("errpr",error).build();
            return Response.status(code).entity(json).build();
    }
    
    /**
    * POST method to modify an existing image
    * @param id
    * @param title
    * @param description
    * @param keywords
    * @param author
    * @param creator, used for checking image ownership
    * @param capt_date
    * @return
    */
    @Path("modify")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyImage (@FormParam("id") String id,
    @FormParam("title") String title,
    @FormParam("description") String description,
    @FormParam("keywords") String keywords,
    @FormParam("author") String author,
    @FormParam("creator") String creator,
    @FormParam("capture") String capt_date) {
    }
}
