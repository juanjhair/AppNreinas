package core.entorno.nreinas;

import java.util.ArrayList;
import java.util.List;

import core.util.estructuradedatos.UbicacionXY;

/**
 * Representa un tablero cuadrado con una matriz de casillas en las que
 * las reinas se pueden colocar (solo uno por cuadrado) y movidos.
 */
public class TableroNReinas {

	/**
	 * X se incrementa de izquierda a derecha con un índice que comienza
         * con cero, Y se incrementa de arriba hacia abajo con un índice que
         * comienza en cero
	 */
	int[][] casillas;

	int tamanno;

	public TableroNReinas(int n) {
            tamanno = n;
            casillas = new int[tamanno][tamanno];
            for (int i = 0; i < tamanno; i++) {
                for (int j = 0; j < tamanno; j++) {
                    casillas[i][j] = 0;
                }
            }
	}

	public void limpiar() {
            for (int i = 0; i < tamanno; i++) {
                for (int j = 0; j < tamanno; j++) {
                    casillas[i][j] = 0;
                }
            }
	}

	public void setTablero(List<UbicacionXY> al) {
            limpiar();
            for (int i = 0; i < al.size(); i++) {
                agregarReinaEn(al.get(i));
            }
	}

	public int getTamanno() {
		return tamanno;
	}

	public void agregarReinaEn(UbicacionXY u) {
		if (!(reinaExisteEn(u)))
			casillas[u.getCoordenadaX()][u.getCoordenadaY()] = 1;
	}

	public void quitarReinaEn(UbicacionXY u) {
		if (casillas[u.getCoordenadaX()][u.getCoordenadaY()] == 1) {
			casillas[u.getCoordenadaX()][u.getCoordenadaY()] = 0;
		}
	}

	/**
	 * Mueve la reina en la columna especificada en 
         * (valor-x de <code>u</code>) a la fila especificada
         * (valor-y de <code>u</code>). La accion asume una formulación de
         * estados completo en el problema de las n reinas.
	 * 
	 * @param u
	 */
	public void moverReinaA(UbicacionXY u) {
		for (int i = 0; i < tamanno; i++)
			casillas[u.getCoordenadaX()][i] = 0;
		casillas[u.getCoordenadaX()][u.getCoordenadaY()] = 1;
	}

	public void moverReina(UbicacionXY desde, UbicacionXY hacia) {
		if ((reinaExisteEn(desde)) && (!(reinaExisteEn(hacia)))) {
			quitarReinaEn(desde);
			agregarReinaEn(hacia);
		}
	}

	public boolean reinaExisteEn(UbicacionXY u) {
		return (reinaExisteEn(u.getCoordenadaX(), u.getCoordenadaY()));
	}

	private boolean reinaExisteEn(int x, int y) {
		return (casillas[x][y] == 1);
	}

	public int getNumeroDeReinasEnTablero() {
		int contador = 0;
		for (int i = 0; i < tamanno; i++) {
			for (int j = 0; j < tamanno; j++) {
				if (casillas[i][j] == 1)
					contador++;
			}
		}
		return contador;
	}

	public List<UbicacionXY> getPosicionesDeReina() {
		ArrayList<UbicacionXY> resultado = new ArrayList<UbicacionXY>();
		for (int i = 0; i < tamanno; i++) {
			for (int j = 0; j < tamanno; j++) {
				if (reinaExisteEn(i, j))
					resultado.add(new UbicacionXY(i, j));
			}
		}
		return resultado;

	}

	public int getNumeroDeParesAtacandose() {
		int resultado = 0;
		for (UbicacionXY ubicacion : getPosicionesDeReina()) {
			resultado += getNumeroDeAtaquesOn(ubicacion);
		}
		return resultado / 2;
	}

	public int getNumeroDeAtaquesOn(UbicacionXY u) {
		int x = u.getCoordenadaX();
		int y = u.getCoordenadaY();
		return numeroDeAtaquesHorizontalesEn(x, y)
				+ numeroDeAtaquesVerticalesEn(x, y)
				+ numeroDeAtaquesDiagonalesEn(x, y);
	}

	public boolean estaCasillaBajoAtaque(UbicacionXY u) {
		int x = u.getCoordenadaX();
		int y = u.getCoordenadaY();
		return (   estaCasillaAtacadaHorizontalmente(x, y)
			|| estaCasillaAtacadaVerticalmente(x, y) 
                        || estaCasillaAtacadaDiagonalmente(x, y));
	}

	private boolean estaCasillaAtacadaHorizontalmente(int x, int y) {
		return numeroDeAtaquesHorizontalesEn(x, y) > 0;
	}

	private boolean estaCasillaAtacadaVerticalmente(int x, int y) {
		return numeroDeAtaquesVerticalesEn(x, y) > 0;
	}

	private boolean estaCasillaAtacadaDiagonalmente(int x, int y) {
		return numeroDeAtaquesDiagonalesEn(x, y) > 0;
	}

	private int numeroDeAtaquesHorizontalesEn(int x, int y) {
		int valDev = 0;
		for (int i = 0; i < tamanno; i++) {
			if ((reinaExisteEn(i, y)))
				if (i != x)
					valDev++;
		}
		return valDev;
	}

	private int numeroDeAtaquesVerticalesEn(int x, int y) {
		int valDev = 0;
		for (int j = 0; j < tamanno; j++) {
			if ((reinaExisteEn(x, j)))
				if (j != y)
					valDev++;
		}
		return valDev;
	}

	private int numeroDeAtaquesDiagonalesEn(int x, int y) {
		int valDev = 0;
		int i;
		int j;
		// adelante hacia arriba diagonal
		for (i = (x + 1), j = (y - 1); (i < tamanno && (j > -1)); i++, j--) {
			if (reinaExisteEn(i, j))
				valDev++;
		}
		// adelante hacia abajo diagonal 
		for (i = (x + 1), j = (y + 1); ((i < tamanno) && (j < tamanno)); i++, j++) {
			if (reinaExisteEn(i, j))
				valDev++;
		}
		// hacia atras diagonal hacia arriba
		for (i = (x - 1), j = (y - 1); ((i > -1) && (j > -1)); i--, j--) {
			if (reinaExisteEn(i, j))
				valDev++;
		}

		// hacia atras diagonal hacia abajo
		for (i = (x - 1), j = (y + 1); ((i > -1) && (j < tamanno)); i--, j++) {
			if (reinaExisteEn(i, j))
				valDev++;
		}

		return valDev;
	}

	@Override
	public int hashCode() {
		List<UbicacionXY> ubics = getPosicionesDeReina();
		int resultado = 17;
		for (UbicacionXY ubic : ubics) {
			resultado = 37 * ubic.hashCode();
		}
		return resultado;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (this.getClass() != o.getClass()))
			return false;
		TableroNReinas unTablero = (TableroNReinas) o;
		boolean retVal = true;
		List<UbicacionXY> ubics = getPosicionesDeReina();

		for (UbicacionXY ubic : ubics) {
			if (!(unTablero.reinaExisteEn(ubic)))
				retVal = false;
		}
		return retVal;
	}

	public void print() {
		System.out.println(getImagenTablero());
	}

	public String getImagenTablero() {
		StringBuffer buffer = new StringBuffer();
		for (int fila = 0; (fila < tamanno); fila++) { // fila
			for (int col = 0; (col < tamanno); col++) { // columna
				if (reinaExisteEn(col, fila))
					buffer.append(" R ");
				else
					buffer.append(" - ");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int fila = 0; fila < tamanno; fila++) { // filas
			for (int col = 0; col < tamanno; col++) { // columnas
				if (reinaExisteEn(col, fila))
					buf.append('R');
				else
					buf.append('-');
			}
			buf.append("\n");
		}
		return buf.toString();
	}
}