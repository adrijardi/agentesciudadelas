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
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.DecirEstado;
import acciones.DestruirDistrito;
import acciones.ObtenerDistritos;
import acciones.ObtenerMonedas;
import acciones.PedirDistritoJugadores;
import conceptos.Distrito;
import conceptos.Jugador;

public class DestruirDistritoJugador extends Behaviour {
	
	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public DestruirDistritoJugador(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		
		DecirEstado de = new DecirEstado();
		de.setJugador(_agj.getJugador());
		_agj.sendMSG(ACLMessage.REQUEST, raid, de, Filtros.PEDIRRESUMENESJUGADORES);
		
		/* ahora me quedo bloqueado esperando q me llege el mensaje con el resumen de los jugadres */
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.DARRESUMENESJUGADORES, false);
		
		if(msg!=null){
			PedirDistritoJugadores pd=null;
			try {
				pd=(PedirDistritoJugadores)_agj.getContentManager().extractContent(msg);
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
			
			DestruirDistrito dd= new DestruirDistrito();
			/*FALLO! no se si el jugador es el condotiero y deberia */
			completarDetruirDistrito(pd, dd);
			
			
			_agj.sendMSG(ACLMessage.REQUEST, raid, dd, Filtros.DESTRUIRDISTRITO);		
		}
		
		
	}

	private void completarDetruirDistrito(PedirDistritoJugadores pd,
			DestruirDistrito dd) {
		int num=(int)(Math.random()*3);
		int aux=0;
		int dinero=0;
		switch (num) {
		case 0:
			dd.setJugador(pd.getJugador1());
			dinero=pd.getJugador1().getMonedas();
			aux=(int)(Math.random()*pd.getDistritos1().size());
			dd.setDistrito((Distrito)(pd.getDistritos1().get(aux)));
			break;
		case 1:
			dd.setJugador(pd.getJugador2());
			dinero=pd.getJugador2().getMonedas();
			aux=(int)(Math.random()*pd.getDistritos2().size());
			dd.setDistrito((Distrito)(pd.getDistritos2().get(aux)));
			break;
		case 2:
			dd.setJugador(pd.getJugador3());
			dinero=pd.getJugador3().getMonedas();
			aux=(int)(Math.random()*pd.getDistritos3().size());
			dd.setDistrito((Distrito)(pd.getDistritos3().get(aux)));
			break;
		default:
			break;
		}
		if(dinero>=(dd.getDistrito().getCoste()-1)){
			dd.setPago(dd.getDistrito().getCoste()-1);
		}else{
			dd.setPago(-1);
		}
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}
}