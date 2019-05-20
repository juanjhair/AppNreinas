package core.busqueda.framework;

import core.agente.impl.AccionDinamica;

/**
 * A NoOp action that indicates a CutOff has occurred in a search. Used
 * primarily by DepthLimited and IterativeDeepening search routines.
 * 
 * @author Ciaran O'Reilly
 */
public class IndicadorTopeDeAccion extends AccionDinamica {
	public static final IndicadorTopeDeAccion CUT_OFF = new IndicadorTopeDeAccion();

	//
	// START-Action
	public boolean esNoOp() {
		return true;
	}

	// END-Action
	//

	private IndicadorTopeDeAccion() {
		super("CutOff");
	}
}