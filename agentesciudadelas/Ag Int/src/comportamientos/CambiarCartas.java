package comportamientos;

import acciones.DarDistritos;
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

public class CambiarCartas extends Behaviour {
	/*
	 * Lo mejor seria definir un mensaje para pedir cobrar el distrito del rey y que sea lo q este espera					
	 */
	
	private final AgTablero agt;

	public CambiarCartas(AgTablero agTablero) {
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
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().DARDISTRITOS);
		MessageTemplate filtroPersonal = MessageTemplate.MatchConversationId(filtros.ACCION_MAGO);
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		plantilla = MessageTemplate.and(plantilla, filtroPersonal);
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
			
			DarDistritos dd=(DarDistritos)contenido;
			Mazo ma=Mazo.getInstance();
			int num=0;
			for(int i=0;i<dd.getDistritos().size();i++){
				ep.getResJugadorActual().quitarCartaMano((Distrito) dd.getDistritos().get(i));
				ma.trashDistrito((Distrito) dd.getDistritos().get(i));
				num++;
			}
			
			Distrito[] nuevos=new Distrito[num];
			for(int i=0;i<dd.getDistritos().size();i++){
				Distrito aux=ma.getDistrito();
				ep.getResJugadorActual().anyadirCartaMano(aux);
				nuevos[i]=aux;
			}
			DarDistritos ddN=new DarDistritos();
			ddN.setDistritos(nuevos);
			/*
			 * Ya esta cambiado, ahora a enviar el mensaje al Jugador
			 */
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setOntology(agt.getOnto().DARDISTRITOS);
			msgEnviar.setSender(agt.getAID());
			msgEnviar.setConversationId(filtros.ACCION_MAGO);
			
			try {
				myAgent.getContentManager().fillContent(msgEnviar, ddN);
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
		agt.removeBehaviour(new HabilidadCambiarMano(agt));
		return true;// siempre termina
	}
}