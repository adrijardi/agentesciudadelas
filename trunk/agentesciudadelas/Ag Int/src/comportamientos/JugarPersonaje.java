package comportamientos;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;

import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;
import utils.Personajes;
import acciones.NotificarCorona;
import acciones.NotificarTurno;

public class JugarPersonaje extends Behaviour {

	private final AgTablero agt;
	private EstadoPartida ep = EstadoPartida.getInstance();
	private boolean primero;

	public JugarPersonaje(AgTablero agTablero) {
		agt = agTablero;
		primero = true;
	}

	@Override
	public void action() {
		// Se obtiene el siguiente jugador que juega
		ResumenJugador jugador = ep.getJugActual();
		System.out.println("TOLON!! TOLON!! "+ jugador.getPersonaje().getNombre());
		
		// Se notifica el nuevo jugador con la corona
		if (Personajes.REY.isPersonaje(jugador.getPersonaje())) {
			ep.setTieneCorona(jugador);
			NotificarCorona nc = new NotificarCorona();
			nc.setJugador(jugador.getJugador());
			agt.sendMSG(ACLMessage.INFORM, null, nc, Filtros.NOTIFICARCORONA);
		}

		// Se comprueba que no este muerto, si lo esta no se le envia el turno
		if (!jugador.equals(ep.getNombreMuerto())) {

			// Se añaden los comportamientos para que juege el jugador
			LinkedList<Behaviour> llb = new LinkedList<Behaviour>();
			/// Se añade la opcion de seleccionar cartas o dinero
			Behaviour beh = new EleccionCartasODinero(agt);
			agt.addBehaviour(beh);
			llb.add(beh);
			///Se añade la opcion de construir un Distrito
			beh = new ConstruirDistrito(agt);
			agt.addBehaviour(beh);
			llb.add(beh);
			
			beh = new FinalizarTurno(agt, llb);
			agt.addBehaviour(beh);

			// TODO falta añadir el comportamiento de cada personaje
			
			// Notifica el turno a un jugador
			NotificarTurno msgNotificar = new NotificarTurno();
			msgNotificar.setJugador(jugador.getJugador());
			msgNotificar.setPersonaje(jugador.getPersonaje());

			agt.sendMSG(ACLMessage.REQUEST, jugador, msgNotificar, Filtros.NOTIFICARTURNO);
		}
	}

	@Override
	public boolean done() {
		return true;
	}

}
