package comportamientos;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.CambiarMano;
import acciones.CartasJugadores;
import acciones.DarDistritos;

public class CambiarCartas extends Behaviour {
	/*
	 * Lo mejor seria definir un mensaje para pedir cobrar el distrito del rey y
	 * que sea lo q este espera
	 */

	private final AgTablero agt;
	private boolean fin = false;

	public CambiarCartas(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el
		 * distrito
		 */
		ACLMessage msg = agt.reciveBlockingMessage(Filtros.PEDIRCARTASJUGADORES, 100);
		if (msg != null) {
			fin = true;
			ContentElement contenido = null;
			try {
				contenido = myAgent.getContentManager().extractContent(msg);

				ResumenJugador jug[] = ep.getResumenJugadores();
				CartasJugadores cj = new CartasJugadores();
				int cont = 1;
				for (int i = 0; i < jug.length; i++) {
					if (!(jug[i].getIdentificador().getName().equals(ep
							.getJugActual()))) {
						cj.setJugador(jug[i].getJugador(), cont);
						cont++;
					}
				}

				/*
				 * Ya esta cambiado, ahora a enviar el mensaje al Jugador
				 */
				agt.sendMSG(ACLMessage.REQUEST, ep.getJugActual(), cj,
						Filtros.ENTREGARCARTAS);
				msg = agt.reciveBlockingMessage(
						Filtros.CAMBIARMANO);
				if (msg != null) {
					fin = true;
					contenido = null;
					contenido = myAgent.getContentManager().extractContent(msg);
					CambiarMano cm = (CambiarMano)contenido;
					ep.cambiarMano(cm.getJugador(), ep.getJugActual());
					DarDistritos obj = new DarDistritos();
					
					obj.setDistritos(ep.getJugActual().getCartasMano());
					agt.sendMSG(ACLMessage.INFORM, ep.getJugActual(), obj, Filtros.NOTIFICARMANO);
					obj = new DarDistritos();
					obj.setDistritos(ep.getResumenJugador(cm.getJugador()).getCartasMano());
					agt.sendMSG(ACLMessage.INFORM, ep.getResumenJugador(cm.getJugador()), obj, Filtros.NOTIFICARMANO);
				}
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
		return fin;
	}
}