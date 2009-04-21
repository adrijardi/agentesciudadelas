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
import utils.TipoDistrito;
import acciones.CobrarDistritosRey;
import acciones.CobrarPorDistritos;
import acciones.DarMonedas;
import conceptos.Jugador;
public class CobrarRey extends Behaviour {
	/*
	 * Lo mejor seria definir un mensaje para pedir cobrar el distrito del rey y que sea lo q este espera					
	 */
	
	private final AgTablero agt;
	private boolean fin = false;

	public CobrarRey(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.COBRARDISTRITOSREY,jugador, 100);
		
		if(msg!=null){
			fin = true;
			try {
				CobrarDistritosRey contenido = (CobrarDistritosRey)myAgent.getContentManager().extractContent(msg);
				
				// tengo el mensaje y su contenido, ahora a actualizar el estado actual
				
				Jugador jg=contenido.getJugador();

				CobrarPorDistritos cb=new CobrarPorDistritos();
				cb.setJugador(jg);
				int numCartasNobles = ep.getJugActual().getNumCartasColor(TipoDistrito.NOBLE);
				cb.setCantidad(numCartasNobles);
				
				ep.getJugActual().setDinero(ep.getJugActual().getDinero()+numCartasNobles);

				DarMonedas obj=new DarMonedas();
				obj.setMonedas(numCartasNobles);
				agt.sendMSG(ACLMessage.REQUEST, jugador, obj, Filtros.DARMONEDAS);
				
				agt.sendMSG(ACLMessage.REQUEST, null, contenido, Filtros.COBRARPORDISTRITOS);
				
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
		return fin;// siempre termina
	}
}
