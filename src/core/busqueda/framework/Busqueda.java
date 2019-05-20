package core.busqueda.framework;

import core.busqueda.framework.Metricas;
import java.util.List;

import core.agente.Accion;

/**
 * @author Ravi Mohan
 * @author Mike Stampone
 */
public interface Busqueda {

	/**
	 * Returns a list of actions to the goal if the goal was found, a list
	 * containing a single NoOp Action if already at the goal, or an empty list
	 * if the goal could not be found.
	 * 
	 * @param p
	 *            the search problem
	 * 
	 * @return a list of actions to the goal if the goal was found, a list
	 *         containing a single NoOp Action if already at the goal, or an
	 *         empty list if the goal could not be found.
	 */
	List<Accion> search(Problema p) throws Exception;

	/**
	 * Returns all the metrics of the search.
	 * 
	 * @return all the metrics of the search.
	 */
	Metricas getMetrics();
}