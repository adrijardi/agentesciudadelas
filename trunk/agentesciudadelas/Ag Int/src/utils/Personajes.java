package utils;

import java.util.LinkedList;
import java.util.Random;

import conceptos.Personaje;

public enum Personajes {
	ASESINO("Asesino",1,TipoDistrito.NULL),
	LADRON("Ladron",2,TipoDistrito.NULL),
	MAGO("Mago",3,TipoDistrito.NULL),
	REY("Rey",4,TipoDistrito.NOBLE),
	OBISPO("Obispo",5,TipoDistrito.RELIGIOSO),
	MERCADER("Mercader",6,TipoDistrito.COMERCIAL),
	ARQUITECTO("Arquitecto",7,TipoDistrito.NULL),
	CONDOTIERO("Condotiero",8,TipoDistrito.MILITAR);
	
	private final Personaje pj;
	
	public Personaje getPj() {
		return pj;
	}
	
	private Personajes(String nombre, int turno, TipoDistrito tp) {
		pj = new Personaje();
		pj.setNombre(nombre);
		pj.setTurno(turno);
		pj.setColor(tp.getColor());
	}
	
	public static Personaje getPersonajeByTurno(int turno){
		Personaje ret;
		switch (turno) {
		case 1:
			ret = Personajes.ASESINO.getPj();
			break;
		case 2:
			ret = Personajes.LADRON.getPj();
			break;
		case 3:
			ret = Personajes.MAGO.getPj();
			break;
		case 4:
			ret = Personajes.REY.getPj();
			break;
		case 5:
			ret = Personajes.OBISPO.getPj();
			break;
		case 6:
			ret = Personajes.MERCADER.getPj();
			break;
		case 7:
			ret = Personajes.ARQUITECTO.getPj();
			break;
		case 8:
			ret = Personajes.CONDOTIERO.getPj();
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
}
