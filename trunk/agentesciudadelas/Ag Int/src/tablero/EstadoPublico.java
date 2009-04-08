package tablero;

import jade.core.AID;

import java.util.Random;


import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;
import jade.core.AID;

public class EstadoPublico {

	
	private int turno;
	private final int numJugador;
	private int jugActual;
	private int pjActual;
	private int corona;
	
	// cada jugador un agente, sin complicaciones el 0 es el ag0, el 1 es el ag1, etc...
	private ResumenJugadorPublico[] resJugadoresPublico;
	/*
	 * A�adido por Pablo
	 */
	private String nombreMuerto;
	private String nombreRobado;
	private int numJugHanJugado;
	private Personaje [] _personajesNoDisponibles = new Personaje[2];
	
	
	// Protected constructor is sufficient to suppress unauthorized calls to the constructor
	private EstadoPublico() {
		EstadoPartida ep= EstadoPartida.getInstance();
		numJugador=ep.getNumJugador();
		turno=ep.getTurno();
		resJugadoresPublico=new ResumenJugadorPublico[numJugador];
		for(int i=0;i<resJugadoresPublico.length;i++){
			resJugadoresPublico[i]=new ResumenJugadorPublico(ep.getResJugadores()[i]);
		}
	
		corona = ep.getCorona();
		nombreMuerto=ep.getNombreMuerto();
		numJugHanJugado=ep.getNumJugHanJugado();
		_personajesNoDisponibles = ep.get_personajesNoDisponibles();
		
	}
	


	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private final static EstadoPublico INSTANCE = new EstadoPublico();
	}

	public static EstadoPublico getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public int nextJugador(){
		jugActual = (++jugActual)%numJugador;
		return jugActual;
	}

	public int getJugActual() {
		return jugActual;
	}

/*
	public int getPjActual() {
		return pjActual;
	}
*/

	public int getTurno() {
		return turno;
	}
	public Personaje[] get_personajesNoDisponibles() {
		return _personajesNoDisponibles;
	}

	public void set_personajesNoDisponibles(Personaje[] noDisponibles) {
		_personajesNoDisponibles = noDisponibles;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public int getNumJugador() {
		return numJugador;
	}

	public int getCorona() {
		return corona;
	}

	public void setCorona(int corona) {
		this.corona = corona;
	}


	public String getNombreMuerto() {
		return nombreMuerto;
	}

	public void setNombreMuerto(String nombreMuerto) {
		this.nombreMuerto = nombreMuerto;
	}

	public void setJugActual(int jugActual) {
		this.jugActual = jugActual;
	}
/*
	public void setPjActual(int pjActual) {
		this.pjActual = pjActual;
	}
*/
	public int getNumJugHanJugado() {
		return numJugHanJugado;
	}

	public void setNumJugHanJugado(int numJugHanJugado) {
		this.numJugHanJugado = numJugHanJugado;
	}
	
	public String getNombreRobado() {
		return nombreRobado;
	}

	public void setNombreRobado(String nombreRobado) {
		this.nombreRobado = nombreRobado;
	}
}
