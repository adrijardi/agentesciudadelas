package tablero;

import java.util.Random;

import conceptos.Jugador;
import jade.core.AID;

public class EstadoPartida {
	public enum EnumFase{ INICIADA, SEL_PERSONAJES, JUGAR_RONDA, FINALIZAR_JUEGO;}
	private int turno;
	private EnumFase fase;
	private final int numJugador;
	private final int numPersonajes;
	private int jugActual;
	private int pjActual;
	private Mazo mazo;
	private int corona;
	
	// cada jugador un agente, sin complicaciones el 0 es el ag0, el 1 es el ag1, etc...
	private ResumenJugador[] resJugadores;
	
	// Protected constructor is sufficient to suppress unauthorized calls to the constructor
	private EstadoPartida() {
		fase=EnumFase.INICIADA;
		numJugador = 4;
		numPersonajes = 8;
		turno=0;
		jugActual = 0;
		pjActual = 0;
		resJugadores=new ResumenJugador[numJugador];
		mazo = Mazo.getInstance();
		corona = -1;
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private final static EstadoPartida INSTANCE = new EstadoPartida();
	}

	public static EstadoPartida getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public int nextJugador(){
		jugActual = (++jugActual)%numJugador;
		return jugActual;
	}
	
	public int nextPersonaje(){
		pjActual = (++pjActual)%8;
		return pjActual;
	}

	public int getJugActual() {
		return jugActual;
	}


	public int getPjActual() {
		return pjActual;
	}


	public int getTurno() {
		return turno;
	}
	public void setTurno(int turno) {
		this.turno = turno;
	}
	public EnumFase getFase() {
		return fase;
	}
	public void setFase(EnumFase fase) {
		this.fase = fase;
	}
	public int getNumJugador() {
		return numJugador;
	}
	public int getNumPersonajes() {
		return numPersonajes;
	}

	public ResumenJugador[] getResJugadores() {
		return resJugadores;
	}
	public void setResJugadores(ResumenJugador[] resJugadores) {
		this.resJugadores = resJugadores;
	}

	public boolean isFaseIniciada() {
		if(fase.equals(EnumFase.INICIADA))
			return true;
		return false;
	}

	public boolean isPartidaLibre() {
		if(isFaseIniciada() && jugActual < 4)
			return true;
		return false;
	}

	public ResumenJugador addJugador(AID name) {
		ResumenJugador aux;
		boolean no_coincide = true;
		for (int i = 0; i < resJugadores.length && no_coincide; i++) {
			aux = resJugadores[i];
			if(aux != null && aux.getIdentificador().equals(name)){
				no_coincide = false;
			}
		}
		
		if(no_coincide && jugActual < numJugador){
			System.out.println("Añadido 1 jugador con id: "+name);
			resJugadores[jugActual]= new ResumenJugador(name);
			aux = resJugadores[jugActual];
			jugActual++;
			checkCambioFase(EnumFase.SEL_PERSONAJES);
		}else
			aux = null;
		
		return aux;
	}

	private void checkCambioFase(EnumFase newFase) {
		if(jugActual == numJugador){
			fase = newFase;
			jugActual = 0;
		}
		
	}

	public ResumenJugador getResumenJugador(AID aid) {
		ResumenJugador ret = null;
		for (int i = 0; i < resJugadores.length; i++) {
			if(resJugadores[i].getIdentificador().equals(aid))
				ret = resJugadores[i];
		}
		return ret;
	}

	public Jugador seleccionarCoronaRandom() {
		Random r = new Random();
		corona = r.nextInt(numJugador);
		return resJugadores[corona].getJugador();
	}
	
}
