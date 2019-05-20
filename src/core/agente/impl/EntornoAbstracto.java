package core.agente.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.agente.Accion;
import core.agente.Agente;
import core.agente.Entorno;
import core.agente.ObjetoEntorno;
import core.agente.EstadoEntorno;
import core.agente.VistaEntorno;
import core.agente.NotificadorVistaEntorno;
import core.agente.Percepcion;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public abstract class EntornoAbstracto implements Entorno,
		NotificadorVistaEntorno {

	// Note: Use LinkedHashSet's in order to ensure order is respected as
	// provide
	// access to these elements via List interface.
	protected Set<ObjetoEntorno> envObjects = new LinkedHashSet<ObjetoEntorno>();

	protected Set<Agente> agents = new LinkedHashSet<Agente>();

	protected Set<VistaEntorno> views = new LinkedHashSet<VistaEntorno>();

	protected Map<Agente, Double> performanceMeasures = new LinkedHashMap<Agente, Double>();

	//
	// PRUBLIC METHODS
	//

	//
	// Methods to be implemented by subclasses.
	public abstract EstadoEntorno getCurrentState();

	public abstract EstadoEntorno executeAction(Agente agent, Accion action);

	public abstract Percepcion getPerceptSeenBy(Agente anAgent);

	/**
	 * Method for implementing dynamic environments in which not all changes are
	 * directly caused by agent action execution. The default implementation
	 * does nothing.
	 */
	public void createExogenousChange() {
	}

	//
	// START-Entorno
	public List<Agente> getAgentes() {
		// Return as a List but also ensures the caller cannot modify
		return new ArrayList<Agente>(agents);
	}

	public void agregarAgent(Agente a) {
		agregarObjetoEntorno(a);
	}

	public void eliminarAgent(Agente a) {
		removeEnvironmentObject(a);
	}

	public List<ObjetoEntorno> getObjetosEntorno() {
		// Return as a List but also ensures the caller cannot modify
		return new ArrayList<ObjetoEntorno>(envObjects);
	}

	public void agregarObjetoEntorno(ObjetoEntorno eo) {
		envObjects.add(eo);
		if (eo instanceof Agente) {
			Agente a = (Agente) eo;
			if (!agents.contains(a)) {
				agents.add(a);
				this.updateEnvironmentViewsAgentAdded(a);
			}
		}
	}

	public void removeEnvironmentObject(ObjetoEntorno eo) {
		envObjects.remove(eo);
		agents.remove(eo);
	}

	/**
	 * Central template method for controlling agent simulation. The concrete
	 * behavior is determined by the primitive operations
	 * {@link #getPerceptSeenBy(Agent)}, {@link #executeAction(Agent, Action)},
	 * and {@link #createExogenousChange()}.
	 */
	public void paso() {
		for (Agente agent : agents) {
			if (agent.estaVivo()) {
				Accion anAction = agent.ejecutar(getPerceptSeenBy(agent));
				EstadoEntorno es = executeAction(agent, anAction);
				updateEnvironmentViewsAgentActed(agent, anAction, es);
			}
		}
		createExogenousChange();
	}

	public void paso(int n) {
		for (int i = 0; i < n; i++) {
			paso();
		}
	}

	public void pasoHastaTerminar() {
		while (!estaHecha()) {
			paso();
		}
	}

	public boolean estaHecha() {
		for (Agente agent : agents) {
			if (agent.estaVivo()) {
				return false;
			}
		}
		return true;
	}

	public double getMedidaPerformance(Agente forAgent) {
		Double pm = performanceMeasures.get(forAgent);
		if (null == pm) {
			pm = new Double(0);
			performanceMeasures.put(forAgent, pm);
		}

		return pm;
	}

	public void addEnvironmentView(VistaEntorno ev) {
		views.add(ev);
	}

	public void removeEnvironmentView(VistaEntorno ev) {
		views.remove(ev);
	}

	public void notifyViews(String msg) {
		for (VistaEntorno ev : views) {
			ev.notify(msg);
		}
	}

	// END-Entorno
	//

	//
	// PROTECTED METHODS
	//

	protected void updatePerformanceMeasure(Agente forAgent, double addTo) {
		performanceMeasures.put(forAgent, getMedidaPerformance(forAgent)
				+ addTo);
	}

	protected void updateEnvironmentViewsAgentAdded(Agente agent) {
		for (VistaEntorno view : views) {
			view.agentAdded(agent, getCurrentState());
		}
	}

	protected void updateEnvironmentViewsAgentActed(Agente agent, Accion action,
			EstadoEntorno state) {
		for (VistaEntorno view : views) {
			view.agentActed(agent, action, state);
		}
	}
}