package comportamientos;

import java.util.Iterator;

import conceptos.Distrito;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.Mazo;
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.ObtenerDistritos;
import acciones.ObtenerMonedas;

public class EleccionCartasODinero extends Behaviour {
	
	private final AgTablero agt;
	private boolean fin = false;

	public EleccionCartasODinero(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		//ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.ACCION_JUGADOR, jugador, 100);
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.ACCION_JUGADOR,jugador, 100);
		if(msg!=null){
			System.out.println("Accion recibida: "+ fin);
			fin = true;
			try {
				ContentElement contenido =agt.getContentManager().extractContent(msg);
				
				if(contenido instanceof ObtenerDistritos){
					// Se obtienen los distritos que seleccionara el jugador
					Mazo ma=Mazo.getInstance();
					Distrito[] lista= ma.getDistritos(2);
					
					// Se le mandan los distritos al jugador
					DarDistritos obj=new DarDistritos();
					obj.setDistritos(lista);
					agt.sendMSG(ACLMessage.REQUEST, jugador, obj, Filtros.DARDISTRITOS);
					
					// Se espera a que el jugador seleccione una carta
					msg = agt.reciveBlockingMessageFrom(Filtros.DARDISTRITOS, jugador);
					
					DarDistritos discart = (DarDistritos)agt.getContentManager().extractContent(msg);
					Iterator it = discart.getDistritos().iterator();
					while(it.hasNext())
						ma.trashDistrito((Distrito)it.next());
					
				}else if(contenido instanceof ObtenerMonedas){
					// Se manda el mensaje con las monedas
					DarMonedas obj=new DarMonedas();
					obj.setMonedas(2);
					agt.sendMSG(ACLMessage.REQUEST, jugador, obj, Filtros.DARMONEDAS);
				}
				
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
		}else{
			System.out.println("Accion no recibida: "+fin);
		}
	}

	@Override
	public boolean done() {
		return fin;
	}
}