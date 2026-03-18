package ejercicio2;

import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2 implements RangeIntegerData<Solucion2> {
	public Cromosoma2(String file) {
		Datos2.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.RangeInteger;
	}

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return Datos2.getNumProductos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		double beneficioTotal = 0.0;
		double tiempoProd = 0.0;
		double tiempoElab = 0.0;

		// Cálculo de métricas básicas (Beneficio y Tiempos acumulados)
		for (int i = 0; i < value.size(); i++) {
			int unidades = value.get(i);
			if (unidades > 0) {
				// Beneficio total = Beneficio acumulado + unidades(i) * precio(i)
				beneficioTotal += unidades * Datos2.getPrecioProd(i);
				
				// Tiempos totales del producto i (ya calculados en Datos2)
				tiempoProd += unidades * Datos2.getTiempoProdProd(i);
				tiempoElab += unidades * Datos2.getTiempoElabProd(i);
			}
		}

		// Penalización por exceder los límites semanales de la fábrica
		double penalizacion = 0.0;
		
		// A. Tiempo Total de Producción: sum(TiempoProdComp * Unidades) <= A
		if (tiempoProd > Datos2.getTiempoProdTotal()) {
			penalizacion += Math.pow(tiempoProd - Datos2.getTiempoProdTotal(), 2);
		}
		
		// B. Tiempo Total de Elaboración Manual: sum(TiempoElabComp * Unidades) <= B
		if (tiempoElab > Datos2.getTiempoElabTotal()) {
			penalizacion += Math.pow(tiempoElab - Datos2.getTiempoElabTotal(), 2);
		}
		// C. Demanda Máxima de Mercado: x[i] <= getUnidsSemanaProd(i)
		// Nota: Si el rango del cromosoma ya limita esto, la penalización será 0.

		// D. No Negatividad: x[i] >= 0
		// Generalmente controlada por el tipo de cromosoma (Range), pero se puede validar
		
		// goal section: Maximizar beneficio total menos penalizaciones
		return beneficioTotal - 10000.0 * penalizacion;
	}

	@Override
	public Solucion2 solution(List<Integer> value) {
		return Solucion2.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// Límite máximo de ventas semanal para el producto i
		return Datos2.getUnidsSemanaProd(i);
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
}