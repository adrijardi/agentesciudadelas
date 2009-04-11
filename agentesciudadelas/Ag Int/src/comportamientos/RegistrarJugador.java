package comportamientos;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.NotificarCorona;
import conceptos.Jugador;

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
		//Se obtiene el contenido de los mensajes
		DarDistritos dd = new DarDistritos();
		dd.setDistritos(rj.getCartasMano());
		
		DarMonedas dm = new DarMonedas();
		dm.setMonedas(rj.getDinero());
		
		agt.sendMSG(ACLMessage.INFORM, rj, dd, Filtros.DARDISTRITOS);
		agt.sendMSG(ACLMessage.INFORM, rj, dm, Filtros.DARMONEDAS);
	}

	@Override //Lo llama solo jade condicion de fin
	public boolean done() {
		EstadoPartida ep = EstadoPartida.getInstance();
		if (ep.isFaseIniciada()) {
			return false;
		} else {
			//
			seleccionarCoronaRandom();
			agt.addBehaviour(new NotificarDescartado(agt));
			return true;
		}
	}

	private void seleccionarCoronaRandom() {
		//Se obtiene el contenido de los mensajes
		EstadoPartida ep = EstadoPartida.getInstance();
		Jugador j = ep.getTieneCorona().getJugador();
		NotificarCorona nc = new NotificarCorona();
		nc.setJugador(j);
	
		agt.sendMSG(ACLMessage.INFORM, null, nc, Filtros.NOTIFICARCORONA);
	}
}
