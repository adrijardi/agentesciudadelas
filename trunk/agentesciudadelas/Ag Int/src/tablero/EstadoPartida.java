package tablero;

import java.util.Random;

import conceptos.Distrito;
import conceptos.Jugador;
import jade.core.AID;

public class EstadoPartida {
	public enum EnumFase{INICIADA, SEL_PERSONAJES, JUGAR_RONDA, FINALIZAR_JUEGO;}
	private int turno;
	private EnumFase fase;
	private final int numJugador;
	private final int numPersonajes;
	private int jugActual;
	private int pjActual;
	private int corona;
	
	private int[] persoJugador;
	
	// cada jugador un agente, sin complicaciones el 0 es el ag0, el 1 es el ag1, etc...
	private ResumenJugador[] resJugadores;
	/*
	 * A�adido por Pablo
	 */
	private String nombreMuerto;
	private String nombreRobado;
	private ResumenJugador jugLadron;
	private int numJugHanJugado;
	private int destapado;
	
	/*private int [] _personajesNoDisponibles;
	private int _personajeNoDisponibleOculto;*/
	
	// Protected constructor is sufficient to suppress unauthorized calls to the constructor
	private EstadoPartida() {
		fase=EnumFase.INICIADA;
		numJugador = 4;
		numPersonajes = 8;
		turno=0;
		jugActual = 0;
		pjActual = 0;
		resJugadores=new ResumenJugador[numJugador];
		Mazo.getInstance();
		corona = -1;
		persoJugador = new int[8];
		resetPersoJugador();
		nombreMuerto=null;
		numJugHanJugado=0;
		jugLadron=null;
		//setPersonajesNoDisponibles();
		destapado = 0;
	}
	
	private void resetPersoJugador() {
		for (int i = 0; i < persoJugador.length; i++) {
			persoJugador[i] = -1;
		}
	}

	public ResumenJugador nextPersonaje(){
		ResumenJugador ret;
		int jugador = persoJugador[pjActual++];
		if(pjActual == 8)
			pjActual = 0;
			
		if(jugador == -1){
			ret = null;
		}else{
			ret = resJugadores[jugador];
		}
		return ret;
	
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
	public int getDestapado() {
		return destapado;
	}

	public void setDestapado(int destapado) {
		this.destapado = destapado;
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
			System.out.println("A�adido 1 jugador con id: "+name);
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
	
	/*
	 * 
	 * a�adido por Pablo 
	 * 
	 * 
	 */
	
	public boolean isJugarRonda() {
		if(fase.equals(EnumFase.JUGAR_RONDA))
			return true;
		return false;
	}
	
	public ResumenJugador getResJugadorActual() {
		return resJugadores[jugActual];
	}
	
	public boolean tieneDistrito(Jugador j, Distrito d){
		boolean tiene=false;
		for(int i=0;i<resJugadores.length;i++){
			if(resJugadores[i].esJugador(j.getNombre())){
				tiene=resJugadores[i].tieneDistrito(d);
			}
		}
		return tiene;
	}

	public int getCorona() {
		return corona;
	}

	public void setCorona(int corona) {
		this.corona = corona;
	}

	public int[] getPersoJugador() {
		return persoJugador;
	}

	public void setPersoJugador(int[] persoJugador) {
		this.persoJugador = persoJugador;
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
	
	public ResumenJugador getResumenJugador(String aid) {
		ResumenJugador ret = null;
		for (int i = 0; i < resJugadores.length; i++) {
			if(resJugadores[i].getIdentificador().equals(aid))
				ret = resJugadores[i];
		}
		return ret;
	}

	public String getNombreRobado() {
		return nombreRobado;
	}

	public void setNombreRobado(String nombreRobado) {
		this.nombreRobado = nombreRobado;
	}

	public ResumenJugador getJugLadron() {
		return jugLadron;
	}

	public void setJugLadron(ResumenJugador jugLadron) {
		this.jugLadron = jugLadron;
	}
	
	/*private void setPersonajesNoDisponibles(){
		Random r = new Random();
		_personajeNoDisponibleOculto = r.nextInt(8);
		_personajesNoDisponibles[0] = r.nextInt(7);
		if(_personajesNoDisponibles[0] >= _personajeNoDisponibleOculto)
			_personajesNoDisponibles[0]++;
		_personajesNoDisponibles[1] = r.nextInt(6);
		if(_personajesNoDisponibles[1] >= _personajeNoDisponibleOculto)
			_personajesNoDisponibles[1]++;
		if(_personajesNoDisponibles[1] >= _personajesNoDisponibles[0])
			_personajesNoDisponibles[1]++;
		if(_personajesNoDisponibles[1] == _personajeNoDisponibleOculto)
			_personajesNoDisponibles[1]++;
	}

	public int[] get_personajesNoDisponibles() {
		return _personajesNoDisponibles;
	}

	public void set_personajesNoDisponibles(int[] noDisponibles) {
		_personajesNoDisponibles = noDisponibles;
	}

	public int get_personajeNoDisponibleOculto() {
		return _personajeNoDisponibleOculto;
	}

	public void set_personajeNoDisponibleOculto(int noDisponibleOculto) {
		_personajeNoDisponibleOculto = noDisponibleOculto;
	}*/

	public int getPjActual() {
		return pjActual;
	}

	public void setPjActual(int pjActual) {
		this.pjActual = pjActual;
	}
	
	
}
