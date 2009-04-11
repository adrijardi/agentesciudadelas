package jugador;

import jade.lang.acl.ACLMessage;

import java.util.Random;

import acciones.NotificarFinTurnoJugador;
import acciones.OfertarPersonajes;
import conceptos.Personaje;

public class JugadorDani extends AgJugador {
	private Personaje pj_actual;

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		// Se selecciona un personaje aleatorio de los que llegan:
		int sel = new Random().nextInt(contenido.getDisponibles().size());
		pj_actual = (Personaje)contenido.getDisponibles().get(sel);
		return pj_actual;
	}

	@Override
	public NotificarFinTurnoJugador jugarTurno(ACLMessage msg) {
		//TODO faltan las acciones del jugador
		
		NotificarFinTurnoJugador ret = new NotificarFinTurnoJugador();
		ret.setJugador(getJugador());
		ret.setPersonaje(pj_actual);
		return ret;
	}

}
