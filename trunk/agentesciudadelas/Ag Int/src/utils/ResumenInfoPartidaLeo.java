package utils;

import jade.util.leap.ArrayList;
import jade.util.leap.LinkedList;

import java.awt.List;

import acciones.InfoPartida;

import conceptos.Jugador;
import conceptos.Personaje;
//Almacena la informacion de la partida, es decir el conocimineto para el AgenteLeo
public class ResumenInfoPartidaLeo {

	Jugador leo;
	int posicion = -1;
	Personaje[] personajes = new Personaje[4];
	Jugador[] jugadores = new Jugador[4];
	LinkedList[] disConstruidos = new LinkedList[4];
	
	public static ResumenInfoPartidaLeo Instance=null;
	private static boolean inicializado=false;
	
	private ResumenInfoPartidaLeo(InfoPartida msgInfo, String nombre) {
		
		jugadores[0]=msgInfo.getJugador1();
		jugadores[1]=msgInfo.getJugador2();
		jugadores[2]=msgInfo.getJugador3();
		jugadores[3]=msgInfo.getJugador4();
		personajes[0]=null;
		personajes[1]=null;
		personajes[2]=null;
		personajes[3]=null;
		if(msgInfo.getJugoP1()){
			personajes[0]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP2()){
			personajes[1]=msgInfo.getPersonaje2();
		}
		if(msgInfo.getJugoP3()){
			personajes[2]=msgInfo.getPersonaje3();
		}
		if(msgInfo.getJugoP4()){
			personajes[3]=msgInfo.getPersonaje4();
		}
		for(int i=0;i<disConstruidos.length;i++) 
			disConstruidos[i]=new LinkedList();
		
		for(int i=0;i<msgInfo.getDistritosJ1().size();i++){
			disConstruidos[0].add(msgInfo.getDistritosJ1().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ2().size();i++){
			disConstruidos[1].add(msgInfo.getDistritosJ2().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ3().size();i++){
			disConstruidos[2].add(msgInfo.getDistritosJ3().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ4().size();i++){
			disConstruidos[3].add(msgInfo.getDistritosJ4().get(i));
		}
		for(int i=0;i<jugadores.length;i++){
			if(jugadores[i]!=null){
				if(jugadores[i].getNombre().compareToIgnoreCase(nombre)==0){
					this.posicion = i;
					leo =jugadores[posicion];
					inicializado=true;
				}
			}
		}
		
	}
	
	public void darValores(String nombre){
		if(!inicializado){
			for(int i=0;i<jugadores.length;i++){
				if(jugadores[i]!=null){
					if(jugadores[i].getNombre().compareToIgnoreCase(nombre)==0){
						posicion = i;
						leo = jugadores[posicion];
						inicializado=true;
					}
				}
			}
		}
	}
	
	public void actualizarPartida(InfoPartida msgInfo) {
		
		jugadores[0]=msgInfo.getJugador1();
		jugadores[1]=msgInfo.getJugador2();
		jugadores[2]=msgInfo.getJugador3();
		jugadores[3]=msgInfo.getJugador4();
		personajes[0]=null;
		personajes[1]=null;
		personajes[2]=null;
		personajes[3]=null;
		if(msgInfo.getJugoP1()){
			personajes[0]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP2()){
			personajes[1]=msgInfo.getPersonaje2();
		}
		if(msgInfo.getJugoP3()){
			personajes[2]=msgInfo.getPersonaje3();
		}
		if(msgInfo.getJugoP4()){
			personajes[3]=msgInfo.getPersonaje4();
		}
		for(int i=0;i<disConstruidos.length;i++) 
			disConstruidos[i]=new LinkedList();
		
		for(int i=0;i<msgInfo.getDistritosJ1().size();i++){
			disConstruidos[0].add(msgInfo.getDistritosJ1().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ2().size();i++){
			disConstruidos[1].add(msgInfo.getDistritosJ2().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ3().size();i++){
			disConstruidos[2].add(msgInfo.getDistritosJ3().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ4().size();i++){
			disConstruidos[3].add(msgInfo.getDistritosJ4().get(i));
		}
		
	}
	
	public static ResumenInfoPartidaLeo getInstance(InfoPartida msgInfo, String nombre){
		if(Instance==null) Instance=new ResumenInfoPartidaLeo(msgInfo, nombre);
		return Instance;
	}


	public int get_miPosicion() {
		return posicion;
	}

	public Personaje[] getpersonajes() {
		return personajes;
	}

	public Jugador[] getjugadores() {
		return jugadores;
	}
	
	public LinkedList getDistritos(int i){
		return disConstruidos[i];
	}
	
	
	public boolean isInicializado(){
		return inicializado;
	}

}
