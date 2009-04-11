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
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.ObtenerDistritos;
import acciones.ObtenerMonedas;
import conceptos.Distrito;

public class EleccionCartasODinero extends Behaviour {
	
	private final AgTablero agt;

	public EleccionCartasODinero(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		System.out.println("Esperando la accion del jugador "+jugador.getJugador().getNombre());
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.ACCION_JUGADOR, jugador);
		System.out.println("Accion recibida");
		if(msg!=null){
			try {
				ContentElement contenido =agt.getContentManager().extractContent(msg);
				
				if(contenido instanceof ObtenerDistritos){
					/*// Se obtienen los distritos que seleccionara el jugador
					Mazo ma=Mazo.getInstance();
					Distrito[] lista=new Distrito[2];
					lista[0]=ma.getDistrito();
					lista[1]=ma.getDistrito();
					DarDistritos obj=new DarDistritos();
					obj.setDistritos(lista);
					
					msgEnviar.setOntology(agt.getOnto().DARDISTRITOS);
					
					
					agt.addBehaviour(new EsperarDistrito(agt));
					
					try {
						myAgent.getContentManager().fillContent(msgEnviar, obj);
						myAgent.send(msgEnviar);
					} catch (CodecException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OntologyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // contenido es el objeto que envia*/
					
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
		}
	}

	@Override
	public boolean done() {
		return true;
	}
}