package acciones;

import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;
import jade.content.AgentAction;
import jade.util.leap.ArrayList;

public class OfertarPersonajes implements AgentAction {

	private Jugador jugador;
	private Personaje [] personajes;

	public jade.util.leap.List getPersonajes() {
		ArrayList al = new ArrayList(personajes.length);
		for (int i = 0; i < personajes.length; i++) {
			al.add(i, personajes[i]);
		}
		return al;
	}

	public void setPersonajes(jade.util.leap.List l) {
		Personaje[] d = new Personaje[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Personaje)l.get(i);
		}
		personajes = d;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}
