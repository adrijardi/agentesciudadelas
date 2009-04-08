package comportamientos;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import tablero.AgTablero;
import utils.Personajes;
import acciones.NotificarDescartados;

public class NotificarDescartado extends Behaviour{

	private AgTablero agt;
	
	public NotificarDescartado(AgTablero agTablero){
		this.agt = agTablero;
	}
	
	@Override
	public void action() {
		
		ACLMessage msgEnviar = new ACLMessage(ACLMessage.INFORM);
		msgEnviar.setSender(agt.getAID());
		msgEnviar.setOntology(agt.getOnto().getName());
		msgEnviar.setLanguage(agt.getCodec().getName());
		
		NotificarDescartados nd=new NotificarDescartados();
		nd.setDestapados(Personajes.difinirPersonajesDescartados());
		
		try {
			myAgent.getContentManager().fillContent(msgEnviar,nd);
			myAgent.send(msgEnviar);
			System.out.println(msgEnviar);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // contenido es el objeto que envia
	}

	@Override
	public boolean done() {
		agt.addBehaviour(new SeleccionarPersonajes(agt));
		return true;// siempre termina
	}
}
