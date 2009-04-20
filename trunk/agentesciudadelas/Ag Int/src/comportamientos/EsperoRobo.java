package comportamientos;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;

import acciones.Matar;
import acciones.Monedas;
import conceptos.Personaje;

import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;

public class EsperoRobo extends Behaviour {

	private final AgTablero agt;
	
	public EsperoRobo(AgTablero agt) {
		this.agt = agt;
	}

	@Override
	public void action() {
		/*
		 * espera a recibir el mensaje de la clase: PagoRobo y envia a CobroRobo 
		 */
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.PAGARROBO,jugador, 100);
		
		if(msg!=null){
			try {
				Monedas contenido = (Monedas)myAgent.getContentManager().extractContent(msg);
				
				// tengo el mensaje y su contenido, ahora a actualizar el estado actual
				ep.getJugLadron().setDinero(ep.getJugLadron().getDinero()+contenido.getDinero());
				ep.getJugActual().setDinero(0);
				
				/* se ha cambiado el estado interno de la partida
				 * ahora se envia a todo el mundo el mensaje diciendo q personaje esta muerto, el contenido es el mismo que el del mensaje que hemos recibido
				 */
				
				agt.sendMSG(ACLMessage.REQUEST, null, contenido, Filtros.COBRARROBO);
				
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
		return true;
	}

}