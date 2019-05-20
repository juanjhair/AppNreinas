package core.busqueda.framework;

import core.agente.Accion;

/**
 * Returns one for every action.
 * 
 * @author Ravi Mohan
 */
public class FuncionCostoDePasoPorDefecto implements FuncionCostoDePaso {

	public double c(Object stateFrom, Accion action, Object stateTo) {
		return 1;
	}
}