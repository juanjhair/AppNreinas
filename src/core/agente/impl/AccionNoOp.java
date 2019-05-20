package core.agente.impl;

/**
 * @author Ciaran O'Reilly
 */
public class AccionNoOp extends AccionDinamica {

	public static final AccionNoOp NO_OP = new AccionNoOp();

	//
	// START-Action
	public boolean esNoOp() {
		return true;
	}

	// END-Action
	//

	private AccionNoOp() {
		super("NoOp");
	}
}
