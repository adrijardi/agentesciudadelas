package comportamientos;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import acciones.DarDistritos;
import acciones.DarMonedas;
import conceptos.Distrito;
import jade.content.ContentElement;
import jade.content.abs.AbsContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.Mazo;
import utils.Filtros;

public class EleccionCartasODinero extends Behaviour {
	/*
	 * Lo mejor seria definir un mensaje para pedir cobrar el distrito del rey y que sea lo q este espera					
	 */
	
	private final AgTablero agt;

	public EleccionCartasODinero(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		Filtros filtros = new Filtros();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(filtros.accionJugador);
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
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setSender(agt.getAID());
			
			if(contenido instanceof DarDistritos){
				/*
				 * Preparar el mensaje de dar distritos
				 */
				Mazo ma=Mazo.getInstance();
				Distrito[] lista=new Distrito[2];
				lista[0]=ma.getDistrito();
				lista[1]=ma.getDistrito();
				DarDistritos obj=new DarDistritos();
				obj.setDistritos(lista);
				
				msgEnviar.setOntology(agt.getOnto().DARDISTRITOS);
				
				
				agt.addBehaviour(new EsperarDistrito(agt));
				
				try {
					myAgent.getContentManager().fillContent(msgEnviar, obj);
				} catch (CodecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OntologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // contenido es el objeto que envia
				
			}else{
				/*
				 * Preparar el mensaje de dar monedas
				 */
				DarMonedas obj=new DarMonedas();
				obj.setMonedas(2);
				msgEnviar.setOntology(agt.getOnto().DARMONEDAS);
				
				try {
					myAgent.getContentManager().fillContent(msgEnviar, obj);
				} catch (CodecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OntologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // contenido es el objeto que envia
			}
		}
	}

	@Override
	public boolean done() {
		return true;// siempre termina
	}
}