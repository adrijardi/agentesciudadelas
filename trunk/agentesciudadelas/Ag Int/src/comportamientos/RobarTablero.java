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
import acciones.NotificarRobado;
import acciones.Robar;
import conceptos.Personaje;

public class RobarTablero extends Behaviour {

	private final AgTablero agt;

	public RobarTablero(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.ROBAR,jugador, 100);

		if(msg!=null){
			try {
				Robar contenido = (Robar)myAgent.getContentManager().extractContent(msg);
				
				// tengo el mensaje y su contenido, ahora a actualizar el estado actual
				
				Personaje personajeRobado = contenido.getPersonaje();
				ep.setNombreRobado(personajeRobado);
				
				NotificarRobado nr=new NotificarRobado();
				nr.setPersonaje(personajeRobado);
				
				agt.sendMSG(ACLMessage.REQUEST, null, contenido, Filtros.NOTIFICARROBADO);
				
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