package pe.christianmtr.cambiomonedas_web.cambiador;

import org.jgap.*;

public class CambioMinimoFuncionAptitud extends FitnessFunction{
    private final int montoObjetivo;
	// Maximo monto posible 1000 Centimos = 10 soles
	public static final int MAX_MONTO = 1000;

	// Maxima cantidad de monedas posibles. Es igual al Monto maximo en
	// centavos, ya que si se utilizan monedas de un centavo se llegaria al
	// monton con la mayor cantidad posible de monedas
	public static final int MAX_CANT_MONEDAS = MAX_MONTO;

	// El constructor de la funcion de aptitud debe recibir el monto objetivo
	// del problema y almacenarlo en un atributo. Si el monto es invalido arroja
	// una excepcion
	public CambioMinimoFuncionAptitud(int monto) {
		if (monto < 1 || monto >= MAX_MONTO) {
			throw new IllegalArgumentException("El monto debe ser un numero entre 1 y " + MAX_MONTO	+ " centavos");
		}
		montoObjetivo = monto;
	}

	/**
	* El metodo evaluate es el metodo que se debe sobrecargar para que devuelva
	* el valor de aptitud asociado al cromosoma que se recibe por parametro.
	*/
	
        public double evaluate(IChromosome cromosoma) {

		// Se debe tener en cuenta el evaluador que se esta usando. El evaluador
		// estandar le asigna un valor mas apto a los valores mas altos de
		// aptitud. Tambien hay otros evaluadores que asignan mejor aptitud a
		// los valores mas bajos.
		// Es por esto que se chequea si 2 es mas apto que 1. Si esto es asi
		// entonces el valor mas apto sera el mayor y el menos apto el 0
		boolean evaluadorEstandard = cromosoma.getConfiguration().getFitnessEvaluator().isFitter(2, 1);
		int montoCambioMonedas = montoCambioMoneda(cromosoma);
		int totalMonedas = getNumeroTotalMonedas(cromosoma);
		int diferenciaMonto = Math.abs(montoObjetivo - montoCambioMonedas);

		// El primer paso es asignar la menor aptitud a aquellos cromosomas cuyo
		// monto no sea el monto objetivo. Es decir una descomposicion en
		// monedas que no sea del monto ingresado
		if (evaluadorEstandard) {
			if (diferenciaMonto != 0)
				return 0.0d;
		} else {
			if (diferenciaMonto != 0)
				return MAX_CANT_MONEDAS;
		}

                // luego se debe asignar mas aptitud a aquellos cromosomas que posean
		// menor cantidad de monedas.
		if (evaluadorEstandard) {
		// Se debe asegurar devolver un valor de aptitud positivo siempre.
		// Si el valor es negativo se devuelve MAX_CANT_MONEDAS ( elemento
		// menos apto )
			return Math.max(0.0d, MAX_CANT_MONEDAS - totalMonedas);
		} else {
		// Se debe asgurar devolver un valor de aptitud positivo siempre.
		// Si el valor es negativo se devuelve 0 ( elemento menos apto )
			return Math.max(0.0d, totalMonedas);
		}
	}

	/**
	* Calcula el monto total que suman todas las monedas de un cromosoma
	*
	*/

	public static int montoCambioMoneda(IChromosome cromosoma) {
                int moneda5Sol = getNumeroDeComendasDeGen(cromosoma, 0);
                int moneda2Sol = getNumeroDeComendasDeGen(cromosoma, 1);
                int moneda1Sol = getNumeroDeComendasDeGen(cromosoma, 2);
		int moneda50Centimos = getNumeroDeComendasDeGen(cromosoma, 3);
		int moneda20Centimos = getNumeroDeComendasDeGen(cromosoma, 4);
		int moneda10Centimos = getNumeroDeComendasDeGen(cromosoma, 5);
		
		return (moneda5Sol * 50) + (moneda2Sol * 20) + (moneda1Sol * 10) + (moneda50Centimos * 5) +
                        (moneda20Centimos * 2) + (moneda10Centimos * 1); // + (moneda5Centavos * 5)
	}

	/**
	* Calcula la cantidad de monedas de determinado tipo (gen) de un cromosoma
	* Ejemplo. Cantidad de monedas de 25 centavos es cromosoma
	*/

	public static int getNumeroDeComendasDeGen(IChromosome cromosoma,int numeroGen) {
			Integer numMonedas = (Integer) cromosoma.getGene(numeroGen).getAllele();

			return numMonedas.intValue();
	}

	/**
	* Calcula el total de monedas que tiene esa solucion. Este valor se utiliza
	* para calcular la aptitud del cromosoma ya que el objetivo es minimizar la
	* cantidad de monedas de la solucion.
	*/

	public static int getNumeroTotalMonedas(IChromosome cromosoma) {
		int totalMonedas = 0;
		int numberOfGenes = cromosoma.size();
		for (int i = 0; i < numberOfGenes; i++) {
			totalMonedas += getNumeroDeComendasDeGen(cromosoma, i);
		}

		return totalMonedas;
	}
}
