package comportamientos;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Vector;

import tablero.AgTablero;
import tablero.EstadoPartida;
import utils.Filtros;
import utils.Personajes;
import acciones.ElegirPersonaje;
import acciones.OfertarPersonajes;
import conceptos.Personaje;


public class SeleccionarPersonajes extends Behaviour {

	private final AgTablero agt;
	private final EstadoPartida ep;
	private int contador;
	private Vector<Personaje> pDisponibles = new Vector<Personaje>();
	
	public SeleccionarPersonajes(AgTablero agTablero) {
		agt = agTablero;
		ep = EstadoPartida.getInstance();
		
		// Se seleccionan aleatoriamente los personajes que no van a estár disponibles en este turno.
		contador = 0;
		
		Vector<Integer> num=new Vector<Integer>();
		for(int i=0;i<8;i++){
			num.add(i+1);
		}
		
		Personaje [] eliminadosNoOcultos = new Personaje[2];
		
		int eliminado = (int)Math.random()*num.size();
		ep.set_personajeNoDisponibleOculto(Personajes.getPersonajeByTurno(num.get(eliminado)));
		num.removeElement(eliminado);
		
		for(int i = 0; i < 2; i++){
			eliminado = (int)Math.random()*num.size();
			eliminadosNoOcultos[i] = Personajes.getPersonajeByTurno(num.get(eliminado));
			num.removeElement(eliminado);
		}
		ep.set_personajesNoDisponibles(eliminadosNoOcultos);
		
		for(int i=0;i<num.size();i++){
			pDisponibles.add(Personajes.getPersonajeByTurno(num.get(i)));
		}
	}

	@Override
	public void action() {
		/*
		 * 1º enviar el mensaje al ag con la corona
		 * 2º me bloque hasta q recibo el mensaje de selecion de personaje
		 */
		int tieneCor=ep.getCorona();
		ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
		msgEnviar.setSender(agt.getAID());
		msgEnviar.setLanguage(agt.getCodec().getName());
		msgEnviar.setOntology(agt.getOnto().getName());
		msgEnviar.setConversationId(Filtros.OFERTARPERSONAJES);
		if(contador==0){
			msgEnviar.addReceiver(ep.getResJugadores()[tieneCor].getIdentificador());
		}else{
			int num=(contador+tieneCor)%4;
			msgEnviar.addReceiver(ep.getResJugadores()[num].getIdentificador());
		}
		
		// crear la oferta de personajes
		OfertarPersonajes op=new OfertarPersonajes();
		op.setDisponibles(pDisponibles.toArray(new Personaje[pDisponibles.size()]));
		op.setJugador(ep.getResJugadorActual().getJugador());
		
		System.out.println("<<<<<<<<<llega aqui 0");
		try {
			agt.getContentManager().fillContent(msgEnviar,op);
			System.out.println(msgEnviar);
			agt.send(msgEnviar);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("<<<<<<<<<llega aqui");		

		System.out.println("<<<<<<<<<llega aqui 2");
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.ELEGIRPERSONAJE);
		

		ACLMessage msg = myAgent.blockingReceive(filtroIdentificador);
		System.out.println(msg);
		
		System.out.println("<<<<<<<<<llega aqui 3");
		if (msg != null) {
			ElegirPersonaje contenido = null;
			try {
				contenido = (ElegirPersonaje)agt.getContentManager().extractContent(msg);
				
				pDisponibles.removeElement(contenido.getPersonaje());
				contador++;
				
				
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
			
			
		}
		System.out.println("<<<<<<<<<llega aqui 4");
	}

	@Override
	public boolean done() {
		System.out.println("<<<<<<<<<llega aqui 5, contador "+contador);
		if(contador==4){
			agt.addBehaviour(new JugarPersonaje(agt));
			return true;
		}else{
			return false;
		}
	}

}
