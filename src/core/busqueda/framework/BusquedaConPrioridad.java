package core.busqueda.framework;

import java.util.Comparator;
import java.util.List;

import core.agente.Accion;
import core.util.estructuradedatos.PriorityQueue;

/**
 * @author Ravi Mohan
 * @author Ruediger Lunde
 */
public class BusquedaConPrioridad implements Busqueda {
	private final BusquedaCola search;
	private final Comparator<Nodo> comparator;

	public BusquedaConPrioridad(BusquedaCola search, Comparator<Nodo> comparator) {
		this.search = search;
		this.comparator = comparator;
		if (search instanceof BusquedaDeGrafo) {
			((BusquedaDeGrafo) search)
					.setReplaceFrontierNodeAtStateCostFunction(comparator);
		}
	}
	
	public List<Accion> search(Problema p) throws Exception {
		return search.search(p, new PriorityQueue<Nodo>(5, comparator));
	}

	public Metricas getMetrics() {
		return search.getMetrics();
	}
}