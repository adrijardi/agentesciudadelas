package comportamientos;

import tablero.AgTablero;
import tablero.EstadoPartida;
import utils.Personajes;
import acciones.DestruirDistrito;
import acciones.NotificarRobado;
import acciones.Robar;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HabilidadCondotiero extends Behaviour{
	
	private final AgTablero agt;

	public HabilidadCondotiero(AgTablero agTablero) {
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
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().DESTRUIRDISTRITO);
		
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
			
			DestruirDistrito dd=(DestruirDistrito)contenido;
			Distrito d=dd.getDistrito();
			Jugador j=dd.getJugador();
			int m=dd.getPago();
			
			if(ep.getResumenJugador(j.getNombre()).getPersonaje().getTurno()!=5){ 
				/* si el turno es el 5º es el condotiero asi q no puedo destruir su distrito */ 
				ep.getResumenJugador(j.getNombre()).quitarDistrito(d);
				ep.getResJugadorActual().setDinero(ep.getResJugadorActual().getDinero()-m);
				
				
				/* se ha cambiado el estado interno de la partida
				 * ahora se envia a todo el mundo el mensaje diciendo q al jugador 'j' se le ha destruido un distrito 'd' por 'm' monedas
				 */
				ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
				msgEnviar.setOntology(agt.getOnto().DESTRUIRDISTRITO);
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
		
	}

	@Override
	public boolean done() {
		return true;// siempre termina
	}

}
