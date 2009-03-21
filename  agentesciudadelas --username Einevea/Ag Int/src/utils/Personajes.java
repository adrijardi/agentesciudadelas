package utils;

import conceptos.Personaje;

public enum Personajes {
	ASESINO("Asesino",1,TipoDistrito.NULL),
	LADRON("Ladrón",2,TipoDistrito.NULL),
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

}
