package core.busqueda.framework;

import java.util.ArrayList;
import java.util.List;

import core.agente.Accion;
import core.agente.impl.AccionNoOp;

/**
 * @author Ravi Mohan
 * 
 */
public class UtilidadesDeBusqueda {

	public static List<Accion> actionsFromNodes(List<Nodo> nodeList) {
		List<Accion> actions = new ArrayList<Accion>();
		if (nodeList.size() == 1) {
			// I'm at the root node, this indicates I started at the
			// Goal node, therefore just return a NoOp
			actions.add(AccionNoOp.NO_OP);
		} else {
			// ignore the root node this has no action
			// hence index starts from 1 not zero
			for (int i = 1; i < nodeList.size(); i++) {
				Nodo node = nodeList.get(i);
				actions.add(node.getAction());
			}
		}
		return actions;
	}

	public static boolean isGoalState(Problema p, Nodo n) {
		boolean isGoal = false;
		PruebaDeMeta gt = p.getGoalTest();
		if (gt.isGoalState(n.getState())) {
			if (gt instanceof RevisorDeSolucion) {
				isGoal = ((RevisorDeSolucion) gt).isAcceptableSolution(UtilidadesDeBusqueda.actionsFromNodes(n.getPathFromRoot()),
						n.getState());
			} else {
				isGoal = true;
			}
		}
		return isGoal;
	}
}