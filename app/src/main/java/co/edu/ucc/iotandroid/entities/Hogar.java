package co.edu.ucc.iotandroid.entities;

/**
 * Created by jggomez on 05-Sep-17.
 */

public class Hogar {

    private int sala;
    private int bano;
    private int cocina;
    private int habitacion;

    public Hogar() {

    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public int getBano() {
        return bano;
    }

    public void setBano(int bano) {
        this.bano = bano;
    }

    public int getCocina() {
        return cocina;
    }

    public void setCocina(int cocina) {
        this.cocina = cocina;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }
}
