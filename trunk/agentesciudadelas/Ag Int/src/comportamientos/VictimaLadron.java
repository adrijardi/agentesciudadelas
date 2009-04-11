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
import acciones.NotificarRobado;

public class VictimaLadron extends Behaviour{
	
	EstadoPartida estPart;
	private final AgTablero agt;
	
	public VictimaLadron(AgTablero agTablero) {
		agt = agTablero;
	}
	/*
	 * Accion a realizar por este comportamiento
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action(){
		
		/*
		 * a la espera de llegar un mensaje
		 */
		/* a la espera de llegar un mensaje que siga el protocolo "VictimaLadron" */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().NOTIFICARROBADO);
		ACLMessage msg = myAgent.receive(filtroIdentificador);
		this.block();
		
		
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
			
			NotificarRobado aux=new NotificarRobado();
			aux=(NotificarRobado)contenido;
			String nombrePers=aux.getPersonaje().getNombre();
			int numAg=-1;
			estPart=EstadoPartida.getInstance();
			for(int i=0;i<this.estPart.getNumJugador();i++){

				
			}
			/* if(nombrePers esta jugando este turno) /* hay q averiguar si esta jugando este turno*/
			if(true){
				// 2 - enviamos el mensaje
				ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST_WHEN);
				/* en el agente receptor esto tiene q despertar a un comportamiento q unicamente cambie el estado interno del agente 
				 * y le diga que ha sido robado
				 * 
				 * Otro comportamiento q se deba activar cuando le digan q es su turno debera de mirar el estado interno para saber 
				 * que fue robado
				 * */

				/* este si nos interesa y lo hemos tenido que averiguar */
				//			msgEnviar.addReceiver(<AGENTE_Q_DEBE_RECIVIR_EL_MENSAJE>); 
				msgEnviar.setOntology(agt.getOnto().NOTIFICARROBADO);
				//			msgEnviar.setOntology(acciones.NotificarRobado); // instancia de una onto que sigue
				//			msgEnviar.setLanguage(FIPANames.ContentLanguage.FIPA_SL0); // 
				try {
					myAgent.getContentManager().fillContent(msgEnviar, contenido);
					myAgent.send(msgEnviar);
				} catch (CodecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OntologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // contenido es el objeto que envia
				System.out.println("El tablero ha recibido el mensaje con el protocolo NotificarRobado");
			}
		}
	
		System.out.println("entra en el comportamietno de VictimaLadon");
	}

	/*
	 * Para que deje de repetirse el intento de llevar a cabo esta accion
	 * @see jade.core.behaviours.Behaviour#done()
	 */
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}
}
