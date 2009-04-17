package comportamientos_jugador;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.Iterator;

import jugador.AgJugador;

import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.Mazo;
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.CambiarMano;
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.DecirEstado;
import acciones.DestruirDistrito;
import acciones.ObtenerDistritos;
import acciones.ObtenerMonedas;
import acciones.PedirDistritoJugadores;
import conceptos.Distrito;
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
		de.setJugador1(_agj.getJugador());
		de.setJugador2(_agj.getJugador());
		_agj.sendMSG(ACLMessage.REQUEST, raid, de, Filtros.PEDIRCARTASJUGADORES);
		
		/* ahora me quedo bloqueado esperando q me llege el mensaje con el resumen de los jugadres */
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.ENTREGARCARTAS, false);
		
		if(msg!=null){
			CambiarMano cm=null;
			try {
				cm=(CambiarMano)_agj.getContentManager().extractContent(msg);
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
			
			
			
			_agj.sendMSG(ACLMessage.REQUEST, raid, de, Filtros.CAMBIARMANO);		
		}
		
		
	}


	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}