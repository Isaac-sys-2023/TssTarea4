package model;

/**
 *
 * @author chichimon
 */

import java.util.*;
public class Main {
    public static void main(String[] args) {
        List<String> departamentos = Arrays.asList(
            "nacional", "la_paz", "cochabamba", "santa_cruz", "chuquisaca",
            "tarija", "potosi", "beni", "pando", "oruro"
        );

        List<Partido> partidos = Arrays.asList(
            new Partido("MAS", 40, 5),
            new Partido("CC", 30, 5),
            new Partido("CREEMOS", 20, 3),
            new Partido("FPV", 5, 2),
            new Partido("Otros", 5, 1)
        );

        int repeticiones = 1000;

        for (String dpto : departamentos) {
            Simulador simulador = new Simulador(partidos);

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

                Map<String, Integer> escSenado = DHondt.asignarEscanos(votos, 4);
                for (String p : escSenado.keySet()) {
                    totalEscSenado.put(p, totalEscSenado.getOrDefault(p, 0) + escSenado.get(p));
                }

                Map<String, Integer> escDip = DHondt.asignarEscanos(votos, 10);
                for (String p : escDip.keySet()) {
                    totalEscDip.put(p, totalEscDip.getOrDefault(p, 0) + escDip.get(p));
                }
            }

            ExportadorResultados.exportarCSV(
                dpto + ".csv",
                sumaPorcentajes,
                ganaPresidencia,
                repeticiones,
                totalEscSenado,
                totalEscDip,
                segundaVueltaCount
            );
        }
    }
}

