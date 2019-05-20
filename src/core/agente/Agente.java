package core.agente;

/**
 * Los agentes interactuan con entornos a través de sensores y actuadores.
 * 
 */
public interface Agente extends ObjetoEntorno {
	/**
	 * Llama al programa del agente, que mapea cualquier secuencia de
         * percepciones dada a una acción.
	 * 
	 * @param percepcion
	 *            La percepción actual de una secuencia percibida por el
         *            agente.
	 * @return la Accion a ser tomada en respuesta a la percepción
         *         actualmente percibida.
	 */
	Accion ejecutar(Percepcion percepcion);

	/**
	 * Indicador del ciclo de vida en cuanto a la vitalidad de un Agente.
	 * 
	 * @return true si el Agente es considerado vivo, false en caso contrario.
	 */
	boolean estaVivo();

	/**
	 * Establece la vitalidad actual del Agente.
	 * 
	 * @param vivo
	 *            establece a true si el agente se considera vivo, false en
         *            caso contrario.
	 */
	void setVitalidad(boolean vivo);
}
