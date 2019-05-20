package core.util;

/**
 * Implements a thread with an additional flag indicating cancellation.
 * 
 * @author R. Lunde
 * @author Mike Stampone
 */
public class CancelableHilo extends Thread {

	/**
	 * Returns <code>true</code> if the current thread is canceled
	 * 
	 * @return <code>true</code> if the current thread is canceled
	 */
	public static boolean currIsCanceled() {
		if (Thread.currentThread() instanceof CancelableHilo)
			return ((CancelableHilo) Thread.currentThread()).isCancelado;
		return false;
	}

	private volatile boolean isCancelado;

	/**
	 * Returns <code>true</code> if this thread is canceled
	 * 
	 * @return <code>true</code> if this thread is canceled
	 */
	public boolean isCancelado() {
		return isCancelado;
	}

	/**
	 * Cancels this thread
	 */
	public void cancel() {
		isCancelado = true;
	}
}
