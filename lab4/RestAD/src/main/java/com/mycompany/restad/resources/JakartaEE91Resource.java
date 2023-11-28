package com.mycompany.restad.resources;

import jakarta.activation.MimetypesFileTypeMap;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import utils.Image;
import utils.dbConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.core.HttpHeaders;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import utils.constants;

/**
 *
 * @author Marcel Calleja
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
	
    private final dbConnection db;
    final public static String uploadDir = constants.IMAGESDIR;
    
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
            @FormParam("password") String password) {
        try {
            if (db.isLoginCorrect(username, password) != null) {
                return Response.ok()
                               .build();
            }
            else return Response.status(Response.Status.NOT_FOUND)
                                .build();
        } catch (SQLException | ClassNotFoundException ex) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
        
    }
    
    
    /** 
    * POST method to register a new image 
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
    @Path("registerTest") 
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Produces(MediaType.APPLICATION_JSON) 
    public Response registerImageT (@FormDataParam("title") String title, 
            @FormDataParam("description") String description, 
            @FormDataParam("keywords") String keywords, 
            @FormDataParam("author") String author, 
            @FormDataParam("creator") String creator, 
            @FormDataParam("capture") String capt_date,
            @FormDataParam("filename") String filename,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        
        Integer StatusCode = 201;    
        System.out.println("Registro");
   
        if (!writeImage(filename, fileInputStream)) { //no sha pogut guardar la imatge            
            StatusCode = 500; //fallada server
            String ErrorName = "general";
        }
        
        return Response
            .status(StatusCode)
            .build();
    }
       
    public static Boolean writeImage(String file_name, Part part) 
        throws IOException {
        makeDirIfNotExists();
        
        InputStream content = part.getInputStream();
        
        File targetfile = new File(uploadDir + file_name);
        
        java.nio.file.Files.copy(
                content,
                targetfile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
        
        return true;
    }
    public static Boolean writeImage(String file_name, InputStream fileInputStream)  {
        try{
            makeDirIfNotExists();
            File targetfile = new File(uploadDir + file_name);
        
            java.nio.file.Files.copy(
                    fileInputStream,
                    targetfile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );  
        } catch (IOException e){
            return false;
        }
        return true; 
    }
    
    
    public static Boolean deleteImageT(String file_name) 
    {
        makeDirIfNotExists();
        
        File targetfile = new File(uploadDir + file_name);
        if(! targetfile.delete()) {
            System.out.println("ERROR: Failed to delete " + targetfile.getAbsolutePath());
            return false;
        }
       
        System.out.println("SUCCESS: deleted " + targetfile.getAbsolutePath());
        return true;
    }    
    
        private static void makeDirIfNotExists() {
        File dir = new File(uploadDir);
        // Creamos directorio si no existe.
        if (! dir.exists() ) {
           dir.mkdirs();
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
	 * @param file
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
	    
	    int code;
	    String error;
            
            // Obtener Fecha de Ingreso al sistema (Actual)
            Date todayDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaActual = sdf.format(todayDate);
        
	    try {
                if( ! writeImage(filename, fileInputStream) ){ //no se ha podido guardar la imagen
                    System.out.println("No se guardo la imagen");
                    code = 500; //fallada server
                    error = "general";

                } else { //SUCCESS
                    db.registerImage(title, description, keywords, author, creator, capt_date, fechaActual, filename);
                    db.closeDb();
                    return Response.ok()
                    .build();
                }
	    } catch (SQLException ex) {
                    System.out.println("ERROR: " + ex);
		    code=502;
		    error="sqlException";
	    }
            JsonObject json = Json.createObjectBuilder().add("error",error).build();
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
	 * @param filename
	 * @param oldFilename
    * @return
    return*/
    @Path("modify")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Produces(MediaType.APPLICATION_JSON) 
    public Response modifyImage (@FormDataParam("id") String id,
	    @FormDataParam("title") String title, 
            @FormDataParam("description") String description, 
            @FormDataParam("keywords") String keywords, 
            @FormDataParam("author") String author, 
            @FormDataParam("creator") String creator, 
            @FormDataParam("capture") String capt_date,
            @FormDataParam("filename") String filename,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData)
    {    
        String error;
        int code;
        
        try {
		
		String oldFilename = db.getFilename(Integer.parseInt(id));
		if( ! writeImage(oldFilename, fileInputStream) ){ //no se ha podido guardar la imagen
                    System.out.println("No se guardo la imagen");
                    code = 500; //fallada server
                    error = "general";

                } else { //SUCCESS
		    db.modifyImage(Integer.parseInt(id), title, description, keywords, author, capt_date, filename);
		    // Renombrar archivo
		    File oldfile = new File(constants.IMAGESDIR+oldFilename);
		    File newfile = new File(constants.IMAGESDIR+filename);
		    oldfile.renameTo(newfile);                    
		    db.closeDb();
                    return Response.ok()
                    .build();
                }
        } catch (ClassNotFoundException ex) {
                error="ClassNotFound";
                code=502;
        } catch (SQLException ex) {
		error="SqlException";
		code=502;
	}
        JsonObject json = Json.createObjectBuilder().add("error",error).build();
        return Response.status(code).entity(json).build();
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
	    try {
		int id_n = Integer.parseInt(id);
		String oldFilename = db.getFilename(id_n);
		db.deleteImage(id_n);
		File imagen = new File(constants.IMAGESDIR+oldFilename);
		FileInputStream readImage = new FileInputStream(imagen);
		readImage.close();
		imagen.delete();
		return Response.ok().build();
	    } catch (SQLException ex) {
		return Response.status(Response.Status.BAD_GATEWAY).build();
	    } catch (FileNotFoundException ex) {
		return Response.status(Response.Status.BAD_GATEWAY).build();
	    } catch (IOException ex) {
		return Response.status(Response.Status.BAD_GATEWAY).build();
	    }
    }
    
    /**
    * GET method to list images
    * @return
    */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listImages() {
        
        /*
	    try {
		    List<Image> list = db.listImages();
		    JsonArray json = Json.createArrayBuilder(list).build();

		    return Response.ok().entity(json).build();
		    
	    } catch (SQLException ex) {
		    return Response.status(Response.Status.BAD_GATEWAY).build();
	    }
        */
            
            
        try {
            List<Image> list = db.listImages();

            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            
            // Convertir lista de image en JSON

            for (Image image : list) {
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", image.getId())
                        .add("title", image.getTitle())
                        .add("description", image.getDescription())
                        .add("keywords", convertKeywordsToJsonArray(image.getKeywords()))
                        .add("author", image.getAuthor())
                        .add("creator", image.getCreator())
                        .add("captureDate", image.getCaptureDate())
                        .add("storageDate", image.getStorageDate())
                        .add("filename", image.getFilename())
                        .build());
            }

            JsonArray json = jsonArrayBuilder.build();
            System.out.println("ENVIA LISTA");
            return Response.ok().entity(json).build();

        } catch (SQLException ex) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
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
	    try {
		    List<Image> list = db.searchImageById(id);
		    JsonArray json = Json.createArrayBuilder(list).build();
		    
		    return Response.ok().entity(json).build();
		    
	    } catch (SQLException ex) {
		    return Response.status(Response.Status.BAD_GATEWAY).build();
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
	    try {
		    List<Image> list = db.searchImageByTitle(title);
		    JsonArray json = Json.createArrayBuilder(list).build();
		    
		    return Response.ok().entity(json).build();
		    
	    } catch (SQLException ex) {
		    return Response.status(Response.Status.BAD_GATEWAY).build();
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
	    try {
		    List<Image> list = db.searchImageDate(date, date);
		    JsonArray json = Json.createArrayBuilder(list).build();
		    
		    return Response.ok().entity(json).build();
		    
	    } catch (SQLException ex) {
		    return Response.status(Response.Status.BAD_GATEWAY).build();
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
	    try {
		    List<Image> list = db.searchImageByAuthor(author);
		    JsonArray json = Json.createArrayBuilder(list).build();
		    
		    return Response.ok().entity(json).build();
		    
	    } catch (SQLException ex) {
		    return Response.status(Response.Status.BAD_GATEWAY).build();
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
	    try {
		    List<Image> list = db.searchImageByKeywords(keywords);
		    JsonArray json = Json.createArrayBuilder(list).build();
		    
		    return Response.ok().entity(json).build();
		    
	    } catch (SQLException ex) {
		    return Response.status(Response.Status.BAD_GATEWAY).build();
	    }
    }
    
    
    /**
    * POST method to register a new image – File is not uploaded
    * @param date_ini
    * @param date_end
    * @param keywords
    * @param author
    * @return
    */
    @Path("searchCombined")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCombined (@FormParam("date_ini") String date_ini, 
            @FormParam("date_fin") String date_end, 
            @FormParam("keywords") String keywords, 
            @FormParam("author") String author){
            
            System.out.println("title=" + date_ini);
            System.out.println("description=" +date_end);
            System.out.println("keywords=" +keywords);
            System.out.println("author=" +author);
            
            
            List<Image> images = new ArrayList<>();
            try {
                
                if (author.length() == 0 && keywords.length() == 0){
                    //System.out.println("Date");
                    //System.out.println("Autor: "+author);
                    //System.out.println("Keywords: "+keywords);
                    images = db.searchImageDate(date_ini, date_end);
                } else if (author.length() != 0 && keywords.length() != 0){
                    //System.out.println("DateAuthorKeywords");
                    //System.out.println("Autor: "+author);
                    //System.out.println("Keywords: "+keywords);
                    images = db.searchImageDateAuthorKeywords(date_ini, date_end, author, keywords);
                } else if (keywords.length() != 0 && author.length() == 0){
                    //System.out.println("Datekeyword");
                    //System.out.println("Autor: "+author);
                    //System.out.println("Keywords: "+keywords);
                    images = db.searchImageDateKeywords(date_ini, date_end, keywords);
                } else if (keywords.length() == 0&& author.length() != 0){
                    //System.out.println("DateAutor");
                    //System.out.println("Autor: "+author);
                    //System.out.println("Keywords: "+keywords);
                    images = db.searchImageDateAuthor(date_ini, date_end, author);
                }
                

                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

                for (Image image : images) {
                    jsonArrayBuilder.add(Json.createObjectBuilder()
                            .add("id", image.getId())
                            .add("title", image.getTitle())
                            .add("description", image.getDescription())
                            .add("keywords", convertKeywordsToJsonArray(image.getKeywords()))
                            .add("author", image.getAuthor())
                            .add("creator", image.getCreator())
                            .add("captureDate", image.getCaptureDate())
                            .add("storageDate", image.getStorageDate())
                            .add("filename", image.getFilename())
                            .build());
                }

                JsonArray json = jsonArrayBuilder.build();
                System.out.println("ENVIA BUSQUEDA");
                return Response.ok().entity(json).build();

            } catch (SQLException ex) {
                return Response.status(Response.Status.BAD_GATEWAY).build();
            }
            
            
            
            
    }
    
    /**
    * GET method to list images
    * @return
    */
    @Path("imageRecent")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response imageRecent() {
        
        try {
            List<Image> list = db.recentImage();

            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            
            // Convertir lista de image en JSON

            for (Image image : list) {
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", image.getId())
                        .add("title", image.getTitle())
                        .add("description", image.getDescription())
                        .add("keywords", convertKeywordsToJsonArray(image.getKeywords()))
                        .add("author", image.getAuthor())
                        .add("creator", image.getCreator())
                        .add("captureDate", image.getCaptureDate())
                        .add("storageDate", image.getStorageDate())
                        .add("filename", image.getFilename())
                        .build());
            }

            JsonArray json = jsonArrayBuilder.build();
            System.out.println("ENVIA LISTA");
            return Response.ok().entity(json).build();

        } catch (SQLException ex) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
            
    }
    
    // Metodo para convertur la lista de keywords a array JSON
    private JsonArray convertKeywordsToJsonArray(String[] keywords) {
        JsonArrayBuilder keywordsArrayBuilder = Json.createArrayBuilder();
        for (String keyword : keywords) {
            keywordsArrayBuilder.add(keyword);
        }
        return keywordsArrayBuilder.build();
    }
    

    @Path("getImage/{filename}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImage(@PathParam("filename") String filename) {
	    try {
		    File file = new File(constants.IMAGESDIR+filename);
		    if (!file.exists()) return Response.status(404).build();
		    String mime = new MimetypesFileTypeMap().getContentType(file);
		    return Response.ok(file,mime).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename).build();
	    }
	    catch (Exception e) {
		    return Response.ok("Error").build();
	    }
    }
    
}
