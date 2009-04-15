package comportamientos;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;
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
		ResumenJugador jugador = ep.getJugActual();
		
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.MATAR,jugador, 100);

		if(msg!=null){
			try {
				Matar contenido = (Matar)myAgent.getContentManager().extractContent(msg);
				
				// tengo el mensaje y su contenido, ahora a actualizar el estado actual
				
				Personaje personajeAsesinado = contenido.getPersonaje();
				ep.setNombreMuerto(personajeAsesinado);
				
				/* se ha cambiado el estado interno de la partida
				 * ahora se envia a todo el mundo el mensaje diciendo q personaje esta muerto, el contenido es el mismo que el del mensaje que hemos recibido
				 */
				agt.sendMSG(ACLMessage.REQUEST, jugador, contenido, Filtros.DARDISTRITOS);
				
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
		return true;// siempre termina
	}
}