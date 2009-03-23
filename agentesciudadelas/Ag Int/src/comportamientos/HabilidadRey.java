package comportamientos;

import acciones.NotificarCorona;
import conceptos.Jugador;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;

public class HabilidadRey extends Behaviour {

	private final AgTablero agt;

	public HabilidadRey(AgTablero agTablero) {
		agt = agTablero;
	}
/*
 * se ha supuesto que es el propio agente quien no roba a quien han matado pero no estaria de mas controlarlo
 */
	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		
		ep.setCorona(ep.getJugActual());
		NotificarCorona nt=new NotificarCorona();
		Jugador jg = new Jugador();
		jg.setNombre(ep.getResJugadorActual().getIdentificador().getName());
		jg.setMano(ep.getResJugadorActual().getCartasMano().length);
		jg.setMonedas(ep.getResJugadorActual().getDinero());
		jg.setPuntos(ep.getResJugadorActual().getPuntos());
		nt.setJugador(jg);
			
		ACLMessage msgEnviar = new ACLMessage(ACLMessage.INFORM);
		msgEnviar.setOntology("NotificarCorona");
		try {
			myAgent.getContentManager().fillContent(msgEnviar, nt);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // contenido es el objeto que envia

		
	}

	@Override
	public boolean done() {
		return true;// siempre termina
	}
}