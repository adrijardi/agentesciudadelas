package jugador;

import onto.*;
import utils.Filtros;
import comportamientos.*;
import comportamientosGeneric.ElegirPersonajeJugador;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgJugador extends jade.core.Agent {
	
	private Codec codec = new SLCodec();
	private final OntologiaCiudadelasDos onto = OntologiaCiudadelasDos.getInstance();
	
	public Codec getCodec() {
		return codec;
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
		getContentManager().registerLanguage(codec);
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
		
		//MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.OFERTARPERSONAJES);
		//MessageTemplate filtroEmisor = MessageTemplate.MatchSender(_agj.);
		//MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		
		ElegirPersonajeJugador comp = new ElegirPersonajeJugador(this);
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
}
