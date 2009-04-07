package comportamientos;

import java.util.Vector;

import conceptos.Distrito;
import conceptos.Jugador;
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.NotificarDescartados;
import acciones.OfertarPersonajes;
import acciones.SeleccionarPersonaje;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.Mazo;
import utils.Filtros;
import jade.core.behaviours.Behaviour;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SeleccionarPersonajes extends Behaviour {

	private final AgTablero agt;
	private final EstadoPartida ep;
	private int contador;
	private Vector<Integer> disp;
	
	public SeleccionarPersonajes(AgTablero agTablero) {
		agt = agTablero;
		ep = EstadoPartida.getInstance();
		contador = 0;
		disp=new Vector<Integer>();
		Vector<Integer> num=new Vector<Integer>();
		for(int i=0;i<8;i++){
			num.add(i+1);
		}
		num.removeElement(ep.getCorona());
		for(int i=0;i<5;i++){
			int aux=(int)(Math.random()*num.size());
			disp.add(num.get(aux));
			num.remove(aux);
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
		msgEnviar.setOntology(agt.getOnto().getName());
		msgEnviar.setLanguage(agt.getCodec().getName());
		if(contador==0){
			msgEnviar.addReceiver(ep.getResJugadores()[tieneCor].getIdentificador());
		}else{
			int num=(contador+tieneCor)%4;
			msgEnviar.addReceiver(ep.getResJugadores()[num].getIdentificador());
		}
		// crear la oferta de personajes
		OfertarPersonajes op=new OfertarPersonajes();
		int[] d =new int[disp.size()];
		for(int i=0;i<d.length;i++){
			d[i]=disp.get(i);
		}
		op.setDisponibles(d);
		
		try {
			myAgent.getContentManager().fillContent(msgEnviar,op);
			myAgent.send(msgEnviar);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		block();
		
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology(agt.getOnto().SELECCIONARPERSONAJE);
		

		ACLMessage msg = myAgent.receive(filtroIdentificador);

		if (msg != null) {
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
			
			SeleccionarPersonaje sp=(SeleccionarPersonaje)contenido;
			
			int n=sp.getId_jugador();
			disp.removeElement(n);
			contador++;
		}
	}

	@Override
	public boolean done() {
		if(contador==4){
			agt.addBehaviour(new JugarPersonaje(agt));
			return true;
		}else{
			return false;
		}
	}

}
