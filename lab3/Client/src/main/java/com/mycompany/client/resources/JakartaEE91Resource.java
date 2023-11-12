package com.mycompany.client.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import utils.dbConnection;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonArray;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Image;


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
            return Response.status(Response.Status.BAD_GATEWAY).build();
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
            @FormParam("filename") String filename){
	    
	    int code;
	    String error;
        
	    try {
			db.registerImage(title, description, keywords, author, creator, capt_date, capt_date, filename);
			db.closeDb();

			return Response.ok()
				.build();
	    } catch (SQLException ex) {
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
    * @return
    return*/
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
    @FormParam("capture") String capt_date,
    @FormParam("filename") String filename)
    {    
	    String error;
	    int code;
	    
	    try {
		    //if (!db.isOwner(id, creator)) {
		    //    code=403;
		    //    error="invalidOwner";
		    //}
		    //else {
			    db.modifyImage(Integer.parseInt(id), title, description, keywords, author, capt_date, filename);
			    return Response.ok().build();
		    //}
	    } catch (ClassNotFoundException | SQLException ex) {
		    error="";
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
		db.deleteImage(Integer.parseInt(id));
		return Response.ok().build();
	    } catch (SQLException ex) {
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
	    try {
		    List<Image> list = db.listImages();
		    JsonArray json = Json.createArrayBuilder(list).build();

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
 
 }