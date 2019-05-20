package core.busqueda.noinformada;

import java.util.List;

import core.agente.Accion;
import core.busqueda.framework.BusquedaDeGrafo;
import core.busqueda.framework.Metricas;
import core.busqueda.framework.Nodo;
import core.busqueda.framework.Problema;
import core.busqueda.framework.BusquedaCola;
import core.busqueda.framework.Busqueda;
import core.util.estructuradedatos.FIFOQueue;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.11, page
 * 82.<br>
 * <br>
 * 
 * <pre>
 * function BREADTH-FIRST-SEARCH(problem) returns a solution, or failure
 *   node &lt;- a node with STATE = problem.INITIAL-STATE, PATH-COST=0
 *   if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
 *   frontier &lt;- a FIFO queue with node as the only element
 *   explored &lt;- an empty set
 *   loop do
 *      if EMPTY?(frontier) then return failure
 *      node &lt;- POP(frontier) // chooses the shallowest node in frontier
 *      add node.STATE to explored
 *      for each action in problem.ACTIONS(node.STATE) do
 *          child &lt;- CHILD-NODE(problem, node, action)
 *          if child.STATE is not in explored or frontier then
 *              if problem.GOAL-TEST(child.STATE) then return SOLUTION(child)
 *              frontier &lt;- INSERT(child, frontier)
 * </pre>
 * 
 * Figure 3.11 Breadth-first search on a graph.<br>
 * <br>
 * <b>Note:</b> Supports both Tree and Graph based versions by assigning an
 instance of TreeSearch or BusquedaDeGrafo to its constructor.
 * 
 * @author Ciaran O'Reilly
 */
public class BreadthFirstSearch implements Busqueda {

	private final BusquedaCola search;

	public BreadthFirstSearch() {
		this(new BusquedaDeGrafo());
	}

	public BreadthFirstSearch(BusquedaCola search) {
		// Goal test is to be applied to each node when it is generated
		// rather than when it is selected for expansion.
		search.setCheckGoalBeforeAddingToFrontier(true);
		this.search = search;
	}

	public List<Accion> search(Problema p) {
		return search.search(p, new FIFOQueue<Nodo>());
	}

	public Metricas getMetrics() {
		return search.getMetrics();
	}
}