

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.swing.JButton;

import core.agente.Accion;
import core.agente.Agente;
import core.agente.Entorno;
import core.agente.EstadoEntorno;
import core.agente.Percepcion;
import core.agente.impl.EntornoAbstracto;
import core.entorno.nreinas.HeuristicaParesAtacandose;
import core.entorno.nreinas.TableroNReinas;
import core.entorno.nreinas.NQueensFunctionFactory;
import core.entorno.nreinas.NQueensGoalTest;
import core.entorno.nreinas.QueenAction;

import core.busqueda.framework.AgenteDeBusqueda;
import core.busqueda.framework.FuncionAcciones;
import core.busqueda.framework.BusquedaDeGrafo;
import core.busqueda.framework.Problema;
import core.busqueda.framework.Busqueda;
import core.busqueda.framework.BusquedaDeArbol;

import core.busqueda.informada.AStarSearch;

import core.busqueda.local.HillClimbingSearch;
import core.busqueda.local.Scheduler;
import core.busqueda.local.SimulatedAnnealingSearch;

import core.busqueda.noinformada.BreadthFirstSearch;
import core.busqueda.noinformada.DepthFirstSearch;
import core.busqueda.noinformada.DepthLimitedSearch;
import core.busqueda.noinformada.IterativeDeepeningSearch;

import core.util.estructuradedatos.UbicacionXY;

import gui.framework.AgentAppController;
import gui.framework.AgentAppEnvironmentView;
import gui.framework.AgentAppFrame;
import gui.framework.MessageLogger;
import gui.framework.SimpleAgentApp;
import gui.framework.SimulationThread;

/**
 * Graphical n-queens game application. It demonstrates the performance of
 * different search algorithms. An incremental problem formulation is supported
 * as well as a complete-state formulation. Additionally, the user can make
 * experiences with manual search.
 * 
 * @author Ruediger Lunde
 */
public class ApNReinas extends SimpleAgentApp {

	/** List of supported search algorithm names. */
	protected static List<String> SEARCH_NAMES = new ArrayList<String>();
	/** List of supported search algorithms. */
	protected static List<Busqueda> SEARCH_ALGOS = new ArrayList<Busqueda>();

	/** Adds a new item to the list of supported search algorithms. */
	public static void addSearchAlgorithm(String name, Busqueda algo) {
		SEARCH_NAMES.add(name);
		SEARCH_ALGOS.add(algo);
	}

	static {
		addSearchAlgorithm("Depth First Search (Graph Search)",
				new DepthFirstSearch(new BusquedaDeGrafo()));
		addSearchAlgorithm("Breadth First Search (Tree Search)",
				new BreadthFirstSearch(new BusquedaDeArbol()));
		addSearchAlgorithm("Breadth First Search (Graph Search)",
				new BreadthFirstSearch(new BusquedaDeGrafo()));
		addSearchAlgorithm("Depth Limited Search (8)",
				new DepthLimitedSearch(8));
		addSearchAlgorithm("Iterative Deepening Search",
				new IterativeDeepeningSearch());
		addSearchAlgorithm("A* search (attacking pair heuristic)",
				new AStarSearch(new BusquedaDeGrafo(),
						new HeuristicaParesAtacandose()));
		addSearchAlgorithm("Hill Climbing Search", new HillClimbingSearch(
				new HeuristicaParesAtacandose()));
		addSearchAlgorithm("Simulated Annealing Search",
				new SimulatedAnnealingSearch(new HeuristicaParesAtacandose(),
						new Scheduler(20, 0.045, 1000)));
	}

	/** Returns a <code>NQueensView</code> instance. */
	public AgentAppEnvironmentView createEnvironmentView() {
		return new NQueensView();
	}

	/** Returns a <code>NQueensFrame</code> instance. */
	@Override
	public AgentAppFrame createFrame() {
		return new NQueensFrame();
	}

	/** Returns a <code>NQueensController</code> instance. */
	@Override
	public AgentAppController createController() {
		return new NQueensController();
	}

	// ///////////////////////////////////////////////////////////////
	// main method

	/**
	 * Starts the application.
	 */
	public static void main(String args[]) {
		new ApNReinas().startApplication();
	}

	// ///////////////////////////////////////////////////////////////
	// some inner classes

	/**
	 * Adds some selectors to the base class and adjusts its size.
	 */
	protected static class NQueensFrame extends AgentAppFrame {
		private static final long serialVersionUID = 1L;
		public static String ENV_SEL = "EnvSelection";
		public static String PROBLEM_SEL = "ProblemSelection";
		public static String SEARCH_SEL = "SearchSelection";

		public NQueensFrame() {
			setTitle("N-Queens Application");
			setSelectors(new String[] { ENV_SEL, PROBLEM_SEL, SEARCH_SEL },
					new String[] { "Select Environment",
							"Select Problem Formulation", "Select Search" });
			setSelectorItems(ENV_SEL, new String[] { "4 Queens", "8 Queens",
					"16 Queens", "32 Queens" }, 1);
			setSelectorItems(PROBLEM_SEL, new String[] { "Incremental",
					"Estado completo" }, 0);
			setSelectorItems(SEARCH_SEL, (String[]) SEARCH_NAMES
					.toArray(new String[] {}), 0);
			setEnvView(new NQueensView());
			setSize(800, 600);
		}
	}

	/**
	 * Displays the informations provided by a <code>NQueensEnvironment</code>
	 * on a panel.
	 */
	protected static class NQueensView extends AgentAppEnvironmentView
			implements ActionListener {
		private static final long serialVersionUID = 1L;
		protected JButton[] squareButtons;
		protected int currSize = -1;

		protected NQueensView() {
		}

		@Override
		public void setEnvironment(Entorno env) {
			super.setEnvironment(env);
			showState();
		}

		/** Agent value null indicates a user initiated action. */
		@Override
		public void agentActed(Agente agent, Accion action,
				EstadoEntorno resultingState) {
			showState();
			notify((agent == null ? "User: " : "") + action.toString());
		}

		@Override
		public void agentAdded(Agente agent, EstadoEntorno resultingState) {
			showState();
		}

		/**
		 * Displays the board state by labeling and coloring the square buttons.
		 */
		protected void showState() {
			TableroNReinas board = ((NQueensEnvironment) env).getBoard();
			if (currSize != board.getTamanno()) {
				currSize = board.getTamanno();
				removeAll();
				setLayout(new GridLayout(currSize, currSize));
				squareButtons = new JButton[currSize * currSize];
				for (int i = 0; i < currSize * currSize; i++) {
					JButton square = new JButton("");
					square.setMargin(new Insets(0, 0, 0, 0));
					square
							.setBackground((i % currSize) % 2 == (i / currSize) % 2 ? Color.WHITE
									: Color.LIGHT_GRAY);
					square.addActionListener(this);
					squareButtons[i] = square;
					add(square);
				}
			}
			for (int i = 0; i < currSize * currSize; i++)
				squareButtons[i].setText("");
			Font f = new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, Math.min(
					getWidth(), getHeight())
					* 3 / 4 / currSize);
			for (UbicacionXY loc : board.getPosicionesDeReina()) {
				JButton square = squareButtons[loc.getCoordenadaX()
						+ loc.getCoordenadaY() * currSize];
				square.setForeground(board.estaCasillaBajoAtaque(loc) ? Color.RED
						: Color.BLACK);
				square.setFont(f);
				square.setText("Q");
			}
			validate();
		}

		/**
		 * When the user presses square buttons the board state is modified
		 * accordingly.
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			for (int i = 0; i < currSize * currSize; i++) {
				if (ae.getSource() == squareButtons[i]) {
					NQueensController contr = (NQueensController) getController();
					UbicacionXY loc = new UbicacionXY(i % currSize, i / currSize);
					contr.modifySquare(loc);
				}
			}
		}
	}

	/**
	 * Defines how to react on standard simulation button events.
	 */
	protected static class NQueensController extends AgentAppController {

		protected NQueensEnvironment env = null;
		protected AgenteDeBusqueda agent = null;
		protected boolean boardDirty;

		/** Prepares next simulation. */
		@Override
		public void clear() {
			prepare(null);
		}

		/**
		 * Creates an n-queens environment and clears the current search agent.
		 */
		@Override
		public void prepare(String changedSelector) {
			AgentAppFrame.SelectionState selState = cuadro.getSelection();
			TableroNReinas board = null;
			switch (selState.getIndex(NQueensFrame.ENV_SEL)) {
			case 0: // 4 x 4 board
				board = new TableroNReinas(4);
				break;
			case 1: // 8 x 8 board
				board = new TableroNReinas(8);
				break;
			case 2: // 8 x 8 board
				board = new TableroNReinas(16);
				break;
			case 3: // 32 x 32 board
				board = new TableroNReinas(32);
				break;
			}
			env = new NQueensEnvironment(board);
			if (selState.getIndex(NQueensFrame.PROBLEM_SEL) == 1)
				for (int i = 0; i < board.getTamanno(); i++)
					board.agregarReinaEn(new UbicacionXY(i, 0));
			boardDirty = false;
			agent = null;
			cuadro.getEnvView().setEnvironment(env);
		}

		/**
		 * Creates a new search agent and adds it to the current environment if
		 * necessary.
		 */
		protected void addAgent() throws Exception {
			if (agent != null && agent.isDone()) {
				env.eliminarAgent(agent);
				agent = null;
			}
			if (agent == null) {
				int pSel = cuadro.getSelection().getIndex(
						NQueensFrame.PROBLEM_SEL);
				int sSel = cuadro.getSelection().getIndex(
						NQueensFrame.SEARCH_SEL);
				FuncionAcciones af;
				if (pSel == 0)
					af = NQueensFunctionFactory.getIActionsFunction();
				else
					af = NQueensFunctionFactory.getCActionsFunction();
				Problema problem = new Problema(env.getBoard(), af,
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				Busqueda search = SEARCH_ALGOS.get(sSel);
				agent = new AgenteDeBusqueda(problem, search);
				env.agregarAgent(agent);
			}
		}

		/** Checks whether simulation can be started. */
		@Override
		public boolean isPrepared() {
			int problemSel = cuadro.getSelection().getIndex(
					NQueensFrame.PROBLEM_SEL);
			return problemSel == 1
					|| (agent == null || !agent.isDone())
					&& (!boardDirty || env.getBoard()
							.getNumeroDeReinasEnTablero() == 0);
		}

		/** Starts simulation. */
		@Override
		public void run(MessageLogger logger) {
			logger.log("<simulation-log>");
			try {
				addAgent();
				while (!agent.isDone() && !cuadro.simulationPaused()) {
					Thread.sleep(200);
					env.paso();
				}
			} catch (InterruptedException e) {
				// nothing to do...
			} catch (Exception e) {
				e.printStackTrace(); // probably search has failed...
			}
			logger.log(getStatistics());
			logger.log("</simulation-log>\n");
		}

		/** Executes one simulation paso. */
		@Override
		public void step(MessageLogger logger) {
			try {
				addAgent();
				env.paso();
			} catch (Exception e) {
				e.printStackTrace(); // probably search has failed...
			}
		}

		/** Updates the status of the frame after simulation has finished. */
		public void update(SimulationThread simulationThread) {
			if (simulationThread.isCanceled()) {
				cuadro.setStatus("Task canceled.");
			} else if (cuadro.simulationPaused()) {
				cuadro.setStatus("Task paused.");
			} else {
				cuadro.setStatus("Task completed.");
			}
		}

		/** Provides a text with statistical information about the last run. */
		private String getStatistics() {
			StringBuffer result = new StringBuffer();
			Properties properties = agent.getInstrumentation();
			Iterator<Object> keys = properties.keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String property = properties.getProperty(key);
				result.append("\n" + key + " : " + property);
			}
			return result.toString();
		}

		public void modifySquare(UbicacionXY loc) {
			boardDirty = true;
			String atype;
			if (env.getBoard().reinaExisteEn(loc))
				atype = QueenAction.REMOVE_QUEEN;
			else
				atype = QueenAction.PLACE_QUEEN;
			env.executeAction(null, new QueenAction(atype, loc));
			agent = null;
			cuadro.updateEnabledState();
		}
	}

	/** Simple environment maintaining just the current board state. */
	public static class NQueensEnvironment extends EntornoAbstracto {
		TableroNReinas board;

		public NQueensEnvironment(TableroNReinas board) {
			this.board = board;
		}

		public TableroNReinas getBoard() {
			return board;
		}

		/**
		 * Executes the provided action and returns null.
		 */
		@Override
		public EstadoEntorno executeAction(Agente agent, Accion action) {
			if (action instanceof QueenAction) {
				QueenAction act = (QueenAction) action;
				UbicacionXY loc = new UbicacionXY(act.getX(), act.getY());
				if (act.getName() == QueenAction.PLACE_QUEEN)
					board.agregarReinaEn(loc);
				else if (act.getName() == QueenAction.REMOVE_QUEEN)
					board.quitarReinaEn(loc);
				else if (act.getName() == QueenAction.MOVE_QUEEN)
					board.moverReinaA(loc);
				if (agent == null)
					updateEnvironmentViewsAgentActed(agent, action, null);
			}
			return null;
		}

		/** Returns null. */
		@Override
		public EstadoEntorno getCurrentState() {
			return null;
		}

		/** Returns null. */
		@Override
		public Percepcion getPerceptSeenBy(Agente anAgent) {
			return null;
		}
	}
}
