package comportamientos_jugador;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.Monedas;

public class PagarRobo extends Behaviour{
	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public PagarRobo(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	@Override
	public void action(){
		
		Monedas m=new Monedas();
		m.setDinero(_agj.getMonedas());
System.out.println("ejecuta el PagarRobo");
		_agj.setMonedas(0);
		_agj.sendMSG(ACLMessage.REQUEST, raid, m, Filtros.PAGARROBO);
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}
}