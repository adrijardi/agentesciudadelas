package acciones;

import conceptos.Jugador;
import jade.content.AgentAction;
import jade.util.leap.ArrayList;

public class OfertarPersonajes implements AgentAction {

	private Jugador jugador;
	//private int[] disponibles;

	public Jugador getJugador() {
		return jugador;
	}

	/*public void setDisponibles(jade.util.leap.List l){
		int[] disponibles2 = new int[l.size()];
		for (int i = 0; i < disponibles2.length; i++) {
			disponibles2[i] = (Integer)l.get(i);
		}
		disponibles = disponibles2;
	}

	public void setDisponibles(int[] d){
		disponibles = d;
	}

	public jade.util.leap.List getDisponibles(){
		ArrayList al = new ArrayList(disponibles.length);
		for (int i = 0; i < disponibles.length; i++) {
			al.add(i, disponibles[i]);
		}
		return al;
	}*/

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}
