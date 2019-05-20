package core.entorno.nreinas;

import core.busqueda.framework.PruebaDeMeta;

/**
 * @author R. Lunde
 */
public class NQueensGoalTest implements PruebaDeMeta {

	public boolean isGoalState(Object state) {
		TableroNReinas board = (TableroNReinas) state;
		return board.getNumeroDeReinasEnTablero() == board.getTamanno()
				&& board.getNumeroDeParesAtacandose() == 0;
	}
}