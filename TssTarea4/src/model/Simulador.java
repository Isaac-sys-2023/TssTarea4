package model;

/**
 *
 * @author chichimon
 */

import java.util.*;
public class Simulador {
    private List<Partido> partidos;
    //private Random rand = new Random();
    Random rand = new Random(leerSeedDesdeArchivo());

    public Simulador(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public Map<String, Double> simularVotacion() {
        Map<String, Double> votos = new HashMap<>();
        double suma = 0;

        for (Partido p : partidos) {
            double valor = rand.nextGaussian() * p.getDesviacion() + p.getMedia();
            if (valor < 0) valor = 0;
            votos.put(p.getNombre(), valor);
            suma += valor;
        }

        // Normalizar
        for (String nombre : votos.keySet()) {
            votos.put(nombre, (votos.get(nombre) / suma) * 100);
        }

        return votos;
    }

    public boolean haySegundaVuelta(Map<String, Double> resultados) {
        List<Map.Entry<String, Double>> ordenados = new ArrayList<>(resultados.entrySet());
        ordenados.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        double primero = ordenados.get(0).getValue();
        double segundo = ordenados.get(1).getValue();

        return !(primero > 50 || (primero >= 40 && primero - segundo >= 10));
    }
    
    private long leerSeedDesdeArchivo() {
        try {
            List<String> lineas = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("simulacion_seed.txt"));
            return Long.parseLong(lineas.get(0).trim());
        } catch (Exception e) {
            System.err.println("Error leyendo simulacion_seed.txt. Usando semilla aleatoria.");
            return System.currentTimeMillis(); // fallback si el archivo no existe
        }
    }

}
