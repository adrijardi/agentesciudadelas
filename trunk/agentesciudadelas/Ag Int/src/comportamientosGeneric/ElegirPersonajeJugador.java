package comportamientosGeneric;

import conceptos.Jugador;
import conceptos.Personaje;
import acciones.ElegirPersonaje;
import acciones.OfertarPersonajes;
import tablero.EstadoPartida;
import utils.Filtros;
import jade.content.ContentElement;
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
		System.err.println("Entra en ElegirPersonajeJugador");
		
		EstadoPartida ep = EstadoPartida.getInstance();
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.OFERTARPERSONAJES);
		//MessageTemplate filtroEmisor = MessageTemplate.MatchSender(_agj.);
		//MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.blockingReceive(filtroIdentificador);
		
		System.out.println("<Jugador> "+msg);
		try {
			myAgent.getContentManager().extractContent(msg);
			/*
			//Personaje seleccionado = (Personaje)contenido.getDisponibles().get(0); // Se selecciona el primer personaje que llega
			
			ElegirPersonaje salida = new ElegirPersonaje();
			Jugador yo = new Jugador();
			yo.setNombre(_agj.getName());
			salida.setJugador(yo);
			//salida.setPersonaje(seleccionado);
			
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setSender(_agj.getAID());
			msgEnviar.setOntology(_agj.getOnto().ELEGIRPERSONAJE);
			myAgent.getContentManager().fillContent(msgEnviar,contenido);

			myAgent.send(msgEnviar);*/
			
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
