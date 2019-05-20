package core.entorno.nreinas;

import core.agente.impl.AccionDinamica;
import core.util.estructuradedatos.UbicacionXY;

/**
 * Queens can be placed, removed, and moved. For movements, a vertical direction
 * is assumed. Therefore, only the end point needs to be specified.
 * 
 * @author Ravi Mohan
 * @author R. Lunde
 */
public class QueenAction extends AccionDinamica {
	public static final String PLACE_QUEEN = "placeQueenAt";
	public static final String REMOVE_QUEEN = "removeQueenAt";
	public static final String MOVE_QUEEN = "moveQueenTo";

	public static final String ATTRIBUTE_QUEEN_LOC = "location";

	/**
	 * Creates a queen action. Supported values of type are {@link #PLACE_QUEEN}
	 * , {@link #REMOVE_QUEEN}, or {@link #MOVE_QUEEN}.
	 */
	public QueenAction(String type, UbicacionXY loc) {
		super(type);
		setAttribute(ATTRIBUTE_QUEEN_LOC, loc);
	}

	public UbicacionXY getLocation() {
		return (UbicacionXY) getAttribute(ATTRIBUTE_QUEEN_LOC);
	}

	public int getX() {
		return getLocation().getCoordenadaX();
	}

	public int getY() {
		return getLocation().getCoordenadaY();
	}
}
