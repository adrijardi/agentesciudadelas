package acciones;

import java.util.Vector;

import jade.util.leap.ArrayList;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class InfoPartida {

	private Jugador jugador1;
	private Jugador jugador2;
	private Jugador jugador3;
	private Jugador jugador4;
	private Personaje personaje1;
	private boolean jugoP1;
	private Personaje personaje2;
	private boolean jugoP2;
	private Personaje personaje3;
	private boolean jugoP3;
	private Personaje personaje4;
	private boolean jugoP4;
	private Distrito[] distritosJ1;
	private Distrito[] distritosJ2;
	private Distrito[] distritosJ3;
	private Distrito[] distritosJ4;
	
	
	
	
	public boolean getJugoP1() {
		return jugoP1;
	}

	public void setJugoP1(boolean jugoP1) {
		this.jugoP1 = jugoP1;
	}

	public boolean getJugoP2() {
		return jugoP2;
	}

	public void setJugoP2(boolean jugoP2) {
		this.jugoP2 = jugoP2;
	}

	public boolean getJugoP3() {
		return jugoP3;
	}

	public void setJugoP3(boolean jugoP3) {
		this.jugoP3 = jugoP3;
	}

	public boolean getJugoP4() {
		return jugoP4;
	}

	public void setJugoP4(boolean jugoP4) {
		this.jugoP4 = jugoP4;
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
	
	public Personaje getPersonaje1() {
		return personaje1;
	}
	
	public void setPersonaje1(Personaje personaje1) {
		this.personaje1 = personaje1;
	}
	
	public Personaje getPersonaje2() {
		return personaje2;
	}
	
	public void setPersonaje2(Personaje personaje2) {
		this.personaje2 = personaje2;
	}
	
	public Personaje getPersonaje3() {
		return personaje3;
	}
	
	public void setPersonaje3(Personaje personaje3) {
		this.personaje3 = personaje3;
	}
	
	public Personaje getPersonaje4() {
		return personaje4;
	}
	
	public void setPersonaje4(Personaje personaje4) {
		this.personaje4 = personaje4;
	}
	
	public void setDistritosJ1(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritosJ1 = d;
	}
	
	public void setDistritosJ1(Distrito[] d){
		distritosJ1 = d;
	}
	
	public jade.util.leap.List getDistritosJ1(){
		ArrayList al;
		if(distritosJ1 != null){
			 al= new ArrayList(distritosJ1.length);
			for (int i = 0; i < distritosJ1.length; i++) {
				al.add(i, distritosJ1[i]);
			}
		}else{
			al = new ArrayList(0);
		}
		return al;
	}
	
	public void setDistritosJ2(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritosJ2 = d;
	}
	
	public void setDistritosJ2(Distrito[] d){
		distritosJ2 = d;
	}
	
	public jade.util.leap.List getDistritosJ2(){
		ArrayList al;
		if(distritosJ2 != null){
			 al= new ArrayList(distritosJ2.length);
			for (int i = 0; i < distritosJ2.length; i++) {
				al.add(i, distritosJ2[i]);
			}
		}else{
			al = new ArrayList(0);
		}
		return al;
	}
	
	public void setDistritosJ3(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritosJ3 = d;
	}
	
	public void setDistritosJ3(Distrito[] d){
		distritosJ3 = d;
	}
	
	public jade.util.leap.List getDistritosJ3(){
		ArrayList al;
		if(distritosJ3 != null){
			 al= new ArrayList(distritosJ3.length);
			for (int i = 0; i < distritosJ3.length; i++) {
				al.add(i, distritosJ3[i]);
			}
		}else{
			al = new ArrayList(0);
		}
		return al;
	}
	
	public void setDistritosJ4(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritosJ4 = d;
	}
	
	public void setDistritosJ4(Distrito[] d){
		distritosJ4 = d;
	}
	
	public jade.util.leap.List getDistritosJ4(){
		ArrayList al;
		if(distritosJ4 != null){
			 al= new ArrayList(distritosJ4.length);
			for (int i = 0; i < distritosJ4.length; i++) {
				al.add(i, distritosJ4[i]);
			}
		}else{
			al = new ArrayList(0);
		}
		return al;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Vector<Distrito[]> store = new Vector<Distrito[]>(4,1);
		store.add(distritosJ1);
		store.add(distritosJ2);
		store.add(distritosJ3);
		store.add(distritosJ4);
		for(int j = 0; j < store.size(); j++){
			sb.append("Distritos Jugador " + j+1 + " :");
			for (int i = 0; i < store.get(j).length; i++) {
				if(j == 0){
					sb.append(distritosJ1[i].getNombre());
					sb.append(" ");
				}
				if(j == 1){
					sb.append(distritosJ2[i].getNombre());
					sb.append(" ");
				}
				if(j == 2){
					sb.append(distritosJ3[i].getNombre());
					sb.append(" ");
				}
				if(j == 3){
					sb.append(distritosJ4[i].getNombre());
					sb.append(" ");
				}
			}
		}
		return sb.toString();
	}
}
