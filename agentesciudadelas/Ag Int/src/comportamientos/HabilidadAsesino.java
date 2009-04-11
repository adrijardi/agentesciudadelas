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
import acciones.Matar;
import conceptos.Personaje;

public class HabilidadAsesino extends Behaviour {

	private final AgTablero agt;

	public HabilidadAsesino(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().MATAR);
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
			
			Matar mt=(Matar)contenido;
			Personaje pr=mt.getPersonaje();
			
			ep.setNombreMuerto(pr.getNombre());
			/* se ha cambiado el estado interno de la partida
			 * ahora se envia a todo el mundo el mensaje diciendo q personaje esta muerto, el contenido es el mismo que el del mensaje que hemos recibido
			 */
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setOntology(agt.getOnto().NOTIFICARASESINADO);
			try {
				myAgent.getContentManager().fillContent(msgEnviar, mt);
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

	@Override
	public boolean done() {
		return true;// siempre termina
	}
}