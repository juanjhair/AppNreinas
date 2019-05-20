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
public class ReinaAccion extends AccionDinamica {
	public static final String Lugar_reina = "placeQueenAt";
	public static final String Eliminar_reina = "removeQueenAt";
	public static final String MoverReina = "moveQueenTo";

	public static final String ATTRIBUTE_QUEEN_LOC = "location";

	/**
	 * Creates a queen action. Supported values of type are {@link #Lugar_reina}
	 * , {@link #Eliminar_reina}, or {@link #MoverReina}.
	 */
	public ReinaAccion(String type, UbicacionXY loc) {
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
