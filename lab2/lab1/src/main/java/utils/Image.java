/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author marcel
 */
public class Image {
    
    private int id; 
    private String title;
    private String description;
    private String[] keywords;
    private String author;
    private String creator;
    private String captureDate;
    private String storageDate;
    private String filename;
    
    public Image() {}

    public Image(int id, String title, String description, String[] keywords, String author, String creator, String captureDate, String storageDate, String filename) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.author = author;
        this.creator = creator;
        this.captureDate = captureDate;
        this.storageDate = storageDate;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreator() {
        return creator;
    }

    public String getCaptureDate() {
        return captureDate;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    
    
}
