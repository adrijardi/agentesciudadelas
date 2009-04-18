package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import utils.Distritos;
import utils.Personajes;

import acciones.DestruirDistrito;
import acciones.OfertarPersonajes;
import acciones.PedirDistritoJugadores;

import comportamientos_jugador.AsesinarPersonaje;
import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.HabilidadArquitecto;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirCobrarCondotierro;
import comportamientos_jugador.PedirCobrarMercader;
import comportamientos_jugador.PedirCobrarObispo;
import comportamientos_jugador.PedirCobrarRey;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class JugadorDani extends AgJugador {
	private final Random dado = new Random();

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		// Se selecciona un personaje aleatorio de los que llegan:
		int sel = dado.nextInt(contenido.getDisponibles().size());
		pj_actual = (Personaje) contenido.getDisponibles().get(sel);
		return pj_actual;
	}

	@Override
	public Behaviour jugarTurno(ACLMessage msg) {
		Behaviour ret;
		printEstado();
		msg_sender = msg.getSender();

		// TODO faltan las acciones del jugador
		ret = new FinTurno(this, msg_sender);

		// Construir distrito
		ret = new ConstruirDistritoJugador(this, ret, msg_sender);

		// Accion jugador falta PedirCartas
		if (seleccionarMonedasOCartas()) {
			ret = new PedirCartas(this, ret, msg_sender);
		} else {
			ret = new PedirMonedas(this, ret, msg_sender);
		}

		switch (Personajes.getPersonajeByPJ(pj_actual)) {
		case ASESINO:
			ret = new AsesinarPersonaje(this, ret, msg_sender);
			break;
		case LADRON:

			break;
		case MAGO:

			break;
		case REY:
			ret = new PedirCobrarRey(this, ret, msg_sender);
			break;
		case OBISPO:
			ret = new PedirCobrarObispo(this, ret, msg_sender);
			break;
		case MERCADER:
			ret = new PedirCobrarMercader(this, ret, msg_sender);
			break;
		case ARQUITECTO:
			ret = new HabilidadArquitecto(this, ret, msg_sender);
			break;
		case CONDOTIERO:
			ret = new PedirCobrarCondotierro(this, ret, msg_sender);
			break;
		}

		return ret;
	}

	@Override
	public Distrito getDistritoConstruir() {
		Distrito ret = null;
		Distrito[] dist = getDistritosConstruibles();
		if (dist != null && dist.length > 0) {
			ret = dist[dado.nextInt(dist.length)];
		}
		return ret;
	}

	@Override
	public Distrito[] descartaDistritos(List distritos) {
		Distrito[] descartado = new Distrito[distritos.size() - 1];
		int selecc = dado.nextInt(distritos.size());
		int i = 0;
		Iterator it = distritos.iterator();
		Distrito d;
		while (it.hasNext()) {
			d = (Distrito) it.next();
			if (i == selecc) {
				mano.add(d);
				selecc = -1;
			} else {
				descartado[i++] = d;
			}
		}
		return descartado;
	}

	@Override
	public boolean seleccionarMonedasOCartas() {
		if (cartasManoNoConstruidas() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public Personaje getPersonajeMatar() {
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i]);
			System.out.println(destapados[i]); // TODO quitar
		}
		return llp.get(dado.nextInt(llp.size()));
	}

	@Override
	public Jugador seleccionarJugadorCambiarCartas(Jugador jug1, Jugador jug2,
			Jugador jug3) {
		Jugador ret = null;
		LinkedList<Jugador> jugadores = new LinkedList<Jugador>();
		if (jug1.getMano() > 0)
			jugadores.add(jug1);
		if (jug2.getMano() > 0)
			jugadores.add(jug1);
		if (jug3.getMano() > 0)
			jugadores.add(jug1);
		if (jugadores.size() > 0) {
			ret = jugadores.get(dado.nextInt(jugadores.size()));
		}
		return ret;
	}

	@Override
	public void getDistritoDestruir(PedirDistritoJugadores pd,
			DestruirDistrito dd) {
		Distrito basura = Distritos.TABERNA.getDistrito();
		dd.setJugador(pd.getJugador1());
		dd.setDistrito(basura);
		dd.setPago(-1);
	}

}
