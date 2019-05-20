package core.agente;

import java.util.List;

/**
 * Una descipcion abatracta de posibles entornos discretos en el cual el
 * Agente(s) puede percibir y actuar.
 */
public interface Entorno {
	/**
	 * Devuelve los agentes que pertenecen a este Entorno.
	 * 
	 * @return Los agentes que pertenecen a este Entorno.
	 */
	List<Agente> getAgentes();

	/**
	 * Agrega un agente al Entorno.
	 * 
	 * @param agente
	 *            el agente a ser agregado.
	 */
	void agregarAgent(Agente agente);

	/**
	 * Eliminar un agente del entorno.
	 * 
	 * @param agente
	 *            el agente a ser eliminado.
	 */
	void eliminarAgent(Agente agente);

	/**
	 * Devuelve los Objetos Entorno que existen en este Entorno.
	 * 
	 * @return los Objetos Entorno que existen en este Entorno.
	 */
	List<ObjetoEntorno> getObjetosEntorno();

	/**
	 * Agrega un ObjetoEntorno al Entorno.
	 * 
	 * @param oe
	 *            el ObjetoEntorno a ser agregado.
	 */
	void agregarObjetoEntorno(ObjetoEntorno oe);

	/**
	 * Eliminar un ObjetoEntorno del Entorno.
	 * 
	 * @param oe
	 *            el ObjetoEntorno a ser removido.
	 */
	void removeEnvironmentObject(ObjetoEntorno oe);

	/**
	 * Mover el Entorno un paso de tiempo hacia adelante.
	 */
	void paso();

	/**
	 * Mover el Entorno n pasos de tiempo hacia adelante.
	 * 
	 * @param n
	 *            el número de pasos en el tiempo a mover el Entorno hacia adelante.
	 */
	void paso(int n);

	/**
	 * Mover tantos pasos hasta que el Entorno no tenga más tareas.
	 */
	void pasoHastaTerminar();

	/**
	 * Devuelve <code>true</code> si el Entorno terminó con su(s)
	 * tarea(s) actual(es).
	 * 
	 * @return <code>true</code> si el Entorno terminó con su(s)
	 *         tarea(s) actual(es)
	 */
	boolean estaHecha();

	/**
	 * Recupera la medida de desempeño asociada con un Agente.
	 * 
	 * @param paraAgente
	 *            el Agente para la cual la medida de desempeño se recupera.
	 * @return la medida de desempeño asociada con el Agente.
	 */
	double getMedidaPerformance(Agente paraAgente);

	/**
	 * Add a view on the Entorno.
	 * 
	 * @param ev
	 *            the VistaEntorno to be added.
	 */
	void addEnvironmentView(VistaEntorno ev);

	/**
	 * Remove a view on the Entorno.
	 * 
	 * @param ev
	 *            the VistaEntorno to be removed.
	 */
	void removeEnvironmentView(VistaEntorno ev);

	/**
	 * Notify all registered EnvironmentViews of a message.
	 * 
	 * @param msg
	 *            the message to notify the registered EnvironmentViews with.
	 */
	void notifyViews(String msg);
}
