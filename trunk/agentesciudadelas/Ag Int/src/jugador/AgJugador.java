package jugador;

import jade.content.AgentAction;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import onto.OntologiaCiudadelasDos;
import tablero.ResumenJugador;
import acciones.NotificarFinTurnoJugador;
import acciones.OfertarPersonajes;

import comportamientosGeneric.RecibirIniciarJugador;

import conceptos.Jugador;
import conceptos.Personaje;

public abstract class AgJugador extends jade.core.Agent {
	
	//private Codec codec = new SLCodec();
	private Codec codec2 = new LEAPCodec();
	private final OntologiaCiudadelasDos onto = OntologiaCiudadelasDos.getInstance();
	
	private InfoPartida infoPartida = new InfoPartida();
	
	public Codec getCodec() {
		return codec2;
	}
	public OntologiaCiudadelasDos getOnto() {
		return onto;
	}
	
	/*
	 * Codigo relacionado con la inicializacion del agente
	 * @see jade.core.Agent#setup()
	 */
	public void setup(){
		//Saludo del agente
		System.out.println("Soy el agente "+ getAID()+" y represento a un Jugador.");
		
		//Se registra la ontologia
		getContentManager().registerLanguage(codec2);
		getContentManager().registerOntology(onto);
		
		//Se registra el agente en el servicio del directorio
		///Se describen los servicios del agente
		ServiceDescription sd= new ServiceDescription(); 
		sd.setType("jugador");
		sd.setName("jugador1");
		
		///Se crea una descripción del agente
		DFAgentDescription dfd= new DFAgentDescription(); 
		dfd.setName(getAID());
		dfd.addServices(sd);
		///Se registra
		try { 
			DFService.register(this,dfd); 
		}catch (FIPAException fe) {
			fe.printStackTrace(); 
		}
		
		RecibirIniciarJugador comp = new RecibirIniciarJugador(this);
		addBehaviour(comp);
	}

	/*
	 * Se liberan los ercursos
	 * @see jade.core.Agent#takeDown()
	 */
	@Override
	protected void takeDown() { // Aqui se ponen las operaciones de limpieza de recursos
	    try {
	    	DFService.deregister(this); // Intenta darse de baja del resgitro de las paginas amarillas
	    }catch (FIPAException fe) {
	    	fe.printStackTrace();
	    }
	    System.out.println("Seller-agent "+getAID().getName()+" terminating."); // Esribe en pantalla un mensaje de terminaci�n
	}
	public InfoPartida getInfoPartida() {
		return infoPartida;
	}
	public void setInfoPartida(InfoPartida infoPartida) {
		this.infoPartida = infoPartida;
	}
	
	public Jugador getJugador(){
		Jugador ret = new Jugador();
		ret.setMano(infoPartida.getMano());
		ret.setMonedas(infoPartida.getMonedas());
		ret.setPuntos(infoPartida.getPuntos());
		ret.setNombre(getName());
		return ret;
	}
	
	/*
	 * Esta funcion simplifica el envio de mensajes rellenando los campos necesarios
	 */
	public boolean sendMSG(int perf, AID reciver, AgentAction msgContent, String filtro) {
		boolean ret = false;
		
		ACLMessage msgEnviar = new ACLMessage(perf);
		msgEnviar.setSender(getAID());
		msgEnviar.setLanguage(getCodec().getName());
		msgEnviar.setOntology(getOnto().getName());
		if(filtro != null){
			msgEnviar.setConversationId(filtro);
		}
		if (reciver != null)
			msgEnviar.addReceiver(reciver);

		try {
			getContentManager().fillContent(msgEnviar, msgContent);
			System.out.println("$ Envio de mensaje:");
			if(reciver != null)
				System.out.println("| "+getAID().getName()+" manda un mensaje a "+ reciver.getName());
			else
				System.out.println("| "+getAID().getName()+" manda un mensaje a todos");
			if(filtro != null)
				System.out.println("| Conversacion: "+filtro);
			System.out.println("| Contenido: "+msgContent);
			System.out.println("$ Fin de mensaje\n");
			send(msgEnviar);
			ret = true;
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	/*
	 * Funcion que se bloquea esperando un mensaje con un determinado filtro
	 */
	public ACLMessage reciveBlockingMessage(String filtro){
		ACLMessage ret = null;
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(filtro);
		ret = blockingReceive(filtroIdentificador);
		return ret;
	}
	
	/*
	 * Funcion que se bloquea esperando un mensaje de un agente espedifico con un determinado filtro
	 */
	public ACLMessage reciveBlockingMessageFrom(String filtro, ResumenJugador sender){
		ACLMessage ret = null;
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(filtro);
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(sender.getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ret = blockingReceive(plantilla);
		return ret;
	}
	
	public abstract Personaje selectPersonaje(OfertarPersonajes contenido);
	public abstract NotificarFinTurnoJugador jugarTurno(ACLMessage msg);

}
