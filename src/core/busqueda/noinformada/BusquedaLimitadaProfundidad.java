package core.busqueda.noinformada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.agente.Accion;
import core.busqueda.framework.IndicadorTopeDeAccion;
import core.busqueda.framework.Nodo;
import core.busqueda.framework.ExpansorDeNodo;
import core.busqueda.framework.Problema;
import core.busqueda.framework.Busqueda;
import core.busqueda.framework.UtilidadesDeBusqueda;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.17, page
 * 88.<br>
 * <br>
 * 
 * <pre>
 * function DEPTH-LIMITED-SEARCH(problem, limit) returns a solution, or failure/cutoff
 *   return RECURSIVE-DLS(MAKE-NODE(problem.INITIAL-STATE), problem, limit)
 *   
 * function RECURSIVE-DLS(node, problem, limit) returns a solution, or failure/cutoff
 *   if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
 *   else if limit = 0 then return cutoff
 *   else
 *       cutoff_occurred? &lt;- false
 *       for each action in problem.ACTIONS(node.STATE) do
 *           child &lt;- CHILD-NODE(problem, node, action)
 *           result &lt;- RECURSIVE-DLS(child, problem, limit - 1)
 *           if result = cutoff then cutoff_occurred? &lt;- true
 *           else if result != failure then return result
 *       if cutoff_occurred? then return cutoff else return failure
 * </pre>
 * 
 * Figure 3.17 A recursive implementation of depth-limited search.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 */
public class BusquedaLimitadaProfundidad extends ExpansorDeNodo implements Busqueda {
	private static String Costo_Camino = "pathCost";
	private static List<Accion> cortarResultado = null;
	private final int limite;

	public BusquedaLimitadaProfundidad(int limite) {
		this.limite = limite;
	}

	/**
	 * Returns <code>true</code> if the specified action list indicates a search
	 * reached it limit without finding a goal.
	 * 
	 * @param resultado
	 *            the action list resulting from a previous search
	 * 
	 * @return <code>true</code> if the specified action list indicates a search
	 *         reached it limit without finding a goal.
	 */
	public boolean isCutOff(List<Accion> resultado) {
		return 1 == resultado.size()
				&& IndicadorTopeDeAccion.CUT_OFF.equals(resultado.get(0));
	}

	/**
	 * Returns <code>true</code> if the specified action list indicates a goal
	 * not found.
	 * 
	 * @param resultado
	 *            the action list resulting from a previous search
	 * 
	 * @return <code>true</code> if the specified action list indicates a goal
	 *         not found.
	 */
	public boolean isFailure(List<Accion> resultado) {
		return 0 == resultado.size();
	}

	// function DEPTH-LIMITED-SEARCH(problem, limit) returns a solution, or
	// failure/cutoff
	/**
	 * Returns a list of actions to the goal if the goal was found, a list
 containing a single NoOp Action if already at the goal, an empty list if
 the goal could not be found, or a list containing a single
 IndicadorTopeDeAccion.CUT_OFF if the search reached its limit without
 finding a goal.
	 * 
	 * @param p
	 * @return if goal found, the list of actions to the Goal. If already at the
         goal you will receive a List with a single NoOp Action in it. If
         fail to find the Goal, an empty list will be returned to indicate
         that the search failed. If the search was cutoff (i.e. reached
         its limit without finding a goal) a List with one
         IndicadorTopeDeAccion.CUT_OFF in it will be returned (Note: this
         is a NoOp action).
	 */
	public List<Accion> search(Problema p) throws Exception {
		clearInstrumentation();
		// return RECURSIVE-DLS(MAKE-NODE(INITIAL-STATE[problem]), problem,
		// limit)
		return DLSrecursivo(new Nodo(p.getInitialState()), p, limite);
	}

	@Override
	/**
	 * Sets the nodes expanded and path cost metrics to zero.
	 */
	public void clearInstrumentation() {
		super.clearInstrumentation();
		metrics.set(Costo_Camino, 0);
	}

	/**
	 * Returns the path cost metric.
	 * 
	 * @return the path cost metric
	 */
	public double getCostoCamino() {
		return metrics.getDouble(Costo_Camino);
	}

	/**
	 * Sets the path cost metric.
	 * 
	 * @param CostoCamino
	 *            the value of the path cost metric
	 */
	public void setCostoCamino(Double CostoCamino) {
		metrics.set(Costo_Camino, CostoCamino);
	}

	//
	// PRIVATE METHODS
	//

	// function RECURSIVE-DLS(node, problem, limit) returns a solution, or
	// failure/cutoff
	private List<Accion> DLSrecursivo(Nodo node, Problema problem, int limit) {
		// if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
		if (UtilidadesDeBusqueda.isGoalState(problem, node)) {
			setCostoCamino(node.getPathCost());
			return UtilidadesDeBusqueda.actionsFromNodes(node.getPathFromRoot());
		} else if (0 == limit) {
			// else if limit = 0 then return cutoff
			return cortaroff();
		} else {
			// else
			// cutoff_occurred? <- false
			boolean cutoff_occurred = false;
			// for each action in problem.ACTIONS(node.STATE) do
			for (Nodo child : this.expandNode(node, problem)) {
				// child <- CHILD-NODE(problem, node, action)
				// result <- RECURSIVE-DLS(child, problem, limit - 1)
				List<Accion> result = DLSrecursivo(child, problem, limit - 1);
				// if result = cutoff then cutoff_occurred? <- true
				if (isCutOff(result)) {
					cutoff_occurred = true;
				} else if (!isFailure(result)) {
					// else if result != failure then return result
					return result;
				}
			}

			// if cutoff_occurred? then return cutoff else return failure
			if (cutoff_occurred) {
				return cortaroff();
			} else {
				return failure();
			}
		}
	}

	private List<Accion> cortaroff() {
		// Only want to created once
		if (null == cortarResultado) {
			cortarResultado = new ArrayList<Accion>();
			cortarResultado.add(IndicadorTopeDeAccion.CUT_OFF);
			// Ensure it cannot be modified externally.
			cortarResultado = Collections.unmodifiableList(cortarResultado);
		}
		return cortarResultado;
	}

	private List<Accion> failure() {
		return Collections.emptyList();
	}
}