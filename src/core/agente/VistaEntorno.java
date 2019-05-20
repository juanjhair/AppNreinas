package core.agente;

/**
 * Allows external applications/logic to view the interaction of Agent(s) with
 * an Environment.
 */
public interface VistaEntorno {
	/**
	 * A simple notification message from an object in the Environment.
	 * 
	 * @param msg
	 *            the message received.
	 */
	void notify(String msg);

	/**
	 * Indicates an Agent has been added to the environment and what it
	 * perceives initially.
	 * 
	 * @param agent
	 *            the Agent just added to the Environment.
	 * @param resultingState
	 *            the EstadoEntorno that resulted from the Agent being added
	 *            to the Environment.
	 */
	void agentAdded(Agente agent, EstadoEntorno resultingState);

	/**
	 * Indicates the Environment has changed as a result of an Agent's action.
	 * 
	 * @param agent
	 *            the Agent that performed the Action.
	 * @param action
	 *            the Action the Agent performed.
	 * @param resultingState
	 *            the EstadoEntorno that resulted from the Agent's Action on
	 *            the Environment.
	 */
	void agentActed(Agente agent, Accion action, EstadoEntorno resultingState);
}
