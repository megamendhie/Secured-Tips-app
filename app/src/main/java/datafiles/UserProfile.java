package datafiles;

public class UserProfile {
    private String a1_firstname;
    private String a2_lastname;
    private String a3_username;
    private String a7_imageURL;
    private long a8_points;

    public UserProfile(){}

    public String getA3_username() {
        return a3_username;
    }

    public void setA3_username(String a3_username) {
        this.a3_username = a3_username;
    }

    public String getA7_imageURL() {
        return a7_imageURL;
    }

    public void setA7_imageURL(String a7_imageURL) {
        this.a7_imageURL = a7_imageURL;
    }

    public long getA8_points() {
        return a8_points;
    }

    public void setA8_points(long a8_points) {
        this.a8_points = a8_points;
    }

    public String getA1_firstname() {
        return a1_firstname;
    }

    public void setA1_firstname(String a1_firstname) {
        this.a1_firstname = a1_firstname;
    }

    public String getA2_lastname() {
        return a2_lastname;
    }

    public void setA2_lastname(String a2_lastname) {
        this.a2_lastname = a2_lastname;
    }
}
