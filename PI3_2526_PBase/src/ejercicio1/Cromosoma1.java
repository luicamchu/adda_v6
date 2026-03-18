package ejercicio1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {
	public Cromosoma1(String file) {
		//TODO 
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
		int n = value.size();

		// 1. Recorrido del cromosoma para calcular métricas
		for (int i = 0; i < n; i++) {
			if (value.get(i) == 1) { // Si el candidato i es seleccionado
				valoracionTotal += Datos1.getValoracion(i);
				sueldoTotal += Datos1.getSueldoMin(i);
				cualidadesCubiertas.addAll(Datos1.getCualidades(i));
				
				// Comprobar incompatibilidades con otros seleccionados
				for (int j = i + 1; j < n; j++) {
					if (value.get(j) == 1 && Datos1.getSonIncompatibles(i, j)>0) {
						incompatibilidades++;
					}
				}
			}
		}

		// 2. Cálculo de penalizaciones
		double penalizacion = 0;
		// Restricción: Presupuesto máximo
		if (sueldoTotal > Datos1.getPresupuestoMax()) {
			penalizacion += Math.pow(sueldoTotal - Datos1.getPresupuestoMax(), 2);
		}
		// Restricción: Cubrir todas las cualidades deseadas
		int cualidadesFaltantes = Datos1.getNumCualidades() - cualidadesCubiertas.size();
		if (cualidadesFaltantes > 0) {
			penalizacion += Math.pow(cualidadesFaltantes, 2);
		}
		// Restricción: Incompatibilidades
		if (incompatibilidades > 0) {
			penalizacion += Math.pow(incompatibilidades, 2);
		}

		// El objetivo es maximizar valoración total reduciendo penalizaciones
		return valoracionTotal - 1000 * (penalizacion);
	}

	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}

}