package core.busqueda.noinformada;

import java.util.List;

import core.agente.Accion;
import core.busqueda.framework.Metricas;
import core.busqueda.framework.Nodo;
import core.busqueda.framework.Problema;
import core.busqueda.framework.BusquedaCola;
import core.busqueda.framework.Busqueda;
import core.util.estructuradedatos.LIFOQueue;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 85.<br>
 * <br>
 * Depth-first search always expands the deepest node in the current frontier of
 * the search tree. <br>
 * <br>
 * <b>Note:</b> Supports both Tree and Graph based versions by assigning an
 * instance of TreeSearch or GraphSearch to its constructor.
 * 
 * @author Ravi Mohan
 * 
 */
public class BusquedaProfundidad implements Busqueda {

	BusquedaCola search;

	public BusquedaProfundidad(BusquedaCola search) {
		this.search = search;
	}

	public List<Accion> search(Problema p) {
		return search.search(p, new LIFOQueue<Nodo>());
	}

	public Metricas getMetrics() {
		return search.getMetrics();
	}
}