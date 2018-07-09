package com.teamA.data;

public class Mundial extends Entity {

    private String name;
    private String location;
    private int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Mundial{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", year=" + year +
                "} " + super.toString();
    }
}
