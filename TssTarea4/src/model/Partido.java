package model;

/**
 *
 * @author chichimon
 */
public class Partido {
    private String nombre;
    private double media;
    private double desviacion;

    public Partido(String nombre, double media, double desviacion) {
        this.nombre = nombre;
        this.media = media;
        this.desviacion = desviacion;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMedia() {
        return media;
    }

    public double getDesviacion() {
        return desviacion;
    }
}
