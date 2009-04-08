package conceptos;

import jade.content.Concept;

public class Jugador implements Concept,Comparable<Jugador> {

	private String nombre;
	private Integer puntos;
	private Integer mano;
	private Integer monedas;

	public Integer getMano() {
		return mano;
	}

	public Integer getMonedas() {
		return monedas;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setMano(Integer mano) {
		this.mano = mano;
	}

	public void setMonedas(Integer monedas) {
		this.monedas = monedas;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	public int compareTo(Jugador o) {
		return nombre.compareTo(o.nombre);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Jugador)
			if(nombre.compareTo(((Jugador)obj).nombre) == 0)
				return true;
		return super.equals(obj);
	}
}