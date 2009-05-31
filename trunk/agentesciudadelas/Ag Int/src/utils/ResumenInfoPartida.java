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
	List[] _distrotosConstruidos=new List[4];
	
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
			_distrotosConstruidos[i]=new List();
		
		_distrotosConstruidos[0]=(List) msgInfo.getDistritosJ1();
		_distrotosConstruidos[1]=(List) msgInfo.getDistritosJ2();
		_distrotosConstruidos[2]=(List) msgInfo.getDistritosJ3();
		_distrotosConstruidos[3]=(List) msgInfo.getDistritosJ4();
		
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
			_distrotosConstruidos[i]=new List();
		
		_distrotosConstruidos[0]=(List) msgInfo.getDistritosJ1();
		_distrotosConstruidos[1]=(List) msgInfo.getDistritosJ2();
		_distrotosConstruidos[2]=(List) msgInfo.getDistritosJ3();
		_distrotosConstruidos[3]=(List) msgInfo.getDistritosJ4();
		
	}
	
	public static ResumenInfoPartida getInstance(InfoPartida msgInfo, Personaje pj_actual){
		if(Instance==null) Instance=new ResumenInfoPartida(msgInfo, pj_actual);
		else Instance=new ResumenInfoPartida(msgInfo);
		return Instance;
	}
}
