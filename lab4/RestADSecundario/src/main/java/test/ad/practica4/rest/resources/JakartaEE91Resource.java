package test.ad.practica4.rest.resources;

import database.UsuarisInterface;
import database.ImatgeInterface;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.PathParam;

import java.time.LocalDate;

//Practica 4
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    final public static String uploadDir = "/var/webapp/testGlassfish/upload/";
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    

    /**
    * OPERACIONES DEL SERVICIO REST
    */
    /**
    * POST method to login in the application
    * @param username
    * @param password
    * @return
    */
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        
        UsuarisInterface db = new UsuarisInterface();
        db.UsuarisConnect();
        if(db.existeixUsuari(username, password)) {
            db.UsuarisDisconnect();
            JsonObject object = new JsonObject();
            object.addProperty("estat", Boolean.TRUE);
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();
        }
        else {
            db.UsuarisDisconnect();
            JsonObject object = new JsonObject();
            object.addProperty("estat", Boolean.FALSE);
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();
        }
    }

    /**
    * POST method to register in the application
    * @param username
    * @param password
    * @param password2
    * @return
    */
    @Path("registrar")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar (@FormParam("username") String username, @FormParam("password") String password, @FormParam("password2") String password2) {
        if(password.equals(password2)) {
            UsuarisInterface db = new UsuarisInterface();
            db.UsuarisConnect();
            if(db.nouUsuari(username, password)) {
                db.UsuarisDisconnect();
                JsonObject object = new JsonObject();
                object.addProperty("estat", Boolean.TRUE);
                return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();
            }
            else {
                db.UsuarisDisconnect();
                JsonObject object = new JsonObject();
                object.addProperty("estat", Boolean.FALSE);
                return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();
            }
        }
        else {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
    }
    
    // 2 new params:  @param fileInputStream  and @param fileMetaData
    
    /**
    * POST method to register and upload a new image 
    * @param title
    * @param description
    * @param keywords
    * @param author
    * @param creator
    * @param capt_date
    * @param filename
    * @param fileInputStream
    * @param fileMetaData
    * @return
    */
    @Path("register")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerImage (@FormDataParam("title") String title, 
        @FormDataParam("description") String description, 
        @FormDataParam("keywords") String keywords, 
        @FormDataParam("author") String author, 
        @FormDataParam("creator") String creator, 
        @FormDataParam("capture") String capt_date,
        @FormDataParam("filename") String filename,
        @FormDataParam("file") InputStream fileInputStream,
        @FormDataParam("file") FormDataContentDisposition fileMetaData) {
             
        LocalDate datapujada = LocalDate.now();
        String datapujadaString = datapujada.toString();
        
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        boolean b = db.novaImatge(title, description, keywords, author, creator, capt_date, datapujadaString, filename);
        db.ImatgeDisconnect();
        if(b) {
            JsonObject object = new JsonObject();
            object.addProperty("estat", Boolean.TRUE);
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            object.addProperty("estat", Boolean.FALSE);
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();
        }
    }
   
public static Boolean writeImage(String file_name, Part part) throws IOException {
        //makeDirIfNotExists();
        InputStream content = part.getInputStream();
        File targetfile = new File(uploadDir + file_name);
        java.nio.file.Files.copy(content,targetfile.toPath(),StandardCopyOption.REPLACE_EXISTING);
        return true;
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
    public Response modifyImage (@FormParam("id") String id, @FormParam("title") String title, @FormParam("description") String description, @FormParam("keywords") String keywords, @FormParam("author") String author, @FormParam("creator") String creator, @FormParam("capt_date") String capt_date) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarID(Integer.parseInt(id));
        if(jsonString == null) {
            db.ImatgeDisconnect();
            JsonObject o = new JsonObject();
            o.addProperty("estat", Boolean.FALSE);
            return Response.ok(o.toString(), MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = JsonParser.parseString(jsonString).getAsJsonObject(); 
            String ids = object.get("ID").getAsString();
            int idx = Integer.parseInt(ids);
            if(idx!=Integer.parseInt(id)) {
                db.ImatgeDisconnect();
                JsonObject o = new JsonObject();
                o.addProperty("estat", Boolean.FALSE);
                return Response.ok(o.toString(), MediaType.APPLICATION_JSON).build();
            }
            else {
                int n = db.updateImage(idx, title, description, keywords, author, creator, capt_date);
                db.ImatgeDisconnect();
                if (n==0) {
                    JsonObject o = new JsonObject();
                    o.addProperty("estat", Boolean.FALSE);
                    return Response.ok(o.toString(), MediaType.APPLICATION_JSON).build();
                }
                else {
                    JsonObject o = new JsonObject();
                    o.addProperty("estat", Boolean.TRUE);
                    return Response.ok(o.toString(), MediaType.APPLICATION_JSON).build();
                }
            }
        }
    }
        

    /**
    * POST method to delete an existing image
    * @param id
    * @return
    */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage (@FormParam("id") String id) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarID(Integer.parseInt(id));
        if(jsonString == null) {
            db.ImatgeDisconnect();
            JsonObject o = new JsonObject();
            o.addProperty("estat", Boolean.FALSE);
            return Response.ok(o.toString(), MediaType.APPLICATION_JSON).build();
        }
        else {
            db.esborrarImage(Integer.parseInt(id));
            db.ImatgeDisconnect();
            JsonObject o = new JsonObject();
            o.addProperty("estat", Boolean.TRUE);
            return Response.ok(o.toString(), MediaType.APPLICATION_JSON).build();
        }
    }
    
    /**
    * GET method to list images
    * @return
    */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listImages () {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.llistarImatges();
        db.ImatgeDisconnect();
        if(jsonString != null) {
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();            
        }
    }
    
    /**
    * GET method to search images by id
    * @param id
    * @return
    */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID (@PathParam("id") int id) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarID(id);
        db.ImatgeDisconnect();
        if(jsonString != null) {
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();            
        }
    }
    
    /**
    * GET method to search images by title
    * @param title
    * @return
    */
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByTitle (@PathParam("title") String title) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarTitol(title);
        db.ImatgeDisconnect();
        if(jsonString != null) {
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();            
        }
    }
    
    /**
    * GET method to search images by creation date. Date format should be
    * yyyy-mm-dd
    * @param date
    * @return
    */
    @Path("searchCreationDate/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByCreationDate (@PathParam("date") String date) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarCreationDate(date);
        db.ImatgeDisconnect();
        if(jsonString != null) {
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();            
        }
    }

    /**
    * GET method to search images by author
    * @param author
    * @return
    */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByAuthor (@PathParam("author") String author) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarAutor(author);
        db.ImatgeDisconnect();
        if(jsonString != null) {
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();            
        }
    }
    
    /**
    * GET method to search images by keyword
    * @param keywords
    * @return
    */
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByKeywords (@PathParam("keywords") String keywords) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarKeywords(keywords);
        db.ImatgeDisconnect();
        if(jsonString != null) {
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            object.addProperty("SQLEXCEPTION", Boolean.FALSE);
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();            
        }
    }
    
    /**
    * GET method to search images by different params
    * @param paraula
    * @return
    */
    @Path("searchImage/{paraula}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchImage (@PathParam("paraula") String paraula) {
        ImatgeInterface db = new ImatgeInterface();
        db.ImatgeConnect();
        String jsonString = db.buscarImatge(paraula);
        db.ImatgeDisconnect();
        if(jsonString != null) {
            return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject object = new JsonObject();
            object.addProperty("SQLEXCEPTION", Boolean.FALSE);
            return Response.ok(object.toString(), MediaType.APPLICATION_JSON).build();            
        }
    }
    
}
