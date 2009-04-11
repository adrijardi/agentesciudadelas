package jugador;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;
import java.util.Random;

import comportamientosGeneric.FinTurno;
import comportamientosGeneric.PedirMonedas;

import utils.Filtros;

import acciones.DarMonedas;
import acciones.NotificarFinTurnoJugador;
import acciones.ObtenerMonedas;
import acciones.OfertarPersonajes;
import conceptos.Personaje;

public class JugadorDani extends AgJugador {
	

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		// Se selecciona un personaje aleatorio de los que llegan:
		int sel = new Random().nextInt(contenido.getDisponibles().size());
		pj_actual = (Personaje)contenido.getDisponibles().get(sel);
		return pj_actual;
	}

	@Override
	public Behaviour jugarTurno(ACLMessage msg) {
		Behaviour ret;
		printEstado();
		
		//TODO faltan las acciones del jugador
		FinTurno ft = new FinTurno(this, msg.getSender());
		ret = new PedirMonedas(this, ft);
		return ft;
	}

}
