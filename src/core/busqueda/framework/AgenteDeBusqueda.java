package core.busqueda.framework;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import core.agente.Accion;
import core.agente.Percepcion;
import core.agente.impl.AgenteAbstracto;
import core.agente.impl.AccionNoOp;

/**
 * @author Ravi Mohan
 * 
 */
public class AgenteDeBusqueda extends AgenteAbstracto {
	protected List<Accion> actionList;

	private Iterator<Accion> actionIterator;

	private Metricas searchMetrics;

	public AgenteDeBusqueda(Problema p, Busqueda search) throws Exception {
		actionList = search.search(p);
		actionIterator = actionList.iterator();
		searchMetrics = search.getMetrics();
	}

	@Override
	public Accion ejecutar(Percepcion p) {
		if (actionIterator.hasNext()) {
			return actionIterator.next();
		} else {
			return AccionNoOp.NO_OP;
		}
	}

	public boolean isDone() {
		return !actionIterator.hasNext();
	}

	public List<Accion> getActions() {
		return actionList;
	}

	public Properties getInstrumentation() {
		Properties retVal = new Properties();
		Iterator<String> iter = searchMetrics.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String value = searchMetrics.get(key);
			retVal.setProperty(key, value);
		}
		return retVal;
	}
}