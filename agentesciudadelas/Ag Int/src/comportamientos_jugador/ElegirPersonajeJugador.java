package comportamientos_jugador;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.ElegirPersonaje;
import acciones.NotificarDescartados;
import acciones.OfertarPersonajes;
import conceptos.Personaje;

public class ElegirPersonajeJugador extends Behaviour {

	private final AgJugador _agj;
	private boolean fin;

	public ElegirPersonajeJugador(AgJugador agJugador) {
		_agj = agJugador;
		fin = false;
	}

	@Override
	public void action() {

		ACLMessage msgPersonajesDescartados = _agj.reciveBlockingMessage(
				Filtros.NOTIFICARDESCARTADOS, true, 100);
		if (msgPersonajesDescartados != null) {
			fin = true;
			_agj.addTurno();
			ACLMessage msg = _agj.reciveBlockingMessage(
					Filtros.OFERTARPERSONAJES, true);

			try {
				NotificarDescartados nd = (NotificarDescartados) _agj
						.getContentManager().extractContent(
								msgPersonajesDescartados);
				_agj.setPersonajesDescartados(nd.getDestapados());

				OfertarPersonajes contenido = (OfertarPersonajes) _agj
						.getContentManager().extractContent(msg);
				Personaje seleccionado = _agj.selectPersonaje(contenido);

				ElegirPersonaje salida = new ElegirPersonaje();
				salida.setJugador(_agj.getJugador());
				salida.setPersonaje(seleccionado);
				_agj.sendMSG(ACLMessage.REQUEST, msg.getSender(), salida,
						Filtros.ELEGIRPERSONAJE);

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
		if (fin) {
			_agj.addBehaviour(new JugarPartida(_agj));
			_agj.setCambiarMano(new ActualizarCartas(_agj));
			_agj.addBehaviour(_agj.getCambiarMano());
		}
		return fin;
	}

}
