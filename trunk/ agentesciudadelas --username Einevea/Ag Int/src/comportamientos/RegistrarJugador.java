package comportamientos;

import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.NotificarCorona;
import conceptos.Distrito;
import conceptos.Jugador;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class RegistrarJugador extends Behaviour {

	private final AgTablero agt;

	public RegistrarJugador(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		// Se busca un agente jugador
		ServiceDescription sd = new ServiceDescription();
		sd.setType("jugador");
		DFAgentDescription template = new DFAgentDescription();
		template.addServices(sd);

		// Se registran los 4 primeros jugadores que se encuentren
		try {
			DFAgentDescription[] result = DFService.search(agt, template);
			ResumenJugador rj;
			for (int i = 0; i < result.length && ep.isPartidaLibre(); ++i) {
				rj = ep.addJugador(result[i].getName());
				if (rj != null)
					confirmarJugador(rj);

			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	private void confirmarJugador(ResumenJugador rj) {
		ACLMessage mensajeCartas = new ACLMessage(ACLMessage.INFORM);
		ACLMessage mensajeMondeas = new ACLMessage(ACLMessage.INFORM);

		mensajeCartas.setLanguage(agt.getCodec().getName());
		mensajeMondeas.setLanguage(agt.getCodec().getName());
		mensajeCartas.addReceiver(rj.getIdentificador());
		mensajeMondeas.addReceiver(rj.getIdentificador());
		mensajeCartas.setOntology(agt.getOnto().getName());
		mensajeMondeas.setOntology(agt.getOnto().getName());
		
		//Se obtiene el contenido de los mensajes
		DarDistritos dd = new DarDistritos();
		dd.setDistritos(rj.getCartasMano());
		
		DarMonedas dm = new DarMonedas();
		dm.setMonedas(rj.getDinero());

		try {
			agt.getContentManager().fillContent(mensajeCartas, dd);
			agt.getContentManager().fillContent(mensajeMondeas, dm);
			System.out.println(mensajeCartas);
			System.out.println(mensajeMondeas);
			agt.send(mensajeCartas);
			agt.send(mensajeMondeas);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		EstadoPartida ep = EstadoPartida.getInstance();
		if (ep.isFaseIniciada()) {
			return false;
		} else {
			//
			seleccionarCoronaRandom();
			agt.addBehaviour(new SeleccionarPersonajes(agt));
			return true;
		}
	}

	private void seleccionarCoronaRandom() {
		ACLMessage mensajeCorona = new ACLMessage(ACLMessage.INFORM);
		EstadoPartida ep = EstadoPartida.getInstance();
		Jugador j = ep.seleccionarCoronaRandom();
	
		mensajeCorona.setLanguage(agt.getCodec().getName());
		mensajeCorona.setOntology(agt.getOnto().getName());

		//Se obtiene el contenido de los mensajes
		NotificarCorona nc = new NotificarCorona();
		nc.setJugador(j);
		
		try {
			agt.getContentManager().fillContent(mensajeCorona, nc);
			System.out.println(mensajeCorona);
			agt.send(mensajeCorona);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
