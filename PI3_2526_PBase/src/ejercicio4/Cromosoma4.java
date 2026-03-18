package ejercicio4; // Asegúrate de que el package sea el correcto

import java.util.List;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.ag.PermutationData;

// Cambiamos BinaryData por PermutationData
public class Cromosoma4 implements PermutationData<Solucion4> {

    public Cromosoma4(String file) {
        Datos4.iniDatos(file);
    }

    @Override
    public ChromosomeType type() {
        // Para rutas y caminos (TSP), el tipo DEBE ser Permutation
        return ChromosomeType.Permutation;
    }
    
    @Override
    public Integer size() {
        // El tamaño de la permutación es el número de vértices (intersecciones)
        return Datos4.N;
    }

    @Override
    public Double fitnessFunction(List<Integer> list) {
        double esfuerzoTotal = 0.0;
        double tiempoTotal = 0.0;
        int monumentosConsecutivos = 0;
        
        // Recorremos la permutación para calcular valores
        for (int i = 0; i < list.size(); i++) {
            int origen = list.get(i);
            // El último conecta con el primero para cerrar el ciclo
            int destino = list.get((i + 1) % list.size());
            
            esfuerzoTotal += Datos4.esfuerzo(origen, destino);
            tiempoTotal += Datos4.tiempo(origen, destino);
            
            if (Datos4.sonMonumentos(origen, destino)) {
                monumentosConsecutivos++;
            }
        }

        // --- GESTIÓN DE RESTRICCIONES (Penalizaciones) ---
        double penalizacion = 0.0;
        
        // 1. Restricción de tiempo (<= maxTime)
        if (tiempoTotal > Datos4.maxTime) {
            penalizacion += Math.pow(tiempoTotal - Datos4.maxTime, 2);
        }
        
        // 2. Al menos 2 monumentos consecutivos
        if (monumentosConsecutivos < 1) {
            penalizacion += 10.0;
        }

        // El objetivo es MINIMIZAR el esfuerzo, por lo tanto el fitness es negativo
        return -(esfuerzoTotal + (10000 * penalizacion));
    }

    @Override
    public Solucion4 solution(List<Integer> value) {
        return Solucion4.create(value);
    }
}