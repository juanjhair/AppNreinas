package core.busqueda.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.agente.Accion;
import core.busqueda.framework.FuncionHeuristica;
import core.busqueda.framework.Nodo;
import core.busqueda.framework.ExpansorDeNodo;
import core.busqueda.framework.Problema;
import core.busqueda.framework.Busqueda;
import core.busqueda.framework.UtilidadesDeBusqueda;
import core.util.CancelableThread;
import core.util.Util;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 4.5, page
 * 126.<br>
 * <br>
 * 
 * <pre>
 * function SIMULATED-ANNEALING(problem, schedule) returns a solution state
 *                    
 *   current &lt;- MAKE-NODE(problem.INITIAL-STATE)
 *   for t = 1 to INFINITY do
 *     T &lt;- schedule(t)
 *     if T = 0 then return current
 *     next &lt;- a randomly selected successor of current
 *     /\E &lt;- next.VALUE - current.value
 *     if /\E &gt; 0 then current &lt;- next
 *     else current &lt;- next only with probability e&circ;(/\E/T)
 * </pre>
 * 
 * Figure 4.5 The simulated annealing search algorithm, a version of stochastic
 * hill climbing where some downhill moves are allowed. Downhill moves are
 * accepted readily early in the annealing schedule and then less often as time
 * goes on. The schedule input determines the value of the temperature T as a
 * function of time.
 * 
 * @author Ravi Mohan
 * @author Mike Stampone
 */
public class SimulatedAnnealingSearch extends ExpansorDeNodo implements Busqueda {

	public enum SearchOutcome {
		FAILURE, SOLUTION_FOUND
	};

	private final FuncionHeuristica hf;
	private final Scheduler scheduler;

	private SearchOutcome outcome = SearchOutcome.FAILURE;

	private Object lastState = null;

	/**
	 * Constructs a simulated annealing search from the specified heuristic
	 * function and a default scheduler.
	 * 
	 * @param hf
	 *            a heuristic function
	 */
	public SimulatedAnnealingSearch(FuncionHeuristica hf) {
		this.hf = hf;
		this.scheduler = new Scheduler();
	}

	/**
	 * Constructs a simulated annealing search from the specified heuristic
	 * function and scheduler.
	 * 
	 * @param hf
	 *            a heuristic function
	 * @param scheduler
	 *            a mapping from time to "temperature"
	 */
	public SimulatedAnnealingSearch(FuncionHeuristica hf, Scheduler scheduler) {
		this.hf = hf;
		this.scheduler = scheduler;
	}

	// function SIMULATED-ANNEALING(problem, schedule) returns a solution state
	public List<Accion> search(Problema p) throws Exception {
		clearInstrumentation();
		outcome = SearchOutcome.FAILURE;
		lastState = null;
		// current <- MAKE-NODE(problem.INITIAL-STATE)
		Nodo current = new Nodo(p.getInitialState());
		Nodo next = null;
		List<Accion> ret = new ArrayList<Accion>();
		// for t = 1 to INFINITY do
		int timeStep = 0;
		while (!CancelableThread.currIsCanceled()) {
			// temperature <- schedule(t)
			double temperature = scheduler.getTemp(timeStep);
			timeStep++;
			// if temperature = 0 then return current
			if (temperature == 0.0) {
				if (UtilidadesDeBusqueda.isGoalState(p, current)) {
					outcome = SearchOutcome.SOLUTION_FOUND;
				}
				ret = UtilidadesDeBusqueda.actionsFromNodes(current.getPathFromRoot());
				lastState = current.getState();
				break;
			}

			List<Nodo> children = expandNode(current, p);
			if (children.size() > 0) {
				// next <- a randomly selected successor of current
				next = Util.selectRandomlyFromList(children);
				// /\E <- next.VALUE - current.value
				double deltaE = getValue(p, next) - getValue(p, current);

				if (shouldAccept(temperature, deltaE)) {
					current = next;
				}
			}
		}

		return ret;
	}

	/**
	 * Returns <em>e</em><sup>&delta<em>E / T</em></sup>
	 * 
	 * @param temperature
	 *            <em>T</em>, a "temperature" controlling the probability of
	 *            downward steps
	 * @param deltaE
	 *            VALUE[<em>next</em>] - VALUE[<em>current</em>]
	 * @return <em>e</em><sup>&delta<em>E / T</em></sup>
	 */
	public double probabilityOfAcceptance(double temperature, double deltaE) {
		return Math.exp(deltaE / temperature);
	}

	public SearchOutcome getOutcome() {
		return outcome;
	}

	/**
	 * Returns the last state from which the simulated annealing search found a
	 * solution state.
	 * 
	 * @return the last state from which the simulated annealing search found a
	 *         solution state.
	 */
	public Object getLastSearchState() {
		return lastState;
	}

	//
	// PRIVATE METHODS
	//

	// if /\E > 0 then current <- next
	// else current <- next only with probability e^(/\E/T)
	private boolean shouldAccept(double temperature, double deltaE) {
		return (deltaE > 0.0)
				|| (new Random().nextDouble() <= probabilityOfAcceptance(
						temperature, deltaE));
	}

	private double getValue(Problema p, Nodo n) {
		// assumption greater heuristic value =>
		// HIGHER on hill; 0 == goal state;
		// SA deals with gardient DESCENT
		return -1 * hf.h(n.getState());
	}
}