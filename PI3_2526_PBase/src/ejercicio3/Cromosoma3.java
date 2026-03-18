package ejercicio3;

import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3 implements RangeIntegerData<Solucion3> {
	public Cromosoma3(String file) {
		//TODO 
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

        for (int i = 0; i < m; i++) {
            int j = value.get(i);
            if (j < n) { // Si el elemento i está asignado al contenedor j
                // Restricción 1: ¿Puede el elemento i ir en el contenedor j por tipo?
                if (Datos3.getPuedeUbicarse(i, j) > 0) {
                    cargaContenedores[j] += Datos3.getTamElemento(i);
                } else {
                    penalizacion += 100.0; // Penalizar si el tipo no coincide
                }
            }
        }

        for (int j = 0; j < n; j++) {
            int capacidad = Datos3.getTamContenedor(j);
            if (cargaContenedores[j] == capacidad) {
                contenedoresTotalmenteLlenos++;
            } else if (cargaContenedores[j] > capacidad) {
                // Penalizar exceso de capacidad
                penalizacion += Math.pow(cargaContenedores[j] - capacidad, 2);
            }
        }

        // Maximizar contenedores llenos restando las penalizaciones por incumplir restricciones
        return contenedoresTotalmenteLlenos - (1000.00 * penalizacion);
    }

	@Override
	public Solucion3 solution(List<Integer> value) {
		// TODO Auto-generated method stub
		return Solucion3.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return Datos3.getNumElementos();
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}