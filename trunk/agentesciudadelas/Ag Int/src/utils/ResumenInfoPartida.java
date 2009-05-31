package utils;

import jade.util.leap.ArrayList;
import jade.util.leap.LinkedList;

import java.awt.List;

import acciones.InfoPartida;

import conceptos.Jugador;
import conceptos.Personaje;

public class ResumenInfoPartida {

	Jugador _yo;
	int _miPosicion;
	Personaje[] _personajes=new Personaje[4];
	Jugador[] _jugadores=new Jugador[4];
	LinkedList[] _distrotosConstruidos=new LinkedList[4];
	
	public static ResumenInfoPartida Instance=null;
	
	private ResumenInfoPartida(InfoPartida msgInfo, Personaje pj_actual) {
		
		_jugadores[0]=msgInfo.getJugador1();
		_jugadores[1]=msgInfo.getJugador1();
		_jugadores[2]=msgInfo.getJugador1();
		_jugadores[3]=msgInfo.getJugador1();
		_personajes[0]=null;
		_personajes[1]=null;
		_personajes[2]=null;
		_personajes[3]=null;
		if(msgInfo.getJugoP1()){
			_personajes[0]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP2()){
			_personajes[1]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP3()){
			_personajes[2]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP4()){
			_personajes[3]=msgInfo.getPersonaje1();
		}
		for(int i=0;i<_distrotosConstruidos.length;i++) 
			_distrotosConstruidos[i]=new LinkedList();
		
		for(int i=0;i<msgInfo.getDistritosJ1().size();i++){
			_distrotosConstruidos[0].add(msgInfo.getDistritosJ1().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ2().size();i++){
			_distrotosConstruidos[1].add(msgInfo.getDistritosJ2().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ3().size();i++){
			_distrotosConstruidos[2].add(msgInfo.getDistritosJ3().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ4().size();i++){
			_distrotosConstruidos[3].add(msgInfo.getDistritosJ4().get(i));
		}
		
		for(int i=0;i<_personajes.length;i++){
			if(_personajes.equals(pj_actual)) _miPosicion=i;
		}
		_yo=_jugadores[_miPosicion];
	}
	
	private ResumenInfoPartida(InfoPartida msgInfo) {
		
		_jugadores[0]=msgInfo.getJugador1();
		_jugadores[1]=msgInfo.getJugador1();
		_jugadores[2]=msgInfo.getJugador1();
		_jugadores[3]=msgInfo.getJugador1();
		_personajes[0]=null;
		_personajes[1]=null;
		_personajes[2]=null;
		_personajes[3]=null;
		if(msgInfo.getJugoP1()){
			_personajes[0]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP2()){
			_personajes[1]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP3()){
			_personajes[2]=msgInfo.getPersonaje1();
		}
		if(msgInfo.getJugoP4()){
			_personajes[3]=msgInfo.getPersonaje1();
		}
		for(int i=0;i<_distrotosConstruidos.length;i++) 
			_distrotosConstruidos[i]=new LinkedList();
		
		for(int i=0;i<msgInfo.getDistritosJ1().size();i++){
			_distrotosConstruidos[0].add(msgInfo.getDistritosJ1().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ2().size();i++){
			_distrotosConstruidos[1].add(msgInfo.getDistritosJ2().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ3().size();i++){
			_distrotosConstruidos[2].add(msgInfo.getDistritosJ3().get(i));
		}
		for(int i=0;i<msgInfo.getDistritosJ4().size();i++){
			_distrotosConstruidos[3].add(msgInfo.getDistritosJ4().get(i));
		}
		
	}
	
	public static ResumenInfoPartida getInstance(InfoPartida msgInfo, Personaje pj_actual){
		if(Instance==null) Instance=new ResumenInfoPartida(msgInfo, pj_actual);
		else Instance=new ResumenInfoPartida(msgInfo);
		return Instance;
	}

	public Jugador get_yo() {
		return _yo;
	}

	public int get_miPosicion() {
		return _miPosicion;
	}

	public Personaje[] get_personajes() {
		return _personajes;
	}

	public Jugador[] get_jugadores() {
		return _jugadores;
	}
	/*
	public List[] get_distrotosConstruidos() {
		return _distrotosConstruidos;
	}
	*/
	public LinkedList getDistritos(int i){
		return _distrotosConstruidos[i];
	}
	
	
	
	
}
