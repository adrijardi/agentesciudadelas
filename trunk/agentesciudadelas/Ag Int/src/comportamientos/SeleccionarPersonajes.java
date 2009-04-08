package comportamientos;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Vector;

import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import tablero.EstadoPartida.EnumFase;
import utils.Filtros;
import utils.Personajes;
import acciones.ElegirPersonaje;
import acciones.OfertarPersonajes;
import conceptos.Personaje;


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
		 * 1º enviar el mensaje al ag con la corona
		 * 2º me bloque hasta q recibo el mensaje de selecion de personaje
		 */
		ResumenJugador jugador = ep.getJugActual();

		
		ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
		msgEnviar.setSender(agt.getAID());
		msgEnviar.setLanguage(agt.getCodec().getName());
		msgEnviar.setOntology(agt.getOnto().getName());
		msgEnviar.setConversationId(Filtros.OFERTARPERSONAJES);
		msgEnviar.addReceiver(jugador.getIdentificador());
		
		
		// crear la oferta de personajes
		OfertarPersonajes op=new OfertarPersonajes();
		op.setDisponibles(ep.getPjDisponibles());
		op.setJugador(jugador.getJugador());
		
		try {
			agt.getContentManager().fillContent(msgEnviar,op);
			//System.out.println(msgEnviar);
			agt.send(msgEnviar);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.ELEGIRPERSONAJE);
		System.out.println("<<<<Tablero<<<<<Esperando eleccion");
		ACLMessage msg = myAgent.blockingReceive(filtroIdentificador);
		System.out.println("<<<<Tablero<<<<<Eleccion realizada");
		//System.out.println(msg);
		
		
		if (msg != null) {
			ElegirPersonaje contenido = null;
			try {
				contenido = (ElegirPersonaje)agt.getContentManager().extractContent(msg);
				
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
		if(ep.getFase() == EnumFase.JUGAR_RONDA){
			agt.addBehaviour(new JugarPersonaje(agt));
			return true;
		}else{
			return false;
		}
	}

}
