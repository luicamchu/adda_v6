package ejercicio4;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.List2;

public class Solucion4 {

    public static Solucion4 create(List<Integer> ls) {
        return new Solucion4(ls);
    }

    private String camino, txt;
    private Double totalTime, totalEffort;
    private Double avgTime, avgEffort, monCons;

    private Solucion4(List<Integer> ls) {
    	txt = "";
         List<Integer> aux = List2.addLast(ls, ls.getFirst());
        
    	camino = aux.stream().map(i -> Datos4.getVertex(i)+"")
        	.collect(Collectors.joining(" ->\n\t", "Recorrido:\n\t", ""));
        	
    	// Inicializamos los acumuladores en 0.0 (¡Importante para evitar el NullPointerException!)
        this.totalTime = 0.0;
        this.totalEffort = 0.0;
        int monumentosSeguidos = 0;

        // Calculamos los totales recorriendo la lista de la solución
        for (int k = 0; k < aux.size() - 1; k++) {
            int origen = aux.get(k);
            int destino = aux.get(k + 1);
            
            this.totalTime += Datos4.tiempo(origen, destino);
            this.totalEffort += Datos4.esfuerzo(origen, destino);
            
            if (Datos4.sonMonumentos(origen, destino)) {
                monumentosSeguidos++;
            }
        }

        // Ahora que totalTime y totalEffort tienen valor, calculamos las medias
        this.avgTime = totalTime / Datos4.N;
        this.avgEffort = totalEffort / Datos4.N;
        this.monCons = (double) monumentosSeguidos;
     }

	@Override
    public String toString() {
    	String s1 = String.format("\nTiempos (total/medio/maximo): %.1f / %.1f / %.1f", totalTime, avgTime, Datos4.maxTime);
    	String s2 = String.format("\nEsfuerzos (total/medio): %.1f / %.1f", totalEffort, avgEffort);
    	String s3 = String.format("\nNº de lugares con monumento consecutivos a otro con monumento: %d", monCons.intValue());
        return txt+camino+s1+s2+s3;
    }

}
