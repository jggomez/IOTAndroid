package co.edu.ucc.iotandroid.entidades;

/**
 * Created by jggomez on 29-Aug-17.
 */

public class Usuario {

    private String nombres;
    private String token;

    public Usuario(){

    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
