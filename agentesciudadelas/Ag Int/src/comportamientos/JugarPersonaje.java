package comportamientos;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;

import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.ResumenJugador;
import utils.Filtros;
import utils.Personajes;
import acciones.DarTurno;
import acciones.InfoPartida;
import acciones.NotificarCorona;
import conceptos.Personaje;


public class JugarPersonaje extends Behaviour {

	private final AgTablero agt;
	private EstadoPartida ep = EstadoPartida.getInstance();
	private boolean muerto;
	

	public JugarPersonaje(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		// Se obtiene el siguiente jugador que juega
		ResumenJugador jugador = ep.getJugActual();
		System.out.println("TOLON!! TOLON!! "+ jugador.getPersonaje().getNombre());
		
		Personaje aux=null;
		
		// Se notifica el nuevo jugador con la corona
		if (Personajes.REY.isPersonaje(jugador.getPersonaje())) {
			ep.setTieneCorona(jugador);
			NotificarCorona nc = new NotificarCorona();
			nc.setJugador(jugador.getJugador());
			agt.sendMSG(ACLMessage.INFORM, null, nc, Filtros.NOTIFICARCORONA);
		}

		// Se comprueba que no este muerto, si lo esta no se le envia el turno
		if (!jugador.equals(ep.getNombreMuerto())) {
			muerto = false;
			// Se añaden los comportamientos para que juege el jugador
			LinkedList<Behaviour> llb = new LinkedList<Behaviour>();
			/// Se añade la opcion de seleccionar cartas o dinero
			Behaviour beh;
			beh= new EleccionCartasODinero(agt);
			agt.addBehaviour(beh);
			llb.add(beh);
			///Se añade la opcion de construir un Distrito
			beh = new ConstruirDistrito(agt);
			agt.addBehaviour(beh);
			llb.add(beh);

			beh = new FinalizarTurno(agt, llb);
			agt.addBehaviour(beh);

			// TODO falta añadir el comportamiento de cada personaje
			switch (Personajes.getPersonajeByPJ(jugador.getPersonaje())) {
			case ASESINO:
				beh = new HabilidadAsesino(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				
				break;
			case LADRON:
				beh = new RobarTablero(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				
				break;	
			case MAGO:
				beh = new CambiarCartas(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				break;
			case REY:
				beh = new CobrarRey(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				
				break;
			case OBISPO:
				beh = new CobrarObispo(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				
				break;
			case MERCADER:
				beh = new CobrarMercader(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				
				break;
			case ARQUITECTO:
				beh = new HabilidadArquitecto(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				break;
			case CONDOTIERO:
				beh = new DestruirDistritoCondotiero(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				beh = new CobrarCondotierro(agt);
				agt.addBehaviour(beh);
				llb.add(beh);
				
				break;
			}
		
		}else{
			System.out.println("Jugador "+jugador.getJugador().getNombre()+" muerto");
			muerto = true;
		}
		
		
		// Notifica la informacion de la partida
		InfoPartida msgInfo = new InfoPartida();
		msgInfo.setJugador1(ep.getResumenJugador(0).getJugador());
		msgInfo.setJugador2(ep.getResumenJugador(1).getJugador());
		msgInfo.setJugador3(ep.getResumenJugador(2).getJugador());
		msgInfo.setJugador4(ep.getResumenJugador(3).getJugador());
		for(int i = 0; i < 4; i++){
			if(ep.getResumenJugador(i).getJugo()){
				switch (i){
				case 0:
					msgInfo.setPersonaje1(ep.getResumenJugador(i).getPersonaje());
					msgInfo.setJugoP1(true);
				break;
				case 1:
					msgInfo.setPersonaje2(ep.getResumenJugador(i).getPersonaje());
					msgInfo.setJugoP2(true);
				break;
				case 2:
					msgInfo.setPersonaje3(ep.getResumenJugador(i).getPersonaje());
					msgInfo.setJugoP3(true);
				break;
				case 3:
					msgInfo.setPersonaje4(ep.getResumenJugador(i).getPersonaje());
					msgInfo.setJugoP4(true);
				break;
				}
			}else{
				switch (i){
				case 0:
					msgInfo.setPersonaje1(Personajes.PREVIO.getPj());
					msgInfo.setJugoP1(false);
				break;
				case 1:
					msgInfo.setPersonaje2(Personajes.PREVIO.getPj());
					msgInfo.setJugoP2(false);
				break;
				case 2:
					msgInfo.setPersonaje3(Personajes.PREVIO.getPj());
					msgInfo.setJugoP3(false);
				break;
				case 3:
					msgInfo.setPersonaje4(Personajes.PREVIO.getPj());
					msgInfo.setJugoP4(false);
				break;
				}
			}
		}
		msgInfo.setDistritosJ1(ep.getResumenJugador(0).getConstruido2());
		msgInfo.setDistritosJ2(ep.getResumenJugador(1).getConstruido2());
		msgInfo.setDistritosJ3(ep.getResumenJugador(2).getConstruido2());
		msgInfo.setDistritosJ4(ep.getResumenJugador(3).getConstruido2());
		
		//agt.sendMSG(ACLMessage.REQUEST, jugador, msgInfo, Filtros.INFOPARTIDA);

		
		
		// Notifica el turno a un jugador
		DarTurno msgNotificar = new DarTurno();
		msgNotificar.setJugador(jugador.getJugador());
		msgNotificar.setMuerto(muerto);
		
		if(!muerto){
			// si no hay muerto pongo haymuerto a false y en personaje muerto al jugador actual
		      msgNotificar.setHaymuerto(false);
		      msgNotificar.setPersonaje(ep.getJugActual().getPersonaje());
		}else{
			// si hay muerto pongo haymuerto a true y el muerto en el personaje
		      msgNotificar.setHaymuerto(true);
		      msgNotificar.setPersonaje(ep.getNombreMuerto().getPersonaje());
		}
		
		// TODO cambiar
		msgNotificar.setRobado(false);
		
		if(ep.getNombreRobado()!=null){
			msgNotificar.setHayrobado(true);
			msgNotificar.setPersonajerobado(ep.getNombreRobado().getPersonaje());
		}else{
			msgNotificar.setHayrobado(false);
			msgNotificar.setPersonajerobado(ep.getJugActual().getPersonaje());
		}
		
		
		agt.sendMSG(ACLMessage.REQUEST, jugador, msgNotificar, Filtros.NOTIFICARTURNO);
		agt.sendMSG(ACLMessage.REQUEST, jugador, msgInfo, Filtros.INFOPARTIDA);
		ep.getJugActual().setJugo(true);
	}

	@Override
	public boolean done() {
		if(muerto){
			ep.nextJugadorPorTurnoPersonaje();
		
			switch (ep.getFase()) {
			case SEL_PERSONAJES:
				agt.addBehaviour(new NotificarDescartado(agt));
				break;
	
			case FINALIZAR_JUEGO:
				agt.addBehaviour(new JugarPersonaje(agt));
				break;
	
			case JUGAR_RONDA:
				agt.addBehaviour(new JugarPersonaje(agt));
				break;	
			}
		}
		return true;
	}

}
