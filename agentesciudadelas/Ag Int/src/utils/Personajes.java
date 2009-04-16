package utils;

import java.util.LinkedList;
import java.util.Random;

import tablero.EstadoPartida;
import conceptos.Personaje;

public enum Personajes {
	PREVIO("previo",0,TipoDistrito.NULL),
	ASESINO("Asesino",1,TipoDistrito.NULL),
	LADRON("Ladron",2,TipoDistrito.NULL),
	MAGO("Mago",3,TipoDistrito.NULL),
	REY("Rey",4,TipoDistrito.NOBLE),
	OBISPO("Obispo",5,TipoDistrito.RELIGIOSO),
	MERCADER("Mercader",6,TipoDistrito.COMERCIAL),
	ARQUITECTO("Arquitecto",7,TipoDistrito.NULL),
	CONDOTIERO("Condotiero",8,TipoDistrito.MILITAR);
	
	private final Personaje pj;
	private final int turno;
	
	public Personaje getPj() {
		return pj;
	}
	
	private Personajes(String nombre, int turno, TipoDistrito tp) {
		pj = new Personaje();
		pj.setNombre(nombre);
		pj.setTurno(turno);
		pj.setColor(tp.getColor());
		this.turno = turno;
	}
	
	private static Personajes getPersonajeByTurno(int turno){
		Personajes ret;
		switch (turno) {
		case 1:
			ret = Personajes.ASESINO;
			break;
		case 2:
			ret = Personajes.LADRON;
			break;
		case 3:
			ret = Personajes.MAGO;
			break;
		case 4:
			ret = Personajes.REY;
			break;
		case 5:
			ret = Personajes.OBISPO;
			break;
		case 6:
			ret = Personajes.MERCADER;
			break;
		case 7:
			ret = Personajes.ARQUITECTO;
			break;
		case 8:
			ret = Personajes.CONDOTIERO;
			break;

		default:
			ret = null;
			break;
		}
		return ret;
	}

	public static String getNombre(int num){
		String ret;
		switch (num) {
		case 1:
			ret = Personajes.ASESINO.name();
			break;
		case 2:
			ret = Personajes.LADRON.name();
			break;
		case 3:
			ret = Personajes.MAGO.name();
			break;
		case 4:
			ret = Personajes.REY.name();
			break;
		case 5:
			ret = Personajes.OBISPO.name();
			break;
		case 6:
			ret = Personajes.MERCADER.name();
			break;
		case 7:
			ret = Personajes.ARQUITECTO.name();
			break;
		case 8:
			ret = Personajes.CONDOTIERO.name();
			break;

		default:
			ret = null;
			break;
		}
		return ret;
	}

	public Personajes next() {
		return getPersonajeByTurno(turno+1);
	}
	
	public static Personaje[] difinirPersonajesDescartados(){
		Personaje [] ret = new Personaje[2];
		Random r = new Random();
		LinkedList<Personajes> llp = getPersonajesMenosRey();
		ret[0] = llp.remove(r.nextInt(llp.size())).getPj();
		ret[1] = llp.remove(r.nextInt(llp.size())).getPj();
		llp.add(REY);
		llp.remove(r.nextInt(llp.size()));
		
		EstadoPartida.getInstance().setPjDisponibles(llp);
		
		return ret;
	}
	
	private static LinkedList<Personajes> getPersonajesMenosRey(){
		LinkedList<Personajes> ret = new LinkedList<Personajes>();
		ret.add(ASESINO);
		ret.add(LADRON);
		ret.add(MAGO);
		ret.add(OBISPO);
		ret.add(MERCADER);
		ret.add(ARQUITECTO);
		ret.add(CONDOTIERO);
		return ret;
	}

	public static Personajes getPersonajeByPJ(Personaje pj2) {
		return Personajes.valueOf(pj2.getNombre().toUpperCase());
	}

	public boolean isPersonaje(Personaje personaje) {
		return getPj().equals(personaje);
	}

	public static LinkedList<Personaje> getNewListaPersonajes() {
		LinkedList<Personaje> llp = new LinkedList<Personaje>();
		llp.add(ASESINO.getPj());
		llp.add(LADRON.getPj());
		llp.add(MAGO.getPj());
		llp.add(REY.getPj());
		llp.add(OBISPO.getPj());
		llp.add(MERCADER.getPj());
		llp.add(ARQUITECTO.getPj());
		llp.add(CONDOTIERO.getPj());
		return llp;
	}

	
}
