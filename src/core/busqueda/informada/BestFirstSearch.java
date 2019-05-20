package core.busqueda.informada;

import java.util.Comparator;

import core.busqueda.framework.FuncionEvaluacion;
import core.busqueda.framework.Nodo;
import core.busqueda.framework.BusquedaConPrioridad;
import core.busqueda.framework.BusquedaCola;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 92.<br>
 * <br>
 * Best-first search is an instance of the general TREE-SEARCH or GRAPH-SEARCH
 * algorithm in which a node is selected for expansion based on an evaluation
 * function, f(n). The evaluation function is construed as a cost estimate, so
 * the node with the lowest evaluation is expanded first. The implementation of
 * best-first graph search is identical to that for uniform-cost search (Figure
 * 3.14), except for the use of f instead of g to order the priority queue.
 * 
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 * @author Ruediger Lunde
 */
public class BestFirstSearch extends BusquedaConPrioridad {

	/**
	 * Constructs a best first search from a specified search problem and
	 * evaluation function.
	 * 
	 * @param search
	 *            a search problem
	 * @param ef
	 *            an evaluation function, which returns a number purporting to
	 *            describe the desirability (or lack thereof) of expanding a
	 *            node
	 */
	public BestFirstSearch(BusquedaCola search, FuncionEvaluacion ef) {
		super(search, createComparator(ef));
	}

	private static Comparator<Nodo> createComparator(final FuncionEvaluacion ef) {
		return new Comparator<Nodo>() {
			public int compare(Nodo n1, Nodo n2) {
				Double f1 = ef.f(n1);
				Double f2 = ef.f(n2);
				return f1.compareTo(f2);
			}
		};
	}
}
