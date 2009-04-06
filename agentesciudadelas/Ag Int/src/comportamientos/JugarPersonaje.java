package comportamientos;

import conceptos.Jugador;
import conceptos.Personaje;
import acciones.CobrarDistritos;
import acciones.DarMonedas;
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

public class JugarPersonaje extends Behaviour {

	private final AgTablero agt;
	private EstadoPartida ep = EstadoPartida.getInstance();
	private boolean primero;

	public JugarPersonaje(AgTablero agTablero) {
		agt = agTablero;
		primero=true;
	}

	@Override
	public void action() {
		
		if(primero){
			primero=false;
		}else{
			block();
			
			MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology("NotificarFinTurnoJugador");
			MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
			MessageTemplate plantilla = MessageTemplate.and(filtroEmisor,filtroIdentificador);

			ACLMessage msg = myAgent.receive(plantilla);
		}
		/*
		 * el fin de seleccionar personaje añade el comportamiento este pero las demas veces q se tiene 
		 * que ejecutar debera estar bloqueado hasta que se notifique el fin del turno del jugador
		 * 
		 * TODOS LOS PERSONAJES ACABAN COMUNICANDO QUE HAN ACABADO
		 */

		

//		if (msg != null) {
			/*
			 * cuando se empieza a jugar lo primero es incrementar el jugador,
			 * si el ultimo lo puso el jugador 7 ahora se pasara al valor 1
			 * 
			 * cuando se venga de la fase anterior el jugador actual debe ser el
			 * 7, solo por estandarizacion
			 */
			// cogemos el personaje a jugar e incrementamos el numero de
			// jugadores que han jugado
			ResumenJugador jugAct;
			do{
				jugAct=ep.nextPersonaje();
			}while(jugAct==null);
			ep.setNumJugHanJugado(ep.getNumJugHanJugado() + 1);
			
			agt.addBehaviour(new EleccionCartasODinero(agt));
			agt.addBehaviour(new ConstruirDistrito(agt));

			if (!jugAct.getPersonaje().getNombre().equalsIgnoreCase(ep.getNombreMuerto())) {
				/*
				 * a�adir el comportamiento generico ConstruirDistrito(this);
				 * este comportamiento debe finalizarse al final en el propio
				 * done()
				 */
				if(jugAct.getPersonaje().getNombre()==ep.getNombreRobado()){
					/*
					 * Cambia el dinero de manos
					 */
					ACLMessage msgEnviar = new ACLMessage(ACLMessage.REQUEST);
					msgEnviar.setSender(agt.getAID());
					msgEnviar.setOntology(agt.getOnto().DARMONEDAS);
msgEnviar.setConversationId(Filtros.COBRA_LADRON);
					DarMonedas dm=new DarMonedas();
					dm.setMonedas(ep.getResJugadorActual().getDinero());
					ep.getJugLadron().setDinero(ep.getJugLadron().getDinero()+ep.getResJugadorActual().getDinero());
					ep.getResJugadorActual().setDinero(0);
					try {
						myAgent.getContentManager().fillContent(msgEnviar,dm);
						myAgent.send(msgEnviar);
					} catch (CodecException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OntologyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // contenido es el objeto que envia
				}
				switch (jugAct.getPersonaje().getTurno()) {
				case 1:
					/*
					 * a�adir el comportamiento de la habilidad del jugador
					 * Asesino: habilidadAsesino(this)
					 */
					agt.addBehaviour(new HabilidadAsesino(agt));
					break;
				case 2:
					/*
					 * a�adir el comportamiento de la habilidad del jugador
					 * Ladron: habilidadLadron(this)
					 */
					agt.addBehaviour(new HabilidadLadron(agt));
/*
 * Falta que en HabilidadLadron se compruebe si se robo a alguien valido, en ese caso hay q a�adir la habilidad EsperaRobo(agt)
 */
					break;
				case 3:
					/*
					 * a�adir el comportamiento de la habilidad del jugador
					 * Mago: habilidadMago(this)
					 */
					agt.addBehaviour(new HabilidadCambiarMano(agt));
					agt.addBehaviour(new CambiarCartas(agt));
					ep.setJugLadron(ep.getResJugadorActual());
					break;
				case 4:
					/*
					 * a�adir el comportamiento de la habilidad del jugador Rey:
					 * habilidadRey(this)
					 */
					agt.addBehaviour(new HabilidadRey(agt));
					agt.addBehaviour(new CobrarRey(agt));
					break;
				case 5: //obispo
					/*
					 * a�adir el comportamiento de la habilidad del jugador
					 * Obispo: habilidadObispo(this) esta habilidad debe estar
					 * siempre activada o hacerlo mediante control del tablero q
					 * es mas comodo
					 */
//					agt.addBehaviour(new HabilidadObispo(agt));
					agt.addBehaviour(new CobrarObispo(agt));
					break;
				case 6: //mercader
					/*
					 * a�adir el comportamiento de la habilidad del jugador
					 * Mercader: habilidadMercader(this)
					 */
					agt.addBehaviour(new HabilidadMercader(agt));
					agt.addBehaviour(new CobrarMercader(agt));
					break;
				case 7:
					/*
					 * a�adir el comportamiento de la habilidad del jugador
					 * Arquitecto: habilidadArquitecto(this)
					 *
					 * Su habilidad se controla en el comportamiento que permite elegir entre cartas o dinero
					 */
					break;
				case 8: //condotierro
					/*
					 * a�adir el comportamiento de la habilidad del jugador
					 * Condotierro: habilidadCondotierro(this)
					 */
					agt.addBehaviour(new HabilidadCondotiero(agt));
					agt.addBehaviour(new CobrarCondotierro(agt));
					break;
				default:
					break;
				}

			}
		}
//	}

	@Override
	public boolean done() {
		if (ep.isJugarRonda() && ep.getNumJugHanJugado() < 4) {
			return false;
		} else {
			/* si ya se han ejecutado los 4 jugadores debemos parar */
			ep.setNombreMuerto(null);
			ep.setNumJugHanJugado(0);
			ep.setNombreRobado(null);
			ep.setJugLadron(null);
			ep.setFase(EnumFase.SEL_PERSONAJES);
			/*
			 * tras terminar esta fase se entra en notificarDescartado otra vez
			 */
			agt.addBehaviour(new NotificarDescartado(agt));
			return true;
		}
	}

}
