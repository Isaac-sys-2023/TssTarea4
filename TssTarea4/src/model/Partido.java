package model;

/**
 *
 * @author chichimon
 */

/*public class Partido {
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
}*/

import java.io.File;

public class Partido {
    private String nombre;
    private double media;
    private double desviacion;
    private File fotoCandidato;
    private File logoPartido;
    private String colorHex;

    public Partido(String nombre, double media, double desviacion, File fotoCandidato, File logoPartido, String colorHex) {
        this.nombre = nombre;
        this.media = media;
        this.desviacion = desviacion;
        this.fotoCandidato = fotoCandidato;
        this.logoPartido = logoPartido;
        this.colorHex = colorHex;
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

    public File getFotoCandidato() {
        return fotoCandidato;
    }

    public File getLogoPartido() {
        return logoPartido;
    }

    public String getColorHex() {
        return colorHex;
    }

    // Para usar el color como Color de Java:
    public java.awt.Color getColorAWT() {
        return java.awt.Color.decode(colorHex);
    }
}
