package pe.christianmtr.cambiomonedas_web.cambiador;

import java.io.File;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

/**
* En este ejemplo se muestra como resolver un problema clasico de algoritmos
* genéticos utilizando el framework JGAP. El problema consiste en lograr juntar
* el monto de dinero ingresado a la aplicacion por parametro con la menor
* cantidad de monedas posibles.
*/

public class CambioMinimo {
/**
	* The total number of times we'll let the population evolve.
	*/
	private static final int MAX_EVOLUCIONES_PERMITIDAS = 2200;
        public static long tiempoTotal = 0;

	/**
	* Calcula utilizando algoritmos geneticos la solución al problema y la
	* imprime por pantalla
	*
	* @param Monto
	* Monto que se desea descomponer en la menor cantidad de monedas
	* posibles
	* @throws Exception
	*/
        //IChromosome
	public static IChromosome calcularCambioMinimo(int Monto)throws Exception {
		// Se crea una configuracion con valores predeterminados.		
		Configuration conf = new DefaultConfiguration();

		// Se indica en la configuracion que el elemento mas apto siempre pase a la proxima generacion		
		conf.setPreservFittestIndividual(true);

		// Se Crea la funcion de aptitud y se asigna en la configuracion
		FitnessFunction myFunc = new CambioMinimoFuncionAptitud(Monto);
		conf.setFitnessFunction(myFunc);

		// Ahora se debe indicar a la configuracion como seran los cromosomas: en
		// este caso tendran 6 genes (uno para cada tipo de moneda) con un valor
		// entero (cantidad de monedas de ese tipo).
		// Se debe crear un cromosoma de ejemplo y cargarlo en la configuracion
		// Cada gen tendra un valor maximo y minimo que debe asignarse.
		
		Gene[] sampleGenes = new Gene[6];
		sampleGenes[0] = new IntegerGene(conf, 0, 10); // Moneda 5 soles
                sampleGenes[1] = new IntegerGene(conf, 0, 10); // Moneda 2 soles
		sampleGenes[2] = new IntegerGene(conf, 0, 10); // Moneda 1 sol
		sampleGenes[3] = new IntegerGene(conf, 0, 10); // Moneda 50 centimos
                sampleGenes[4] = new IntegerGene(conf, 0, 10); // Moneda 20 centimos
                sampleGenes[5] = new IntegerGene(conf, 0, 10); // Moneda 10 centimos
                
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);

		// Por ultimo se debe indicar el tamaño de la poblacion en la
		// configuracion		
		conf.setPopulationSize(200);

		Genotype Poblacion;
		// El framework permite obtener la poblacion inicial de archivos xml
		// pero para este caso particular resulta mejor crear una poblacion
		// aleatoria, para ello se utiliza el metodo randomInitialGenotype que
		// devuelve la poblacion random creada
		Poblacion = Genotype.randomInitialGenotype(conf);

                // La Poblacion debe evolucionar para obtener resultados mas aptos
		long TiempoComienzo = System.currentTimeMillis();
		for (int i = 0; i < MAX_EVOLUCIONES_PERMITIDAS; i++) {
			Poblacion.evolve();
		}

		long TiempoFin = System.currentTimeMillis();
//		System.out.println("Tiempo total de evolucion: " + (TiempoFin - TiempoComienzo) + " ms");
                tiempoTotal=TiempoFin-TiempoComienzo;
                
		guardarPoblacion(Poblacion);

		// Una vez que la poblacion evoluciono es necesario obtener el cromosoma
		// mas apto para mostrarlo como solucion al problema planteado para ello
		// se utiliza el metodo getFittestChromosome
		IChromosome cromosomaMasApto = Poblacion.getFittestChromosome();

                return cromosomaMasApto;

//		System.out.println("El cromosoma mas apto encontrado tiene un valor de aptitud de: " + cromosomaMasApto.getFitnessValue());
//		System.out.println("Y esta formado por la siguiente distribucion de monedas: ");
//                System.out.println("\t"	+ CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 0) + " Moneda 1 sol");
//		System.out.println("\t"	+ CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 1) + " Moneda 50 centimos");
//		System.out.println("\t"	+ CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 2) + " Moneda 25 centimos");
//		System.out.println("\t"	+ CambioMinimoFuncionAptitud.getNumeroDeComendasDeGen(cromosomaMasApto, 3) + " Moneda 10 centimos");
//
//		System.out.println("Para un total de "+ CambioMinimoFuncionAptitud.montoCambioMoneda(cromosomaMasApto) + " centavos en " + CambioMinimoFuncionAptitud.getNumeroTotalMonedas(cromosomaMasApto) + " monedas.");
	}




	/**
	* Metodo principal: Recibe el monto en dinero por parametro para determinar
	* la cantidad minima de monedas necesarias para formarlo
	*
	* @param args
	* Monto de dinero
	* @throws Exception
	*	
	*/

//	public static void main(String[] args) throws Exception {
//			int amount = 175;
//			try {
//				//amount = Integer.parseInt(args[0]);
//			} catch (NumberFormatException e) {
//				System.out.println("El (Monto de dinero) debe ser un numero entero valido");
//				System.exit(1);
//			}
//			if (amount < 1 || amount >= CambioMinimoFuncionAptitud.MAX_MONTO) {
//				System.out.println("El monto de dinero debe estar entre 1 y "+ (CambioMinimoFuncionAptitud.MAX_MONTO - 1)+ ".");
//			} else {
//				calcularCambioMinimo(amount);
//			}
//
//	}



	// ---------------------------------------------------------------------
	// Este metodo permite guardar en un xml la ultima poblacion calculada
	// ---------------------------------------------------------------------

	public static void guardarPoblacion(Genotype Poblacion) throws Exception {
		DataTreeBuilder builder = DataTreeBuilder.getInstance();
		IDataCreators doc2 = builder.representGenotypeAsDocument(Poblacion);
		// create XML document from generated tree
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
		Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
		XMLManager.writeFile(xmlDoc, new File("PoblacionCambioMinimo.xml"));
	}
}
