package comportamientos;

import acciones.DarDistritos;
import conceptos.Distrito;
import jade.content.ContentElement;
import jade.content.abs.AbsContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.Mazo;

public class EsperarDistrito extends Behaviour {
	/*
	 * Lo mejor seria definir un mensaje para pedir cobrar el distrito del rey y que sea lo q este espera					
	 */
	
	private final AgTablero agt;

	public EsperarDistrito(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().DARDISTRITOS);
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
			
			DarDistritos dd=(DarDistritos)contenido;
			if(dd.getDistritos().isEmpty()) System.out.println("FALLO, quiero mi distrito de vuelta");
			if(dd.getDistritos().size()>1) System.out.println("FALLO, me sobran distritos");
			Mazo ma=Mazo.getInstance();
			ma.trashDistrito((Distrito) dd.getDistritos().get(0));
		}
	}

	@Override
	public boolean done() {
		return true;// siempre termina
	}
}