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
        
        // Recorrido de la permutación para calcular las métricas del ciclo
        for (int i = 0; i < list.size(); i++) {
            int origen = list.get(i);
            // El último conecta con el primero para cerrar el ciclo
            int destino = list.get((i + 1) % list.size());
            
            esfuerzoTotal += Datos4.esfuerzo(origen, destino);
            tiempoTotal += Datos4.tiempo(origen, destino);
            
            // Verificamos si el arco (i, j) une dos monumentos
            if (Datos4.sonMonumentos(origen, destino)) {
                monumentosConsecutivos++;
            }
        }

        double penalizacion = 0.0;
        
        // 1. Camino que pase por todas las intersecciones exactamente una vez y vuelva al origen
        // Nota: Esta restricción se cumple POR CONSTRUCCIÓN al usar ChromosomeType.Permutation.
        // No requiere penalización adicional en el fitness.
        
        // 2. La duración total del trayecto sea menor o igual que maxTime
        if (tiempoTotal > Datos4.maxTime) {
            penalizacion += Math.pow(tiempoTotal - Datos4.maxTime, 2);
        }
        
        // 3. Al menos 2 intersecciones consecutivas que alberguen un monumento
        // Según tu modelo LSI: sum(x[i, j] | sonMonumentos) >= 1
        if (monumentosConsecutivos < 1) {
            penalizacion += 100.0; // Penalización constante o proporcional según prefieras
        }

        // goal section: MINIMIZAR el esfuerzo total
        // En AG de la librería lsi, para minimizar devolvemos el valor en negativo
        return -(esfuerzoTotal + 10000 * penalizacion);
    }

    @Override
    public Solucion4 solution(List<Integer> value) {
        return Solucion4.create(value);
    }
    
}