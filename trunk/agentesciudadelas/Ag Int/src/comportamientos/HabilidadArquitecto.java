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
import tablero.Mazo;
import acciones.DarDistritos;
import acciones.PedirDistritosArquitecto;
import conceptos.Distrito;
import conceptos.Jugador;

public class HabilidadArquitecto extends Behaviour{
	
	private final AgTablero agt;

	public HabilidadArquitecto(AgTablero agTablero) {
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
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().PEDIRDISTRITOSARQUITECTO);
		ACLMessage msg = myAgent.receive(filtroIdentificador);
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
			
			PedirDistritosArquitecto pd=(PedirDistritosArquitecto)contenido;
			Jugador j=pd.getJugador();
			
			Mazo m= Mazo.getInstance();
			Distrito[] d2={m.getDistrito(),m.getDistrito()};
			
			/* si el turno es el 5º es el condotiero asi q no puedo destruir su distrito */ 
			ep.getResumenJugador(j.getNombre()).anyadirCartaMano(d2[0]);
			ep.getResumenJugador(j.getNombre()).anyadirCartaMano(d2[1]);
			
			DarDistritos dd=new DarDistritos();
			dd.setDistritos(d2);
			
				
				
			/* se ha cambiado el estado interno de la partida
			 * ahora se envia a todo el mundo el mensaje diciendo q al jugador 'j' se le ha destruido un distrito 'd' por 'm' monedas
			 */
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setOntology(agt.getOnto().DARDISTRITOS);
			msgEnviar.addReceiver(ep.getResJugadorActual().getIdentificador());
			try {
				myAgent.getContentManager().fillContent(msgEnviar, dd);
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