package acciones;

/*
 * A�adido por Pablo
 * 
 * la idea es q cuando acabamos un turno se envia este mensaje al tablero, tb se envia este mensaje cuando se acaba la seleccion de personajes
 * 
 * realamente la informacion del personaje y jugador que se da solo sirve para control interno en caso de hacer un control de Trampas
 * 
 */
import jade.content.AgentAction;
import conceptos.Jugador;
import conceptos.Personaje;

public class NotificarFinTurnoJugador implements AgentAction {

	private Jugador jugador;
	private Personaje personaje;
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Personaje getPersonaje() {
		return personaje;
	}
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	
}