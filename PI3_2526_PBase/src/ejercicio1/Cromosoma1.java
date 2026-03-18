package ejercicio1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {
	public Cromosoma1(String file) {
		Datos1.iniDatos(file);;
	}

	@Override
	public ChromosomeType type() {
		// TODO Cromosoma de tipo binario
		return ChromosomeType.Binary;
	}
	
	@Override
	public Integer size() {
		// TODO Tantos genes como candidatos
		return Datos1.getNumCandidatos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		double valoracionTotal = 0;
		double sueldoTotal = 0;
		Set<String> cualidadesCubiertas = new HashSet<>();
		int incompatibilidades = 0;

		// Recorrido del cromosoma para extraer datos de los candidatos seleccionados
		for (int i = 0; i < value.size(); i++) {
			if (value.get(i) == 1) { // Si el candidato i es seleccionado
				valoracionTotal += Datos1.getValoracion(i);
				sueldoTotal += Datos1.getSueldoMin(i);
				cualidadesCubiertas.addAll(Datos1.getCualidades(i));
				
				// Comprobar incompatibilidades con otros seleccionados
				for (int j = i + 1; j < value.size(); j++) {
					if (value.get(j) == 1 && Datos1.getSonIncompatibles(i, j) > 0) {
						incompatibilidades++;
					}
				}
			}
		}

		// Cálculo de penalizaciones
		double penalizacion = 0;

		// 1. Cobertura de Cualidades: Al menos un candidato seleccionado por cada cualidad j
		// Calculamos cuántas de las 'm' cualidades totales no han sido cubiertas
		int cualidadesFaltantes = Datos1.getNumCualidades() - cualidadesCubiertas.size();
		if (cualidadesFaltantes > 0) {
			penalizacion += Math.pow(cualidadesFaltantes, 2);
		}

		// 2. Restricción de Presupuesto: La suma de sueldos no debe exceder S
		if (sueldoTotal > Datos1.getPresupuestoMax()) {
			penalizacion += Math.pow(sueldoTotal - Datos1.getPresupuestoMax(), 2);
		}

		// 3. Incompatibilidad: Si i y k son incompatibles, no pueden estar ambos (x[i]+x[k] <= 1)
		if (incompatibilidades > 0) {
			penalizacion += Math.pow(incompatibilidades, 2);
		}

		// El objetivo (Goal) es maximizar valoracionTotal restando el peso de las restricciones incumplidas
		return valoracionTotal - 1000 * penalizacion;
	}

	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}

}