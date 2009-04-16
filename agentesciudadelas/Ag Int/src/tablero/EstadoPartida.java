package tablero;

import jade.core.AID;

import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import utils.Personajes;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class EstadoPartida {
	// Enumeracion de fases del juego
	public enum EnumFase {
		INICIADA, SEL_PERSONAJES, JUGAR_RONDA, FINALIZAR_JUEGO, FIN_JUEGO;
	}

	// Fase actual
	private EnumFase fase;
	// Numero de jugadores
	private final int numJugador = 4;
	// Numero de personajes
	private final int numPersonajes = 8;
	// Jugador del turno actual
	private ResumenJugador jugActual;
	// Personaje del turno actual para la fase JugarRonda
	private Personajes pjActual;
	// Determina el turno
	private int turno = 0;
	// Determina los puntos del el jugador que construya los 8 distritos;
	private int pnts8dist = 4;
	// Indica quien tiene la corona
	private ResumenJugador tieneCorona;

	// cada jugador un agente, sin complicaciones el 0 es el ag0, el 1 es el
	// ag1, etc...
	private Vector<ResumenJugador> resJugadores;

	private ResumenJugador nombreMuerto;
	private ResumenJugador nombreRobado;
	private ResumenJugador jugLadron;
	private LinkedList<Personajes> pjDisponibles;

	// Protected constructor is sufficient to suppress unauthorized calls to the
	// constructor
	private EstadoPartida() {
		fase = EnumFase.INICIADA;
		resJugadores = new Vector<ResumenJugador>(numJugador);
		jugActual = null;
		pjActual = null;
		Mazo.getInstance();
		resetPersoJugador();
		nombreMuerto = null;
		jugLadron = null;
	}

	/*
	 * Elimina los personajes asignados a los jugadores
	 */
	private void resetPersoJugador() {
		for (ResumenJugador rs : resJugadores) {
			rs.setPersonaje(null);
		}
	}

	/*
	 * Inicia todos los atributos necesarios para cada ronda
	 */
	public void initFaseSeleccionPersonajes() {
		System.out.println("% FASE SELECCION DE PJ");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		turno++;
		pjActual = null;
		resetPersoJugador();
		jugActual = tieneCorona;
	}

	public void initFaseJugarPersonajes() {
		System.out.println("% FASE JUGAR PERSONAJES ");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		pjActual = Personajes.PREVIO;
		jugLadron = null;
		jugActual = nextJugadorPorTurnoPersonaje();
	}

	public void terminarJuego() {
		System.out.println("% FASE FIN JUEGO ");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Vector<Distrito> vd;
		for (ResumenJugador rj : resJugadores) {
			
			System.out.println(rj.getJugador().getNombre() + ": "+ rj.getPuntos() + " pnts - Cartas construidas: " + rj.getConstruidos()+" - 5 Colores: "+rj.construidos5Colores());
			vd = rj.getConstruido();
			System.out.println("El jugador ha muerto: "+rj.getNumVecesMuerto());
			for (Distrito distrito : vd) {
				System.out.println(distrito);
			}
			System.out.println();
			
		}
	}

	/*
	 * Pasa el turno al siguiente personaje según el orden de los personajes y
	 * obtiene el resumen del Jugador
	 */
	public ResumenJugador nextJugadorPorTurnoPersonaje() {
		ResumenJugador ret = null;
		if (fase == EnumFase.JUGAR_RONDA || fase == EnumFase.FINALIZAR_JUEGO) {
			do {
				pjActual = pjActual.next();
				if (pjActual != null) {
					ret = getJugadorDesdePersonaje(pjActual.getPj());
					jugActual = ret;
				}
			} while (ret == null && pjActual != null);

			if (pjActual == null) {
				if (fase != EnumFase.FINALIZAR_JUEGO) {
					fase = EnumFase.SEL_PERSONAJES;
					initFaseSeleccionPersonajes();
				} else {
					fase = EnumFase.FIN_JUEGO;
					terminarJuego();
				}
			} else {
				if (pjActual == Personajes.LADRON)
					jugLadron = ret;
			}

		}
		return ret;
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		private final static EstadoPartida INSTANCE = new EstadoPartida();
	}

	/*
	 * Singleton
	 */
	public static EstadoPartida getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/*
	 * Obtiene el siguiente jugador en la fase de SeleccionarPersonaje
	 */
	public ResumenJugador nextJugadorPorSeleccionPersonaje() {
		ResumenJugador ret = null;
		if (fase == EnumFase.SEL_PERSONAJES) {
			int pj = (resJugadores.indexOf(jugActual) + 1) % 4;
			ret = resJugadores.get(pj);
			if (ret.equals(tieneCorona)) {
				ret = null;
				fase = EnumFase.JUGAR_RONDA;
				initFaseJugarPersonajes();
			} else {
				jugActual = ret;
			}
		}
		return ret;
	}

	public void setTieneCorona(ResumenJugador tieneCorona) {
		this.tieneCorona = tieneCorona;
	}

	public ResumenJugador getJugActual() {
		return jugActual;
	}

	public EnumFase getFase() {
		return fase;
	}

	public int getNumJugador() {
		return numJugador;
	}

	public int getNumPersonajes() {
		return numPersonajes;
	}

	public boolean isFaseIniciada() {
		if (fase.equals(EnumFase.INICIADA))
			return true;
		return false;
	}

	public boolean isPartidaLibre() {
		if (isFaseIniciada() && resJugadores.size() < 4)
			return true;
		return false;
	}

	/*
	 * Añade un nuevo jugador a la partida si no esta añadido y hay menos de 4
	 * jugadores
	 */
	public ResumenJugador addJugador(AID name) {
		ResumenJugador aux;
		boolean no_coincide = true;
		for (int i = 0; i < resJugadores.size() && no_coincide; i++) {
			aux = resJugadores.get(i);
			if (aux != null && aux.getIdentificador().equals(name)) {
				no_coincide = false;
			}
		}

		if (no_coincide && resJugadores.size() < numJugador) {
			System.out.println("A�adido 1 jugador con id: " + name);
			aux = new ResumenJugador(name);
			resJugadores.add(aux);

			if (resJugadores.size() == numJugador) {
				fase = EnumFase.SEL_PERSONAJES;
				seleccionarCoronaRandom();
				initFaseSeleccionPersonajes();
			}
		} else
			aux = null;

		return aux;
	}

	/*
	 * Obtiene el ResumenJugador que corresponde a un aid
	 */
	public ResumenJugador getResumenJugador(AID aid) {
		ResumenJugador ret = null;
		for (ResumenJugador rj : resJugadores) {
			if (rj.getIdentificador().equals(aid))
				ret = rj;
		}
		return ret;
	}
	
	/*
	 * Obtiene el ResumenJugador que corresponde a un Jugador.getName()
	 * Del AID se limpia todo lo q hay despues del @ 
	 * no probado
	 */
	public ResumenJugador getResumenJugador(Jugador j) {
		ResumenJugador ret = null;
		for (ResumenJugador rj : resJugadores) {
			if (rj.getIdentificador().toString().compareToIgnoreCase(j.toString())==0)
				ret = rj;
		}
		return ret;
	}

	/*
	 * Se selecciona un jugador para la corona
	 */
	private void seleccionarCoronaRandom() {
		Random r = new Random();
		tieneCorona = resJugadores.get(r.nextInt(numJugador));
	}

	/*
	 * TODO revisar
	 */
	public boolean isJugarRonda() {
		if (fase.equals(EnumFase.JUGAR_RONDA))
			return true;
		return false;
	}

	/*
	 * Comprueba si el Jugador j tiene el distrito d
	 */
	public boolean tieneDistrito(Jugador j, Distrito d) {
		boolean tiene = false;
		for (ResumenJugador rj : resJugadores) {
			if (rj.esJugador(j))
				tiene = rj.tieneDistrito(d);
		}
		return tiene;
	}

	public ResumenJugador getNombreMuerto() {
		return nombreMuerto;
	}

	public void setNombreMuerto(ResumenJugador nombreMuerto) {
		this.nombreMuerto = nombreMuerto;
		if(nombreMuerto != null)
			nombreMuerto.addVecesMuerto();
	}
	
	public void setNombreMuerto(Personaje personaje) {
		this.nombreMuerto = getJugadorDesdePersonaje(personaje);
		if(nombreMuerto != null)
			nombreMuerto.addVecesMuerto();
	}

	public ResumenJugador getNombreRobado() {
		return nombreRobado;
	}

	public void setNombreRobado(ResumenJugador nombreRobado) {
		this.nombreRobado = nombreRobado;
	}

	/*
	 * Devuelve un ResumenJugador a partir del personaje especificado Si no
	 * existe ningún jugador con este personaje devuelve null
	 */
	private ResumenJugador getJugadorDesdePersonaje(Personaje personaje) {
		for (ResumenJugador rj : resJugadores) {
			if (rj.getPersonaje().compareTo(personaje) == 0)
				return rj;
		}
		return null;
	}

	public ResumenJugador getJugLadron() {
		return jugLadron;
	}

	public Personajes getPjActual() {
		return pjActual;
	}

	public ResumenJugador getTieneCorona() {
		return tieneCorona;
	}

	public int getTurno() {
		return turno;
	}

	public Personaje[] getPjDisponibles() {
		Personaje[] pjs = new Personaje[pjDisponibles.size()];
		int i = 0;
		for (Personajes personaje : pjDisponibles) {
			pjs[i++] = personaje.getPj();
		}
		return pjs;
	}

	public boolean removePersonajeFromPjDisponibles(Personaje pj) {
		boolean ret = false;
		pjDisponibles.remove(Personajes.getPersonajeByPJ(pj));
		return ret;
	}

	public void setPjDisponibles(LinkedList<Personajes> pjDisponibles) {
		this.pjDisponibles = pjDisponibles;
	}

	public void comprobarFinPartida() {
		// TODO cambair fin a 8
		if (fase != EnumFase.FINALIZAR_JUEGO && jugActual.getConstruidos() >= 8) {
			fase = EnumFase.FINALIZAR_JUEGO;
		}
	}
	
	/*
	 * Funcion para obtener los puntos que se lleva el jugador que construye 8 distritos
	 */
	public int getPnts8dist() {
		int ret = pnts8dist;
		pnts8dist = 2;
		return ret;
	}

	/*
	 * devuelve un resumen de todos los jugadores
	 */
	public ResumenJugador[] getResumenJugadores(){
		ResumenJugador[] rj=new ResumenJugador[4];
		for(int i=0;i<this.resJugadores.size();i++){
			rj[i]=resJugadores.get(i);
		}
		return rj;
	}
}
