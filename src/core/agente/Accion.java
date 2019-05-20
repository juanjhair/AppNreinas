package core.agente;

/**
 * Describe una acción que puede o ha sido tomada por un agente via uno de
 * sus actuadores.
 */
public interface Accion {

	/**
	 * Indica si o no esta acción es "No Operación". "No Operación" es
         * el nombre de una instrucción en el lenguaje ensamblador que no
         * hace nada.
	 * 
	 * @return true si esta no es una acción NoOp.
	 */
	boolean esNoOp();
}
