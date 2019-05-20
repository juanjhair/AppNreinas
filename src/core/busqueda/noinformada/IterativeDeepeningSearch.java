package core.busqueda.noinformada;

import java.util.Collections;
import java.util.List;

import core.agente.Accion;
import core.busqueda.framework.Metricas;
import core.busqueda.framework.ExpansorDeNodo;
import core.busqueda.framework.Problema;
import core.busqueda.framework.Busqueda;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.18, page
 * 89.<br>
 * <br>
 * 
 * <pre>
 * function ITERATIVE-DEEPENING-SEARCH(problem) returns a solution, or failure
 *   for depth = 0 to infinity  do
 *     result &lt;- DEPTH-LIMITED-SEARCH(problem, depth)
 *     if result != cutoff then return result
 * </pre>
 * 
 * Figure 3.18 The iterative deepening search algorithm, which repeatedly
 * applies depth-limited search with increasing limits. It terminates when a
 * solution is found or if the depth- limited search returns failure, meaning
 * that no solution exists.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class IterativeDeepeningSearch extends ExpansorDeNodo implements Busqueda {
	public static final String PATH_COST = "pathCost";

	// Not infinity, but will do, :-)
	private final int infinity = Integer.MAX_VALUE;

	private final Metricas iterationMetrics;

	public IterativeDeepeningSearch() {
		iterationMetrics = new Metricas();
		iterationMetrics.set(METRIC_NODES_EXPANDED, 0);
		iterationMetrics.set(PATH_COST, 0);
	}

	// function ITERATIVE-DEEPENING-SEARCH(problem) returns a solution, or
	// failure
	public List<Accion> search(Problema p) throws Exception {
		iterationMetrics.set(METRIC_NODES_EXPANDED, 0);
		iterationMetrics.set(PATH_COST, 0);
		// for depth = 0 to infinity do
		for (int i = 0; i <= infinity; i++) {
			// result <- DEPTH-LIMITED-SEARCH(problem, depth)
			DepthLimitedSearch dls = new DepthLimitedSearch(i);
			List<Accion> result = dls.search(p);
			iterationMetrics.set(METRIC_NODES_EXPANDED,
					iterationMetrics.getInt(METRIC_NODES_EXPANDED)
							+ dls.getMetrics().getInt(METRIC_NODES_EXPANDED));
			// if result != cutoff then return result
			if (!dls.isCutOff(result)) {
				iterationMetrics.set(PATH_COST, dls.getPathCost());
				return result;
			}
		}
		return failure();
	}

	@Override
	public Metricas getMetrics() {
		return iterationMetrics;
	}

	//
	// PRIVATE METHODS
	//

	private List<Accion> failure() {
		return Collections.emptyList();
	}
}