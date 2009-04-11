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
import acciones.ElegirPersonaje;
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
		 * 1º enviar el mensaje al ag con la corona
		 * 2º me bloque hasta q recibo el mensaje de selecion de personaje
		 */
		ResumenJugador jugador = ep.getJugActual();
		
		// crear la oferta de personajes
		OfertarPersonajes op=new OfertarPersonajes();
		op.setDisponibles(ep.getPjDisponibles());
		op.setJugador(jugador.getJugador());
		// Se le manda la oferta al jugador
		agt.sendMSG(ACLMessage.REQUEST, jugador, op, Filtros.OFERTARPERSONAJES);
		//Se recibe la eleccion del jugador
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.ELEGIRPERSONAJE);
		ACLMessage msg = myAgent.blockingReceive(filtroIdentificador);		
		
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
