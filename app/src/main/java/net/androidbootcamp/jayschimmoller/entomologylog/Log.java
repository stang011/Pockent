package net.androidbootcamp.jayschimmoller.entomologylog;

public class Log {
    private String name;
    private String family;
    private String genus;
    private String species;
    private String method;
    private String latitude, longitude;
    private String date;
    private String notes;

    public Log(String name, String family, String genus, String species, String method, String latitude, String longitude, String date, String notes)
    {
        this.name = name;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.method = method;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.notes = notes;
    }

    public Log(String fromFile) {
        String[] array = fromFile.split(",");
        name = array[0];
        family = array[1];
        genus = array[2];
        species = array[3];
        method = array[4];
        latitude = array[5];
        longitude = array[6];
        date = array[7];
        notes = array[8];
        for(int i = 9; i < array.length; i++)
        {
            notes += "," + array[i];
        }
    }

    public String getName() {
        return name; }
    public String getFamily() {
        return family; }
    public String getGenus() {
        return genus; }
    public String getSpecies() {
        return species; }
    public String getMethod() {
        return method; }
    public String getLatitude() {
        return latitude; }
    public String getLongitude() {
        return longitude; }
    public String getDate() {
        return date; }
    public String getNotes() {
        return notes; }

    // toString returns comma delimited list of log attributes that could be used for file output.
    @Override
    public String toString() {
        String message = "";
        message += name + "," + family + "," + genus + "," + species + "," + method + "," + latitude + "," + longitude + "," + date + "," + notes;
        return message;
    }

    public String displayString() {
        String message = "";
        /*
        message += "Name: " + name + "\n";
        message += "Family: " + family + "\n";
        message += "Genus: " + genus + "\n";
        message += "Species: " + species + "\n";
        message += "Location: " + latitude + " N, " + longitude + " W\n";
        message += "Date: " + date;
         */
        message += name + "\n";
        message += genus + " " + species + "\n";
        message += latitude + " N, " + longitude + " W\n";
        message += date;

        return message;
    }
}