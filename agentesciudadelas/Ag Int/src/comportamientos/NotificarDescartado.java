package comportamientos;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import tablero.AgTablero;
import utils.Filtros;
import utils.Personajes;
import acciones.NotificarDescartados;

public class NotificarDescartado extends Behaviour{

	private AgTablero agt;
	
	public NotificarDescartado(AgTablero agTablero){
		this.agt = agTablero;
	}
	
	@Override
	public void action() {
		NotificarDescartados nd=new NotificarDescartados();
		nd.setDestapados(Personajes.difinirPersonajesDescartados());
		
		agt.sendMSG(ACLMessage.INFORM, null, nd, Filtros.NOTIFICARDESCARTADOS);
	}

	@Override
	public boolean done() {
		agt.addBehaviour(new SeleccionarPersonajes(agt));
		return true;// siempre termina
	}
}
