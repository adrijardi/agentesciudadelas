package conceptos;

import jade.content.Concept;

public class Tablero implements Concept {
	
	private Integer ronda;
	private Jugador jugador1;
	private Jugador jugador2;
	private Jugador jugador3;
	private Jugador jugador4;
	private Jugador corona;
	
	public Integer getRonda() {
		return ronda;
	}
	public void setRonda(Integer ronda) {
		this.ronda = ronda;
	}
	public Jugador getJugador1() {
		return jugador1;
	}
	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}
	public Jugador getJugador2() {
		return jugador2;
	}
	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}
	public Jugador getJugador3() {
		return jugador3;
	}
	public void setJugador3(Jugador jugador3) {
		this.jugador3 = jugador3;
	}
	public Jugador getJugador4() {
		return jugador4;
	}
	public void setJugador4(Jugador jugador4) {
		this.jugador4 = jugador4;
	}
	public Jugador getCorona() {
		return corona;
	}
	public void setCorona(Jugador corona) {
		this.corona = corona;
	}
}