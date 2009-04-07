package comportamientos;

import tablero.EstadoPartida;
import tablero.Mazo;
import acciones.DarDistritos;
import acciones.DarMonedas;
import conceptos.Distrito;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import utils.*;
import conceptos.Jugador;
import conceptos.Personaje;
import acciones.CobrarDistritos;
import acciones.DarMonedas;
import acciones.NotificarDescartados;
import acciones.NotificarFinTurnoJugador;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import tablero.EstadoPartida.EnumFase;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.EstadoPartida;
import utils.Filtros;
import utils.Personajes;

public class NotificarDescartado extends Behaviour{

	private AgTablero agt;
	
	public NotificarDescartado(AgTablero agTablero){
		this.agt = agTablero;
	}
	
	@Override
	public void action() {
		int destapado1 = Personajes.REY.ordinal();
		EstadoPartida ep = EstadoPartida.getInstance();
		while(destapado1 == Personajes.REY.ordinal()){
			destapado1 = (int)(Math.random()*7)+1;
		}
		int destapado2 = Personajes.REY.ordinal();
		while(destapado2 == Personajes.REY.ordinal() || destapado2 == destapado1){
			destapado2 = (int)(Math.random()*7)+1;
		}
		
		Personaje[] noDisponibles = new Personaje[2];
		noDisponibles[0] = Personajes.getPersonajeByTurno(destapado1);
		noDisponibles[1] = Personajes.getPersonajeByTurno(destapado2);
		
		ep.set_personajesNoDisponibles(noDisponibles);
		
		ACLMessage msgEnviar = new ACLMessage(ACLMessage.INFORM);
		msgEnviar.setSender(agt.getAID());
		msgEnviar.setOntology(agt.getOnto().getName());
		msgEnviar.setLanguage(agt.getCodec().getName());
		
		NotificarDescartados nd=new NotificarDescartados();
		nd.setDestapados(noDisponibles);
		
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
