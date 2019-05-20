package core.busqueda.framework;

import java.util.ArrayList;
import java.util.List;

import core.agente.Accion;

/**
 * @author Ravi Mohan
 * @author Mike Stampone
 */
public class ExpansorDeNodo {
	public static final String METRIC_NODES_EXPANDED = "nodesExpanded";

	protected Metricas metrics;

	public ExpansorDeNodo() {
		metrics = new Metricas();
	}

	/**
	 * Sets the nodes expanded metric to zero.
	 */
	public void clearInstrumentation() {
		metrics.set(METRIC_NODES_EXPANDED, 0);
	}

	/**
	 * Returns the number of nodes expanded so far.
	 * 
	 * @return the number of nodes expanded so far.
	 */
	public int getNodesExpanded() {
		return metrics.getInt(METRIC_NODES_EXPANDED);
	}

	/**
	 * Returns all the metrics of the node expander.
	 * 
	 * @return all the metrics of the node expander.
	 */
	public Metricas getMetrics() {
		return metrics;
	}

	/**
	 * Returns the children obtained from expanding the specified node in the
	 * specified problem.
	 * 
	 * @param node
	 *            the node to expand
	 * @param problem
	 *            the problem the specified node is within.
	 * 
	 * @return the children obtained from expanding the specified node in the
	 *         specified problem.
	 */
	public List<Nodo> expandNode(Nodo node, Problema problem) {
		List<Nodo> childNodes = new ArrayList<Nodo>();

		FuncionAcciones actionsFunction = problem.getActionsFunction();
		FuncionResultado resultFunction = problem.getResultFunction();
		FuncionCostoDePaso stepCostFunction = problem.getStepCostFunction();

		for (Accion action : actionsFunction.actions(node.getState())) {
			Object successorState = resultFunction.result(node.getState(),
					action);

			double stepCost = stepCostFunction.c(node.getState(), action,
					successorState);
			childNodes.add(new Nodo(successorState, node, action, stepCost));
		}
		metrics.set(METRIC_NODES_EXPANDED,
				metrics.getInt(METRIC_NODES_EXPANDED) + 1);

		return childNodes;
	}
}