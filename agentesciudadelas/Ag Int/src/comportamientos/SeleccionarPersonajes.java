package comportamientos;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import tablero.EstadoPartida.EnumFase;
import utils.Filtros;
import utils.Personajes;
import acciones.ElegirPersonaje;
import acciones.InfoPartida;
import acciones.OfertarPersonajes;

public class SeleccionarPersonajes extends Behaviour {

	private final AgTablero agt;
	private final EstadoPartida ep;

	public SeleccionarPersonajes(AgTablero agTablero) {
		agt = agTablero;
		ep = EstadoPartida.getInstance();
	}

	@Override
	public void action() {
		/*
		 * 1º enviar el mensaje al ag con la corona 2º me bloque hasta q recibo
		 * el mensaje de selecion de personaje
		 */
		ResumenJugador jugador = ep.getJugActual();

		// crear la oferta de personajes
		OfertarPersonajes op = new OfertarPersonajes();
		op.setDisponibles(ep.getPjDisponibles());
		op.setJugador(jugador.getJugador());
		// Se le manda la oferta al jugador
		agt.sendMSG(ACLMessage.REQUEST, jugador, op, Filtros.OFERTARPERSONAJES);
		// Se recibe la eleccion del jugador
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.ELEGIRPERSONAJE);
		
		// Notifica la informacion de la partida
		InfoPartida msgInfo = new InfoPartida();
		msgInfo.setJugador1(ep.getResumenJugador(0).getJugador());
		msgInfo.setJugador2(ep.getResumenJugador(1).getJugador());
		msgInfo.setJugador3(ep.getResumenJugador(2).getJugador());
		msgInfo.setJugador4(ep.getResumenJugador(3).getJugador());
		for (int i = 0; i < 4; i++) {
			if (ep.getResumenJugador(i).getJugo()) {
				switch (i) {
				case 0:
					msgInfo.setPersonaje1(ep.getResumenJugador(i)
							.getPersonaje());
					msgInfo.setJugoP1(true);
					break;
				case 1:
					msgInfo.setPersonaje2(ep.getResumenJugador(i)
							.getPersonaje());
					msgInfo.setJugoP2(true);
					break;
				case 2:
					msgInfo.setPersonaje3(ep.getResumenJugador(i)
							.getPersonaje());
					msgInfo.setJugoP3(true);
					break;
				case 3:
					msgInfo.setPersonaje4(ep.getResumenJugador(i)
							.getPersonaje());
					msgInfo.setJugoP4(true);
					break;
				}
			} else {
				switch (i) {
				case 0:
					msgInfo.setPersonaje1(Personajes.PREVIO.getPj());
					msgInfo.setJugoP1(false);
					break;
				case 1:
					msgInfo.setPersonaje2(Personajes.PREVIO.getPj());
					msgInfo.setJugoP2(false);
					break;
				case 2:
					msgInfo.setPersonaje3(Personajes.PREVIO.getPj());
					msgInfo.setJugoP3(false);
					break;
				case 3:
					msgInfo.setPersonaje4(Personajes.PREVIO.getPj());
					msgInfo.setJugoP4(false);
					break;
				}
			}
		}
		msgInfo.setDistritosJ1(ep.getResumenJugador(0).getConstruido2());
		msgInfo.setDistritosJ2(ep.getResumenJugador(1).getConstruido2());
		msgInfo.setDistritosJ3(ep.getResumenJugador(2).getConstruido2());
		msgInfo.setDistritosJ4(ep.getResumenJugador(3).getConstruido2());
		
		agt.sendMSG(ACLMessage.REQUEST, jugador, msgInfo, Filtros.INFOPARTIDA);
		
		
		ACLMessage msg = myAgent.blockingReceive(filtroIdentificador);

		if (msg != null) {
			ElegirPersonaje contenido = null;
			try {
				contenido = (ElegirPersonaje) agt.getContentManager()
						.extractContent(msg);

				// Guardamos el personaje escogido en el Resumen del jugador.
				jugador.setPersonaje(contenido.getPersonaje());

				ep.removePersonajeFromPjDisponibles(contenido.getPersonaje());
				ep.nextJugadorPorSeleccionPersonaje();

			} catch (UngroundedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean done() {
		if (ep.getFase() == EnumFase.JUGAR_RONDA) {
			agt.addBehaviour(new JugarPersonaje(agt));
			return true;
		} else {
			return false;
		}
	}

}
