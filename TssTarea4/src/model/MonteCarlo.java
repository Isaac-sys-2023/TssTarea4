package model;

/**
 *
 * @author chichimon
 */

import java.util.*;

public class MonteCarlo {
    private Simulador simulador;
    private int repeticiones;

    public MonteCarlo(Simulador simulador, int repeticiones) {
        this.simulador = simulador;
        this.repeticiones = repeticiones;
    }

    public void ejecutar() {
        Map<String, Double> sumaPorcentajes = new HashMap<>();
        Map<String, Integer> ganaPresidencia = new HashMap<>();
        int segundaVueltaCount = 0;

        Map<String, Integer> totalEscSenado = new HashMap<>();
        Map<String, Integer> totalEscDip = new HashMap<>();

        for (int i = 0; i < repeticiones; i++) {
            Map<String, Double> votos = simulador.simularVotacion();
            List<Map.Entry<String, Double>> orden = new ArrayList<>(votos.entrySet());
            orden.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            String ganador = orden.get(0).getKey();

            ganaPresidencia.put(ganador, ganaPresidencia.getOrDefault(ganador, 0) + 1);

            for (String partido : votos.keySet()) {
                sumaPorcentajes.put(partido, sumaPorcentajes.getOrDefault(partido, 0.0) + votos.get(partido));
            }

            if (simulador.haySegundaVuelta(votos)) {
                segundaVueltaCount++;
            }

            // Asignación de escaños
            Map<String, Integer> escSenado = DHondt.asignarEscanos(votos, 4);
            for (String p : escSenado.keySet()) {
                totalEscSenado.put(p, totalEscSenado.getOrDefault(p, 0) + escSenado.get(p));
            }

            Map<String, Integer> escDip = DHondt.asignarEscanos(votos, 10);
            for (String p : escDip.keySet()) {
                totalEscDip.put(p, totalEscDip.getOrDefault(p, 0) + escDip.get(p));
            }
        }

        System.out.println("\n===== RESULTADOS DE " + repeticiones + " SIMULACIONES =====\n");

        System.out.println("Probabilidad de segunda vuelta: " + ((segundaVueltaCount * 100.0) / repeticiones) + "%\n");

        System.out.println("--- Promedio de porcentaje de votos ---");
        for (String p : sumaPorcentajes.keySet()) {
            double promedio = sumaPorcentajes.get(p) / repeticiones;
            System.out.printf("%s: %.2f%%\n", p, promedio);
        }

        System.out.println("\n--- Frecuencia de victoria presidencial ---");
        for (String p : ganaPresidencia.keySet()) {
            double porcentaje = ganaPresidencia.get(p) * 100.0 / repeticiones;
            System.out.printf("%s: %.2f%%\n", p, porcentaje);
        }

        System.out.println("\n--- Promedio de escaños en Senado ---");
        for (String p : totalEscSenado.keySet()) {
            double promedio = totalEscSenado.get(p) / (double) repeticiones;
            System.out.printf("%s: %.2f\n", p, promedio);
        }

        System.out.println("\n--- Promedio de escaños en Diputados ---");
        for (String p : totalEscDip.keySet()) {
            double promedio = totalEscDip.get(p) / (double) repeticiones;
            System.out.printf("%s: %.2f\n", p, promedio);
        }
    }
}

