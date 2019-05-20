package core.entorno.nreinas;

import core.busqueda.framework.FuncionHeuristica;

/**
 * Estima la distancia a la meta por el número de pares de reinas atacandose
 * en el tablero.
 */
public class HeuristicaParesAtacandose implements FuncionHeuristica {

	public double h(Object estado) {
		TableroNReinas tablero = (TableroNReinas) estado;
		return tablero.getNumeroDeParesAtacandose();
	}
}