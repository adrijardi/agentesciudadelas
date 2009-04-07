package comportamientosGeneric;

import conceptos.Jugador;
import conceptos.Personaje;
import acciones.ElegirPersonaje;
import acciones.OfertarPersonajes;
import tablero.EstadoPartida;
import utils.Filtros;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jugador.AgJugador;

public class ElegirPersonajeJugador extends Behaviour {

	private final AgJugador _agj;

	public ElegirPersonajeJugador(AgJugador agJugador) {
		_agj = agJugador;
	}
	
	@Override
	public void action() {
		ContentManager manager = _agj.getContentManager();
		manager.registerOntology(_agj.getOnto());
		manager.registerLanguage(_agj.getCodec());
		
		EstadoPartida ep = EstadoPartida.getInstance();
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.OFERTARPERSONAJES);
		//MessageTemplate filtroEmisor = MessageTemplate.MatchSender(_agj.);
		//MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.blockingReceive(filtroIdentificador);
		msg.setOntology(_agj.getOnto().getName());
		System.out.println("<Jugador> "+msg);
		try {
			OfertarPersonajes contenido = (OfertarPersonajes)manager.extractContent(msg);
			
			Personaje seleccionado = (Personaje)contenido.getDisponibles().get(0); // Se selecciona el primer personaje que llega
			
			ElegirPersonaje salida = new ElegirPersonaje();
			Jugador yo = new Jugador();
			yo.setNombre(_agj.getName());
			salida.setJugador(yo);
			salida.setPersonaje(seleccionado);
						
			ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
			msgEnviar.setSender(_agj.getAID());
			msgEnviar.setOntology(_agj.getOnto().getName());
			msgEnviar.setLanguage(_agj.getCodec().getName());
			msgEnviar.setConversationId(Filtros.ELEGIRPERSONAJE);
			myAgent.getContentManager().fillContent(msgEnviar,salida);

			System.out.println("<J-envia> "+msgEnviar);
			
			myAgent.send(msgEnviar);
			
		} catch (UngroundedException e) {
			System.out.println("UngroundedException!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			System.out.println("CodecException!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			System.out.println("OntologyException!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean done() {
		return true;
	}

}
