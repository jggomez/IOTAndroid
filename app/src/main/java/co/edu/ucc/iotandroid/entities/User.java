package co.edu.ucc.iotandroid.entities;

/**
 * Created by jggomez on 29-Aug-17.
 */

public class User {

    private String names;
    private String token;

    public User(){

    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
