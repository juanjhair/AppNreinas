package core.agente;

/**
 * El comportamiento de un agente es descrito por la "función agente" que mapea
 * cualquier secuencia de percepciones dado a una acción. Internamente, la
 * función agente para un agente artificial será ejecutado por un programa
 * agente. 
 */
public interface ProgramaAgente {
	/**
	 * El programa de la agente, que mapea cualquier secuencia de
         * percepciones dada a una acción.
	 * 
	 * @param percepcion
	 *            La percepción actual de una secuencia percibida por el agente.
	 * @return la Acción a ser tomada en respuesta a la percepción
         *         actualmente percibida.
	 */
	Accion ejecutar(Percepcion percepcion);
}
