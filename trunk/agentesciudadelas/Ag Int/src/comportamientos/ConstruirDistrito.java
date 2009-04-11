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
import acciones.CobrarDistritos;
import acciones.PedirConstruirDistrito;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class ConstruirDistrito extends Behaviour {

	private final AgTablero agt;
	private int construidoTurno;

	public ConstruirDistrito(AgTablero agTablero) {
		agt = agTablero;
		construidoTurno=0;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().PEDIRCONSTRUIRDISTRITO);
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.receive(plantilla);
		if(msg!=null){
			/*
			 * comprobar que el distrito que me ha enviado es valido, vamos q no lo tiene ya construido y en ese momento pedirle la pasta o negarselo
			 */
			ContentElement contenido = null;
			try {
				contenido=myAgent.getContentManager().extractContent(msg);
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
			
			PedirConstruirDistrito contenidoMensaje=new PedirConstruirDistrito();
			contenidoMensaje=(PedirConstruirDistrito)contenido;

			Jugador jg=contenidoMensaje.getJugador();
			Personaje pr=contenidoMensaje.getPersonaje();
			Distrito ds=contenidoMensaje.getDistrito();
			
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setOntology("CobrarDistritos");
			CobrarDistritos cd=new CobrarDistritos();
			cd.setJugador(jg);
			if(!ep.tieneDistrito(jg, ds)){
				// envia el mensaje de puedes contruir el distrito, realmente lo que hace es pedirle la pasta			
				cd.setCantidad(ds.getCoste());
				/*
				 * a�adir el comportamiento cobrarDistrito
				 */
				agt.addBehaviour(new CobrandoDistrito(agt));
			}else{
				// envia el mensaje de 'no puedes contruir el distrito', realmente lo que hace es pedirle la pasta con valor negativo, siendo opuesto al valor real
				cd.setCantidad((ds.getCoste() * -1));
			}
			
			/*
			 * ahora se envia el mensaje q se preparo
			 */
			try {
				myAgent.getContentManager().fillContent(msgEnviar, cd);
				myAgent.send(msgEnviar);
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // contenido es el objeto que envia
		}
	}

	@Override
	public boolean done() {
		EstadoPartida ep = EstadoPartida.getInstance();
		/*
		 * if jugAct= arquitecto Y const<3 y agentJugCons<8 
		 * 		return done
		 * else
		 * 		return true
		 */
		if(ep.getJugActual()==7 && ep.getResJugadorActual().getConstruido().size()<8 && construidoTurno<3){
			return false;
		}else{
			return true;
		}
	}
	
	public void incDistConstTurno(){
		this.construidoTurno++;
	}
}
