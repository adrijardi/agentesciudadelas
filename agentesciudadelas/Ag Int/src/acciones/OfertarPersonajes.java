package acciones;

import jade.content.AgentAction;
import jade.util.leap.ArrayList;
import conceptos.Jugador;
import conceptos.Personaje;

public class OfertarPersonajes implements AgentAction {

	private Jugador jugador;
	private Personaje[] disponibles;

	public Jugador getJugador() {
		return jugador;
	}

	public void setDisponibles(jade.util.leap.List l){
		disponibles = new Personaje[l.size()];
		for (int i = 0; i < disponibles.length; i++) {
			disponibles[i] = (Personaje)l.get(i);
		}
	}

	public void setDisponibles(Personaje[] d){
		disponibles = d;
	}

	public jade.util.leap.List getDisponibles(){
		jade.util.leap.List al = new ArrayList(disponibles.length);
		for (int i = 0; i < disponibles.length; i++) {
			al.add(i, disponibles[i]);
		}
		return al;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}
