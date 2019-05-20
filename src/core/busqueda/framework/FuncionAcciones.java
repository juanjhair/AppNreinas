package core.busqueda.framework;

import java.util.Set;

import core.agente.Accion;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 67.<br>
 * <br>
 * Given a particular state s, ACTIONS(s) returns the set of actions that can be
 * executed in s. We say that each of these actions is <b>applicable</b> in s.
 * 
 * @author Ciaran O'Reilly
 * 
 */
public interface FuncionAcciones {
	/**
	 * Given a particular state s, returns the set of actions that can be
	 * executed in s.
	 * 
	 * @param s
	 *            a particular state.
	 * @return the set of actions that can be executed in s.
	 */
	Set<Accion> actions(Object s);
}
