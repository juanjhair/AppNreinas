package core.busqueda.framework;

import java.util.Collections;
import java.util.List;

import core.agente.Accion;
import core.util.CancelableThread;
import core.util.estructuradedatos.Queue;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 */
public abstract class BusquedaCola extends ExpansorDeNodo {
	public static final String METRIC_QUEUE_SIZE = "queueSize";

	public static final String METRIC_MAX_QUEUE_SIZE = "maxQueueSize";

	public static final String METRIC_PATH_COST = "pathCost";

	//
	//
	private Queue<Nodo> frontier = null;
	private boolean checkGoalBeforeAddingToFrontier = false;

	public boolean isFailure(List<Accion> result) {
		return 0 == result.size();
	}

	/**
	 * Returns a list of actions to the goal if the goal was found, a list
	 * containing a single NoOp Action if already at the goal, or an empty list
	 * if the goal could not be found.
	 * 
	 * @param problem
	 *            the search problem
	 * @param frontier
	 *            the collection of nodes that are waiting to be expanded
	 * 
	 * @return a list of actions to the goal if the goal was found, a list
	 *         containing a single NoOp Action if already at the goal, or an
	 *         empty list if the goal could not be found.
	 */
	public List<Accion> search(Problema problem, Queue<Nodo> frontier) {
		this.frontier = frontier;

		clearInstrumentation();
		// initialize the frontier using the initial state of the problem
		Nodo root = new Nodo(problem.getInitialState());
		if (isCheckGoalBeforeAddingToFrontier()) {
			if (UtilidadesDeBusqueda.isGoalState(problem, root)) {
				return UtilidadesDeBusqueda.actionsFromNodes(root.getPathFromRoot());
			}
		}
		frontier.insert(root);
		setQueueSize(frontier.size());
		while (!(frontier.isEmpty()) && !CancelableThread.currIsCanceled()) {
			// choose a leaf node and remove it from the frontier
			Nodo nodeToExpand = popNodeFromFrontier();
			setQueueSize(frontier.size());
			// Only need to check the nodeToExpand if have not already
			// checked before adding to the frontier
			if (!isCheckGoalBeforeAddingToFrontier()) {
				// if the node contains a goal state then return the
				// corresponding solution
				if (UtilidadesDeBusqueda.isGoalState(problem, nodeToExpand)) {
					setPathCost(nodeToExpand.getPathCost());
					return UtilidadesDeBusqueda.actionsFromNodes(nodeToExpand
							.getPathFromRoot());
				}
			}
			// expand the chosen node, adding the resulting nodes to the
			// frontier
			for (Nodo fn : getResultingNodesToAddToFrontier(nodeToExpand,
					problem)) {
				if (isCheckGoalBeforeAddingToFrontier()) {
					if (UtilidadesDeBusqueda.isGoalState(problem, fn)) {
						setPathCost(fn.getPathCost());
						return UtilidadesDeBusqueda.actionsFromNodes(fn
								.getPathFromRoot());
					}
				}
				frontier.insert(fn);
			}
			setQueueSize(frontier.size());
		}
		// if the frontier is empty then return failure
		return failure();
	}

	public boolean isCheckGoalBeforeAddingToFrontier() {
		return checkGoalBeforeAddingToFrontier;
	}

	public void setCheckGoalBeforeAddingToFrontier(
			boolean checkGoalBeforeAddingToFrontier) {
		this.checkGoalBeforeAddingToFrontier = checkGoalBeforeAddingToFrontier;
	}

	/**
	 * Removes and returns the node at the head of the frontier.
	 * 
	 * @return the node at the head of the frontier.
	 */
	public Nodo popNodeFromFrontier() {
		return frontier.pop();
	}

	public boolean removeNodeFromFrontier(Nodo toRemove) {
		return frontier.remove(toRemove);
	}

	public abstract List<Nodo> getResultingNodesToAddToFrontier(
			Nodo nodeToExpand, Problema p);

	@Override
	public void clearInstrumentation() {
		super.clearInstrumentation();
		metrics.set(METRIC_QUEUE_SIZE, 0);
		metrics.set(METRIC_MAX_QUEUE_SIZE, 0);
		metrics.set(METRIC_PATH_COST, 0);
	}

	public int getQueueSize() {
		return metrics.getInt("queueSize");
	}

	public void setQueueSize(int queueSize) {

		metrics.set(METRIC_QUEUE_SIZE, queueSize);
		int maxQSize = metrics.getInt(METRIC_MAX_QUEUE_SIZE);
		if (queueSize > maxQSize) {
			metrics.set(METRIC_MAX_QUEUE_SIZE, queueSize);
		}
	}

	public int getMaxQueueSize() {
		return metrics.getInt(METRIC_MAX_QUEUE_SIZE);
	}

	public double getPathCost() {
		return metrics.getDouble(METRIC_PATH_COST);
	}

	public void setPathCost(Double pathCost) {
		metrics.set(METRIC_PATH_COST, pathCost);
	}

	//
	// PRIVATE METHODS
	//
	private List<Accion> failure() {
		return Collections.emptyList();
	}
}