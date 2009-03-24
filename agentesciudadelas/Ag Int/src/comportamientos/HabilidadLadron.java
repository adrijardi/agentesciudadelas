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
import utils.Personajes;
import acciones.CobrarDistritos;
import acciones.NotificarRobado;
import acciones.PagarDistrito;
import acciones.PedirConstruirDistrito;
import acciones.Robar;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class HabilidadLadron extends Behaviour {

	private final AgTablero agt;

	public HabilidadLadron(AgTablero agTablero) {
		agt = agTablero;
	}
/*
 * se ha supuesto que es el propio agente quien no roba a quien han matado pero no estaria de mas controlarlo
 */
	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology("Robar");
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.receive(plantilla);
		if(msg!=null){
			
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
			// tengo el mensaje y su contenido, ahora a actualizar el estado actual
			
			Robar mt=(Robar)contenido;
			Personaje pr=mt.getPersonaje();
			
			Jugador jg = new Jugador();
			jg.setNombre(ep.getResJugadorActual().getIdentificador().getName());
			jg.setMano(ep.getResJugadorActual().getCartasMano().length);
			jg.setMonedas(ep.getResJugadorActual().getDinero());
			jg.setPuntos(ep.getResJugadorActual().getPuntos());
			NotificarRobado nt=new NotificarRobado();
			nt.setJugadorLadron(jg);
			nt.setPersonaje(pr);
			
			ep.setNombreRobado(pr.getNombre());
			
			if(pr.getNombre().equals(ep.getNombreMuerto()) || pr.getNombre().equalsIgnoreCase(Personajes.ASESINO.name())){
				/*
				 * si intenta robar al asesino o al que ha sido asesinado se notifica NULL
				 */
				ep.setNombreRobado(null);
				nt.setPersonaje(null);
			}
			/* se ha cambiado el estado interno de la partida
			 * ahora se envia a todo el mundo el mensaje diciendo q personaje esta muerto, el contenido es el mismo que el del mensaje que hemos recibido
			 */
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setOntology("NotificarRobado");
			try {
				myAgent.getContentManager().fillContent(msgEnviar, nt);
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
		return true;// siempre termina
	}
}