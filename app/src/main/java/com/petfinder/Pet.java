package com.petfinder;

public class Pet {
    private int id;
    private String species;
    private String breed;
    private String size;
    private String color;
    private String is_urgent;
    private String location;
    private String found_date;
    private String post_date;
    private String title;
    private String description;
    private char has_permanent_home;

    public Pet(int id, String species, String breed, String size, String color, String is_urgent,
               String location, String found_date, String post_date, String title, String description, char has_permanent_home) {
        this.id = id;
        this.species = species;
        this.breed = breed;
        this.size = size;
        this.color = color;
        this.is_urgent = is_urgent;
        this.location = location;
        this.found_date = found_date;
        this.post_date = post_date;
        this.title = title;
        this.description = description;
        this.has_permanent_home = has_permanent_home;
    }

    //getters
    public int getId() {return this.id;}
    public String getSpecies() {return this.species;}
    public String getBreed() {return this.breed;}
    public String getSize() {return this.size;}
    public String getColor() {return this.color;}
    public String getIsUrgent() {return this.is_urgent;}
    public String getLocation() {return this.location;}
    public String getFoundDate() {return this.found_date;}
    public String getPostDate() {return this.post_date;}
    public String getTitle() {return this.title;}
    public String getDescription() {return this.description;}
    public char getHasPermanentHome() {return this.has_permanent_home;}

    //setters
    public void setId(int id) { this.id = id;}
    public void setSpecies(String species) { this.species = species;}
    public void setBreed(String breed) { this.breed = species;}
    public void setSize(String size) { this.size = size;}
    public void setColor(String color) { this.color = color;}
    public void setIsUrgent(String is_urgent) { this.is_urgent = is_urgent;}
    public void setLocation(String location) { this.location = location;}
    public void setFoundDate(String found_date) { this.found_date = found_date;}
    public void setPostDate(String post_date) { this.post_date = post_date;}
    public void setTitle(String title) { this.title = title;}
    public void setDescription(String description) { this.found_date = description;}
    public void setHasPermanentHome(char has_permanent_home) {this.has_permanent_home = has_permanent_home;}

}
