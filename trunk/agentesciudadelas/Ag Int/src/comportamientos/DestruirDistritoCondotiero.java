package comportamientos;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.Vector;

import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.DecirEstado;
import acciones.DestruirDistrito;
import acciones.PedirDistritoJugadores;
import conceptos.Distrito;
import conceptos.Jugador;

public class DestruirDistritoCondotiero extends Behaviour {

	private final AgTablero agt;
	private boolean fin = false;
	
	public DestruirDistritoCondotiero(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();
		//ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.ACCION_JUGADOR, jugador, 100);
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.PEDIRRESUMENESJUGADORES,jugador, 100);
		if(msg!=null){
			System.out.println("Accion recibida: "+ fin);
			fin = true;
			try {
				ContentElement contenido =agt.getContentManager().extractContent(msg);
				
				//decir estado me dice quien es el agente q lo pide, y es del q no voy a enviar informacion
				if(contenido instanceof DecirEstado){
					
					PedirDistritoJugadores pdj=new PedirDistritoJugadores();
					//se obtiene la informacion de los demas jugadores
					ResumenJugador[] res=ep.getResumenJugadores();
					int valores=0;
					for(int i=0;i<res.length;i++){
						if(res[i].getJugador().getNombre().compareToIgnoreCase(ep.getJugActual().getJugador().getNombre())!=0){
							//si no soy yo doy valores
							valores++;
							darValorPDJ(pdj, res, valores, i);
System.out.println("Entro");							
						}
					}
					
System.out.println("Imprimimos pdj");
System.out.println("person 1 = " + pdj.getPersonaje1().getNombre() + "; jug 1 = " + pdj.getJugador1().getNombre());
System.out.println("Imprimimos los distritos");
for(int i=0;i<pdj.getDistritos1().size();i++)
	System.out.print(": " + ((Distrito)(pdj.getDistritos1().get(i))).getNombre());
System.out.println("");
System.out.println("---------------------------------------");
System.out.println("person 2 = " + pdj.getPersonaje2().getNombre() + "; jug 2 = " + pdj.getJugador2().getNombre());
System.out.println("Imprimimos los distritos");
for(int i=0;i<pdj.getDistritos2().size();i++)
	System.out.print(": " + ((Distrito)(pdj.getDistritos2().get(i))).getNombre());
System.out.println("");
System.out.println("---------------------------------------");
System.out.println("person 3 = " + pdj.getPersonaje3().getNombre() + "; jug 3 = " + pdj.getJugador3().getNombre());
System.out.println("Imprimimos los distritos");
for(int i=0;i<pdj.getDistritos3().size();i++)
	System.out.print(": " + ((Distrito)(pdj.getDistritos3().get(i))).getNombre());
System.out.println("");
System.out.println("---------------------------------------");

					// Se le mandan los distritos al jugador
					// preparar lo que se da
					agt.sendMSG(ACLMessage.REQUEST, jugador, pdj, Filtros.DARRESUMENESJUGADORES);
					
					//siempre recibo un mensaje, si el pago es negativo es q no va a destruir nada
					ACLMessage msg2 = agt.reciveBlockingMessageFrom(Filtros.DESTRUIRDISTRITO, jugador);
	
					if(msg2!=null){
						DestruirDistrito dd=(DestruirDistrito)agt.getContentManager().extractContent(msg2);
						int p=dd.getPago();
System.out.println("Entra en la recepcion del mensaje del Condotiero");
						if(p>=0){
System.out.println("Entra en la destruccion del distrito");
							Jugador j=dd.getJugador();
							Distrito d=dd.getDistrito();
							ep.getResumenJugador(j).quitarDistrito(d);
							ep.getJugActual().setDinero(ep.getJugActual().getDinero()-p);
							/*
							 * notificar al jugador que le han quitado un distrito
							 * 
							 * ¿Y SI QUIERO Q SEA A TODOS?
							 */
							agt.sendMSG(ACLMessage.INFORM, ep.getResumenJugador(j), dd, Filtros.NOTIFICARDESTRUIRDISTRITO);
						}
					}
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

	private void darValorPDJ(PedirDistritoJugadores pdj, ResumenJugador[] res,
			int valores, int i) {
		Vector<Distrito> d=new Vector<Distrito>();
		switch (valores) {
		case 1:
System.out.println("entra en el 1");			
			pdj.setJugador1(res[i].getJugador());
			pdj.setPersonaje1(res[i].getPersonaje());
			pdj.setDistritos1(obtenerDistritos(res, i));
			break;
		case 2:
System.out.println("entra en el 2");
			pdj.setJugador2(res[i].getJugador());
			pdj.setPersonaje2(res[i].getPersonaje());
			pdj.setDistritos2(obtenerDistritos(res, i));
			break;
		case 3:
System.out.println("entra en el 3");
			pdj.setJugador3(res[i].getJugador());
			pdj.setPersonaje3(res[i].getPersonaje());
			pdj.setDistritos3(obtenerDistritos(res, i));
			break;
		default:
			break;
		}
	}

	private Distrito[] obtenerDistritos(ResumenJugador[] res, int i) {
		Vector<Distrito> d;
		d=res[i].getConstruido();
		Distrito[] dis=new Distrito[d.size()];
		for(int j=0;j<dis.length;j++){
			dis[j]=d.get(j);
		}
		return dis;
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return fin;
	}

}
