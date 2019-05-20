package core.util.estructuradedatos;

/**
 * Nota: Si observa un rectangulo - la coordenada (x=0, y=0) será la esquina
 * superior de la mano izquierda. Esto corresponde con el sistema de coordenadas
 * AWT de Java.
 */
public class UbicacionXY {
	public enum Direccion {
		Norte, Sur, Este, Oeste
	};

	int coordenadaX, coordenadaY;

	/**
	 * Construye e inicializa una ubicación en la ubicación especificada
         * (<em>x</em>,<em>y</em>) en el espacio de coordendas.
	 * 
	 * @param x
	 *            la coordenada x
	 * @param y
	 *            la coordenada y
	 */
	public UbicacionXY(int x, int y) {
		coordenadaX = x;
		coordenadaY = y;
	}

	/**
	 * Devuelve la coordenada X de la ubicación en precisión entera.
	 * 
	 * @return la coordenada X de la ubicación en precisión entera.
	 */
	public int getCoordenadaX() {
		return coordenadaX;
	}

	public int getCoordenadaY() {
		return coordenadaY;
	}

	/**
	 * Devuelve la ubicación una unidad a la izquierda de esta ubicación. 
	 * 
	 * @return la ubicación una unidad a la izquierda de esta ubicación.
	 */
	public UbicacionXY oeste() {
		return new UbicacionXY(coordenadaX - 1, coordenadaY);
	}

	/**
	 * Devuelve la ubicación una unidad a la derecha de esta ubicación.
	 * 
	 * @return la ubicación una unidad a la derecha de esta ubicación.
	 */
	public UbicacionXY este() {
		return new UbicacionXY(coordenadaX + 1, coordenadaY);
	}

	/**
	 * Devuelve la ubicación una unidad arriba de esta ubicación.
	 * 
	 * @return la ubicación una unidad arriba de esta ubicación.
	 */
	public UbicacionXY norte() {
		return new UbicacionXY(coordenadaX, coordenadaY - 1);
	}

	/**
	 * Devuelve la ubicación una unidad debajo de esta ubicación.
	 * 
	 * @return la ubicación una unidad debajo de esta ubicación.
	 */
	public UbicacionXY sur() {
		return new UbicacionXY(coordenadaX, coordenadaY + 1);
	}

	/**
	 * Devuelve la ubicación una unidad a la izquierda de esta ubicación.
	 * 
	 * @return la ubicación una unidad a la izquierda de esta ubicación.
	 */
	public UbicacionXY izquierda() {
		return oeste();
	}

	/**
	 * Devuelve la ubicación una unidad a la derecha de esta ubicación.
	 * 
	 * @return la ubicación una unidad a la derecha de esta ubicación..
	 */
	public UbicacionXY derecha() {
		return este();
	}

	/**
	 * Devuelve la ubicación una unidad encima de esta ubicación.
  	 * 
	 * @return la ubicación una unidad encima de esta ubicación.
	 */
	public UbicacionXY arriba() {
		return norte();
	}

	/**
	 * Devuelve la ubicación una unidad debajo de esta ubicación.
	 * 
	 * @return la ubicación una unidad debajo de esta ubicación.
	 */
	public UbicacionXY abajo() {
		return sur();
	}

	/**
	 * Devuelve la ubicación una unidad de esta ubicación en la dirección
         * especificada.
	 * 
	 * @return la ubicación una unidad de esta ubicación en la dirección
	 *         especificada.
	 */
	public UbicacionXY ubicacionA(Direccion direccion) {
		if (direccion.equals(Direccion.Norte)) {
			return norte();
		}
		if (direccion.equals(Direccion.Sur)) {
			return sur();
		}
		if (direccion.equals(Direccion.Este)) {
			return este();
		}
		if (direccion.equals(Direccion.Oeste)) {
			return oeste();
		} else {
			throw new RuntimeException("Dirección desconocida "
                                + direccion);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (null == o || !(o instanceof UbicacionXY)) {
			return super.equals(o);
		}
		UbicacionXY otraUbic = (UbicacionXY) o;
		return ((otraUbic.getCoordenadaX() == coordenadaX) && (otraUbic
				.getCoordenadaY() == coordenadaY));
	}

	@Override
	public String toString() {
		return " ( " + coordenadaX + " , " + coordenadaY + " ) ";
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + coordenadaX;
		result = 43 * result + coordenadaY;
		return result;
	}
}