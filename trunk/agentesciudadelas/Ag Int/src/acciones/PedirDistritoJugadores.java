package acciones;

import jade.content.AgentAction;
import jade.util.leap.ArrayList;
import conceptos.Jugador;
import conceptos.Distrito;

public class PedirDistritoJugadores implements AgentAction{
	
	Jugador jugador1;
	Distrito[] distritos1;
	Jugador jugador2;
	Distrito[] distritos2;
	Jugador jugador3;
	Distrito[] distritos3;

	public void setDistritos1(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritos1 = d;
	}
	
	public void setDistritos1(Distrito[] d){
		distritos1 = d;
	}
	
	public jade.util.leap.List getDistritos1(){
		ArrayList al = new ArrayList(distritos1.length);
		for (int i = 0; i < distritos1.length; i++) {
			al.add(i, distritos1[i]);
		}
		return al;
	}
	
	public void setDistritos2(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritos2 = d;
	}
	
	public void setDistritos2(Distrito[] d){
		distritos2 = d;
	}
	
	public jade.util.leap.List getDistritos2(){
		ArrayList al = new ArrayList(distritos2.length);
		for (int i = 0; i < distritos2.length; i++) {
			al.add(i, distritos2[i]);
		}
		return al;
	}
	
	public void setDistritos3(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritos3 = d;
	}
	
	public void setDistritos3(Distrito[] d){
		distritos3 = d;
	}
	
	public jade.util.leap.List getDistritos3(){
		ArrayList al = new ArrayList(distritos3.length);
		for (int i = 0; i < distritos3.length; i++) {
			al.add(i, distritos3[i]);
		}
		return al;
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
	
	
}
