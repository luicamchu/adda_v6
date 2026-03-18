package ejercicio3;

import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3 implements RangeIntegerData<Solucion3> {
	public Cromosoma3(String file) {
		Datos3.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.RangeInteger;
	}

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return Datos3.getNumElementos();
	}

	@Override
    public Double fitnessFunction(List<Integer> value) {
        int n = Datos3.getNumContenedores();
        int m = Datos3.getNumElementos();
        int[] cargaContenedores = new int[n];
        int contenedoresTotalmenteLlenos = 0;
        double penalizacion = 0.0;

        // Procesamiento inicial: Cálculo de cargas por contenedor
        for (int i = 0; i < m; i++) {
            int j = value.get(i);
            
            // Si el gen i tiene un valor menor a n, está asignado al contenedor j
            if (j < n) {
				// 2. Restricción de Compatibilidad: x[i,j] <= getPuedeUbicarse(i,j)
				// Si el método devuelve 0, la asignación es inválida
				if (Datos3.getPuedeUbicarse(i, j) == 0) {
					penalizacion += 1.0; 
				}
				cargaContenedores[j] += Datos3.getTamElemento(i);
			}
			// Nota: La Restricción 1 (Cada elemento en max 1 contenedor) 
			// se cumple por diseño del cromosoma (un gen i solo puede tener un valor j).
        }

        for (int j = 0; j < n; j++) {
        		int capacidad = Datos3.getTamContenedor(j);
            
	         // 3. Límite de Capacidad: Suma de tamaños <= getTamContenedor(j)
	         if (cargaContenedores[j] > capacidad) {
	        	 	penalizacion += Math.pow(cargaContenedores[j] - capacidad, 2);
	         }
	
	         // 4. Condición de Llenado Total (Activa y[j]):
	         // Si la carga es exactamente la capacidad, contamos el contenedor como lleno
	         if (cargaContenedores[j] == capacidad) {
	         	contenedoresTotalmenteLlenos++;
	         }
        }

        // goal section: Maximizar contenedores llenos restando el peso de las restricciones
        return contenedoresTotalmenteLlenos - (1000.00 * penalizacion);
    }

	@Override
	public Solucion3 solution(List<Integer> value) {
		// TODO Auto-generated method stub
		return Solucion3.create(value);
	}

	@Override
	public Integer max(Integer i) {
	    return Datos3.getNumContenedores(); // n = sin asignar, 0 a n-1 = contenedores
	}

	@Override
	public Integer min(Integer i) {
	    return 0;
	}
	
}