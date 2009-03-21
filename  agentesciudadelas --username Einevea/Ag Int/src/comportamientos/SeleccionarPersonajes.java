package comportamientos;

import tablero.AgTablero;
import jade.core.behaviours.Behaviour;

public class SeleccionarPersonajes extends Behaviour {

	private final AgTablero agt;

	public SeleccionarPersonajes(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
				
		// NotificarDescartados
		
			// 
	}

	@Override
	public boolean done() {
		return true;
	}

}
