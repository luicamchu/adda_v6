package tests.ple;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import ejercicio3.Datos3;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestsE3 {

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.of("es", "ES"));
		for(Integer i: List.of(1, 2, 3)) { // indique los tests a realizar
			Datos3.iniDatos("datos_entrada/ejercicio3/DatosEntrada"+i+".txt");
			AuxGrammar.generate(Datos3.class,"modelos/Ejercicio3.lsi","lp/Ejercicio3-"+i+".lp");
			
			Optional<GurobiSolution> solucion = GurobiLp.gurobi("lp/Ejercicio3-"+i+".lp");
			if (solucion.isPresent()) {				
				System.out.println(solucion.get().toString((vble, valor)-> valor>0));
			} else {
				System.out.println("\n\n*****Modelo sin solución****");
			}
		}
	}
	
}