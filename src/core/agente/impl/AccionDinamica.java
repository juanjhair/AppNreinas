package core.agente.impl;

import core.agente.Accion;

/**
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 */
public class AccionDinamica extends ObjetoConAtributosDinamicos implements
		Accion {
	public static final String ATTRIBUTE_NAME = "name";

	//

	public AccionDinamica(String name) {
		this.setAttribute(ATTRIBUTE_NAME, name);
	}

	/**
	 * Returns the value of the name attribute.
	 * 
	 * @return the value of the name attribute.
	 */
	public String getName() {
		return (String) getAttribute(ATTRIBUTE_NAME);
	}

	//
	// START-Action
	public boolean esNoOp() {
		return false;
	}

	// END-Action
	//

	@Override
	public String describeType() {
		return Accion.class.getSimpleName();
	}
}