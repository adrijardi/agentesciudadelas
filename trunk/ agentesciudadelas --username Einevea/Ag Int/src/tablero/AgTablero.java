package tablero;

import onto.*;
import comportamientos.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.behaviours.Behaviour;

public class AgTablero extends jade.core.Agent {

	private Behaviour beh;
	private final Codec codec = new SLCodec();
	private final Ontology onto = OntologiaCiudadelasDos.getInstance();

	
	public Codec getCodec() {
		return codec;
	}
	public Ontology getOnto() {
		return onto;
	}
	/*
	 * Codigo relacionado con la inicializacion del agente
	 * @see jade.core.Agent#setup()
	 */
	public void setup(){
		//Saludo del agente
		System.out.println("Soy el agente "+ getAID()+" y represento al Tablero.");
		
		//Se inicializan los parametros
		EstadoPartida.getInstance();
		
		//Se registra la ontología
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(onto);
		
		//Se registra el agente en el servicio del directorio
		///Se describen los servicios del agente
		ServiceDescription sd= new ServiceDescription(); 
		sd.setType("TableroJuego");
		sd.setName("Juego");
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
		
		//Se incializan los comportamientos
		/// Busca jugadores hasta completar la partida
		beh = new RegistrarJugador(this);
		addBehaviour(beh);
		
		//addBehaviour(new VictimaLadron()); // a–ade el servicio al agente
		
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
	    System.out.println("Seller-agent "+getAID().getName()+" terminating."); // Esribe en pantalla un mensaje de terminaci—n
	}
	/*
	 * hay q hacer toda la parte de la gestion de los mensajes y toda la mierda esa 
	 */
	
}
