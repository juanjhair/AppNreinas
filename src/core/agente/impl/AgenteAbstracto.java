package core.agente.impl;

import core.agente.Accion;
import core.agente.Agente;
import core.agente.ProgramaAgente;
import core.agente.Percepcion;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 */
public abstract class AgenteAbstracto implements Agente {

	protected ProgramaAgente program;
	private boolean alive = true;

	public AgenteAbstracto() {

	}

	/**
	 * Constructs an Agent with the specified ProgramaAgente.
	 * 
	 * @param aProgram
	 *            the Agent's program, which maps any given percept sequences to
	 *            an action.
	 */
	public AgenteAbstracto(ProgramaAgente aProgram) {
		program = aProgram;
	}

	//
	// START-Agent
	public Accion ejecutar(Percepcion p) {
		if (null != program) {
			return program.ejecutar(p);
		}
		return AccionNoOp.NO_OP;
	}

	public boolean estaVivo() {
		return alive;
	}

	public void setVitalidad(boolean alive) {
		this.alive = alive;
	}

	// END-Agent
	//
}