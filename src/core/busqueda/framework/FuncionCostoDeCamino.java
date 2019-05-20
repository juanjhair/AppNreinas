package core.busqueda.framework;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 78.<br>
 * <br>
 * 
 * @author Ciaran O'Reilly
 * 
 */
public class FuncionCostoDeCamino {
	public FuncionCostoDeCamino() {
	}

	/**
	 * 
	 * @param n
	 * @return the cost, traditionally denoted by g(n), of the path from the
	 *         initial state to the node, as indicated by the parent pointers.
	 */
	public double g(Nodo n) {
		return n.getPathCost();
	}
}
