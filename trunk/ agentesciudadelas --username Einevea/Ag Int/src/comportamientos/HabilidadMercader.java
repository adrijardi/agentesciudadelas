package comportamientos;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;
import acciones.CobrarMercader;
import acciones.CobrarPorMercader;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class HabilidadMercader extends Behaviour {

	private final AgTablero agt;

	public HabilidadMercader(AgTablero agTablero) {
		agt = agTablero;
	}
/*
 * se ha supuesto que es el propio agente quien no roba a quien han matado pero no estaria de mas controlarlo
 */
	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology("CobrarMercader");
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.receive(plantilla);
		if(msg!=null){
			
			ContentElement contenido = null;
			try {
				contenido=myAgent.getContentManager().extractContent(msg);
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
			// tengo el mensaje y su contenido, ahora a actualizar el estado actual
			
			CobrarMercader cb=(CobrarMercader)contenido;
			Jugador jg=cb.getJugador();
			ep.getResJugadorActual().setDinero(ep.getResJugadorActual().getDinero()+1);
			
			CobrarPorMercader cm=new CobrarPorMercader();
			cm.setJugador(jg);
			cm.setCantidad(1);
			
// hay q informar al resto del mundo del cambio	
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setOntology("CobrarPorMercader");
			msg.setSender(agt.getAID());
			try {
				myAgent.getContentManager().fillContent(msgEnviar, cm);
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // contenido es el objeto que envia
		}
		
	}

	@Override
	public boolean done() {
		return true;// siempre termina
	}
}