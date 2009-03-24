package comportamientos;

import conceptos.*;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import acciones.CobrarDistritos;
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.DestruirDistrito;
import acciones.PedirConstruirDistrito;

public class DestruirDistritoCondotiero extends Behaviour {

	private final AgTablero agt;

	public DestruirDistritoCondotiero(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el
		 * distrito
		 */

		EstadoPartida ep = EstadoPartida.getInstance();

		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt
				.getOnto().DESTRUIRDISTRITO);
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep
				.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor,
				filtroIdentificador);
		ACLMessage msg = myAgent.receive(plantilla);
		if (msg != null) {
			/* Se obtiene el jugador objetivo, el distrito */

			DestruirDistrito contenido = null;
			try {
				contenido = (DestruirDistrito) myAgent.getContentManager()
						.extractContent(msg);
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

			/* Se realizan las comprobaciones de la operación */
			Jugador jObjetivo = contenido.getJugador();
			Distrito distrito = contenido.getDistrito();
			int dinero = (Integer) contenido.getPago();
			if ((distrito.getCoste() - 1) != dinero) {
				System.out.println("Fallo, ese no es el dinero que necesitas para destuir el distrito.");

				if (ep.tieneDistrito(jObjetivo, distrito)) {
					ResumenJugador datosJugadorActual = ep
							.getResJugadorActual();
					if (distrito.getCoste() - 1 <= datosJugadorActual
							.getDinero()) {
						/*
						 * El jugador objetivo tiene el distrito y el jugador
						 * actual tiene dinero para pagar la destrucción
						 */

						// Se indica a todos los jagadores la acción
						// TODO imprementar
						// Se retira el dinero del jugador actual
						datosJugadorActual.setDinero(datosJugadorActual
								.getDinero()
								- distrito.getCoste() - 1);

						// Se retira el distrito del jugador objetivo
						ResumenJugador datosContrario = ep
								.getResumenJugador(jObjetivo.getNombre());
						datosContrario.quitarDistrito(distrito);
					}
					datosJugadorActual.setDinero(datosJugadorActual.getDinero()
							- dinero);
					// enviar informacion de la destruccion del distrito

					ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
					msgEnviar.setSender(agt.getAID());
					msgEnviar.setOntology(agt.getOnto().DESTRUIRDISTRITO);
					try {
						myAgent.getContentManager().fillContent(msgEnviar,
								contenido);
						myAgent.send(msgEnviar);
					} catch (CodecException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OntologyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // contenido es el objeto que envia
				}
			}
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return true;
	}

}
