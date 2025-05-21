package model;

/**
 *
 * @author chichimon
 */

import java.util.*;
public class DHondt {
    
    public static Map<String, Integer> asignarEscanos(Map<String, Double> votos, int escanos) {
        Map<String, Integer> asignados = new HashMap<>();
        for (String partido : votos.keySet()) {
            asignados.put(partido, 0);
        }

        List<Map.Entry<String, Double>> cocientes = new ArrayList<>();

        for (String partido : votos.keySet()) {
            double voto = votos.get(partido);
            for (int i = 1; i <= escanos; i++) {
                cocientes.add(new AbstractMap.SimpleEntry<>(partido, voto / i));
            }
        }

        cocientes.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        for (int i = 0; i < escanos; i++) {
            String partido = cocientes.get(i).getKey();
            asignados.put(partido, asignados.get(partido) + 1);
        }

        return asignados;
    }
}
