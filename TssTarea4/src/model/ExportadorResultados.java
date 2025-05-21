package model;

/**
 *
 * @author chichimon
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.io.File;

public class ExportadorResultados {
    public static void exportarCSV(String nombreArchivo, Map<String, Double> porcentajes,
                                   Map<String, Integer> victorias, int totalSimulaciones,
                                   Map<String, Integer> escSenado, Map<String, Integer> escDiputados,
                                   int cuentaSegundaVuelta) {

        try {
            // Verifica o crea la carpeta 'resultados'
            File carpeta = new File("resultados");
            if (!carpeta.exists()) {
                carpeta.mkdirs();  // crea la carpeta si no existe
            }

            FileWriter writer = new FileWriter("resultados/" + nombreArchivo);
            writer.write("Partido;Promedio Voto %;Promedio Escaños Senado;Promedio Escaños Diputados"
                    + ";Prob Victoria Presidencial %;Prob. Segunda Vuelta %\n");
            
            for (String partido : porcentajes.keySet()) {
                double promedioVoto = porcentajes.get(partido) / totalSimulaciones;
                double promedioSenado = escSenado.getOrDefault(partido, 0) / (double) totalSimulaciones;
                double promedioDip = escDiputados.getOrDefault(partido, 0) / (double) totalSimulaciones;
                double porcentajeVictoria = victorias.getOrDefault(partido, 0) * 100.0 / totalSimulaciones;
                double probSegundaVuelta = cuentaSegundaVuelta * 100.0 / totalSimulaciones;

                writer.write(String.format("%s;%.2f;%.2f;%.2f;%.2f;%.2f\n",
                        partido, promedioVoto, promedioSenado, promedioDip, porcentajeVictoria, probSegundaVuelta));
            }
            writer.close();
            System.out.println("✅ Archivo creado: " + nombreArchivo);
            
            // Mostrar en consola (tabla rápida)
            System.out.println("\n--- RESULTADOS: " + nombreArchivo.toUpperCase() + " ---");
            System.out.printf("%-10s %-15s %-20s %-22s %-20s\n", 
                "Partido", "Voto (%)", "Escaños Senado", "Escaños Diputados", "Victoria (%)");

            for (String partido : porcentajes.keySet()) {
                double promedioVoto = porcentajes.get(partido) / totalSimulaciones;
                double promedioSenado = escSenado.getOrDefault(partido, 0) / (double) totalSimulaciones;
                double promedioDip = escDiputados.getOrDefault(partido, 0) / (double) totalSimulaciones;
                double porcentajeVictoria = victorias.getOrDefault(partido, 0) * 100.0 / totalSimulaciones;

                System.out.printf("%-10s %-15.2f %-20.2f %-22.2f %-20.2f\n",
                        partido, promedioVoto, promedioSenado, promedioDip, porcentajeVictoria);
            }

            // Mostrar probabilidad de segunda vuelta (una vez por archivo)
            double probSegundaVuelta = cuentaSegundaVuelta * 100.0 / totalSimulaciones;
            System.out.println("Probabilidad de segunda vuelta: " + String.format("%.2f%%", probSegundaVuelta));
            System.out.println("----------------------------------------------");
        } catch (IOException e) {
            System.err.println("Error al escribir archivo " + nombreArchivo);
        }
    }
}