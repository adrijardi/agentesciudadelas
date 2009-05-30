package comportamientos_jugador;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.CambiarMano;
import acciones.CartasJugadores;
import conceptos.Jugador;

public class CambiarCartasJugador extends Behaviour {

	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public CambiarCartasJugador(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	
	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		
		CambiarMano de = new CambiarMano();
		de.setJugador(ep.getJugActual().getJugador());
		_agj.sendMSG(ACLMessage.REQUEST, raid, de, Filtros.PEDIRCARTASJUGADORES);
		
		/* ahora me quedo bloqueado esperando q me llege el mensaje con el resumen de los jugadres */
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.ENTREGARCARTAS, false);
		
		if(msg!=null){
			CartasJugadores cm=null;
			try {
				cm=(CartasJugadores)_agj.getContentManager().extractContent(msg);
				Jugador j = _agj.seleccionarJugadorCambiarCartas(cm.getJugador1(), cm.getJugador2(), cm.getJugador3());
				if(j == null)
					j = _agj.getJugador();
				de.setJugador(j);
				_agj.sendMSG(ACLMessage.REQUEST, raid, de, Filtros.CAMBIARMANO);
				msg = _agj.reciveBlockingMessage(Filtros.NOTIFICARMANO, false);
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
		_agj.addBehaviour(beh);
		return true;
	}

}
