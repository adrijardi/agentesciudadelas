package comportamientosGeneric;

import conceptos.Jugador;
import conceptos.Personaje;
import acciones.DestruirDistrito;
import acciones.ElegirPersonaje;
import acciones.OfertarPersonajes;
import tablero.AgTablero;
import tablero.EstadoPartida;
import utils.Filtros;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jugador.AgJugador;

public class ElegirPersonajeJugador extends Behaviour {

	private final AgJugador _agj;

	public ElegirPersonajeJugador(AgJugador agJugador) {
		_agj = agJugador;
	}
	
	@Override
	public void action() {
		block();
		/*
		 * a la espera de q llege el mensaje del agente tablero
		 */
		EstadoPartida ep = EstadoPartida.getInstance();
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.ELEGIR_PERSONAJE);
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.receive(plantilla);
		
		try {
			OfertarPersonajes contenido = (OfertarPersonajes) myAgent.getContentManager().extractContent(msg);
			Personaje seleccionado = (Personaje)contenido.getPersonajes().get(0); // Se selecciona el primer personaje que llega
			
			ElegirPersonaje salida = new ElegirPersonaje();
			Jugador yo = new Jugador();
			yo.setNombre(_agj.getName());
			salida.setJugador(yo);
			salida.setPersonaje(seleccionado);
			
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setSender(_agj.getAID());
			msgEnviar.setOntology(_agj.getOnto().ELEGIRPERSONAJE);
			myAgent.getContentManager().fillContent(msgEnviar,contenido);
			myAgent.send(msgEnviar);
			
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

	@Override
	public boolean done() {
		return true;
	}

}
