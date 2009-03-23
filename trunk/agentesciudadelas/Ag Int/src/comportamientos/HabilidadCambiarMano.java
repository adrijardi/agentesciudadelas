package comportamientos;

import acciones.CambiarMano;
import acciones.DarDistritos;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;
import conceptos.Distrito;
import conceptos.Jugador;

public class HabilidadCambiarMano extends Behaviour {
	/*
	 * Lo mejor seria definir un mensaje para pedir cobrar el distrito del rey y que sea lo q este espera					
	 */
	
	private final AgTablero agt;

	public HabilidadCambiarMano(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().CAMBIARMANO);
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
			
			CambiarMano cm=(CambiarMano)contenido;
			Jugador jg1=cm.getJugador1();
			Jugador jg2=cm.getJugador2();
			
			Jugador aux=new Jugador();
			
			DarDistritos dd1=new DarDistritos();
			DarDistritos dd2=new DarDistritos();
	

			dd2.setDistritos( ep.getResumenJugador(jg1.getNombre()).getCartasMano() );
			dd1.setDistritos( ep.getResumenJugador(jg2.getNombre()).getCartasMano() );
			
			ep.getResumenJugador(jg1.getNombre()).cambiarMano(ep.getResumenJugador(jg2.getNombre()));
			/*
			 * asi puesto son los propios agentes quienes tienen q comprobar que no es un mensaje para ellos, 
			 * eso se hace comparando su jugador con el que enviamos
			 */
			ACLMessage msgEnviarA1 = new ACLMessage(ACLMessage.REQUEST);
			msgEnviarA1.setOntology(agt.getOnto().SUSTITUIRMANO);
			msgEnviarA1.setSender(agt.getAID());
			msgEnviarA1.addReceiver( ep.getResumenJugador(jg1.getNombre()).getIdentificador() );
			
			ACLMessage msgEnviarA2 = new ACLMessage(ACLMessage.REQUEST);
			msgEnviarA2.setOntology(agt.getOnto().SUSTITUIRMANO);
			msgEnviarA2.setSender(agt.getAID());
			msgEnviarA2.addReceiver( ep.getResumenJugador(jg2.getNombre()).getIdentificador() );
			
			try {
				myAgent.getContentManager().fillContent(msgEnviarA1, dd1);
				myAgent.send(msgEnviarA1);
				myAgent.getContentManager().fillContent(msgEnviarA2, dd2);
				myAgent.send(msgEnviarA2);
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
		agt.removeBehaviour(new CambiarCartas(agt));
		return true;// siempre termina
	}
}
