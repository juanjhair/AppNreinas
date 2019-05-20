package core.util.estructuradedatos;

/**
 * Simplified version of <code>java.awt.geom.Point2D</code>. We do not want
 * dependencies to presentation layer packages here.
 * 
 * @author R. Lunde
 * @author Mike Stampone
 */
public class Punto2D {
	private double x;
	private double y;

	public Punto2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the X coordinate of this <code>Point2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the X coordinate of this <code>Point2D</code>.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the Y coordinate of this <code>Point2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the Y coordinate of this <code>Point2D</code>.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the Euclidean distance between a specified point and this point.
	 * 
	 * @return the Euclidean distance between a specified point and this point.
	 */
	public double distancia(Punto2D pt) {
		double resultado = (pt.getX() - x) * (pt.getX() - x);
		resultado += (pt.getY() - y) * (pt.getY() - y);
		return Math.sqrt(resultado);
	}
}
