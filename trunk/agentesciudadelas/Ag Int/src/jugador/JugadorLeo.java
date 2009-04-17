package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.Iterator;
import java.util.Random;

import tablero.ResumenJugador;
import utils.Personajes;

import acciones.OfertarPersonajes;

import comportamientos.CambiarCartas;
import comportamientos_jugador.CambiarCartasJugador;
import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.DestruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class JugadorLeo extends AgJugador {
	private final Random dado =  new Random();

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		// Se selecciona un personaje aleatorio de los que llegan:
		int sel = dado.nextInt(contenido.getDisponibles().size());
		pj_actual = (Personaje)contenido.getDisponibles().get(sel);
		return pj_actual;
	}

	@Override
	public Behaviour jugarTurno(ACLMessage msg) {
		Behaviour ret;
		printEstado();
		msg_sender = msg.getSender();
		
		//TODO faltan las acciones del jugador
		ret =  new FinTurno(this, msg_sender);
		
		// añade el comportamiento del mago 
		
		Personajes p=null;
		if(p.MAGO.isPersonaje(this.pj_actual)){
			//Cambiar con jugador o con mazo random
			ret=new CambiarCartasJugador(this, ret, msg_sender);
		}
		
		// Construir distrito
		ret = new ConstruirDistritoJugador(this, ret, msg_sender);
		
		// Accion jugador falta PedirCartas 
		if(seleccionarMonedasOCartas()){
			ret = new PedirCartas(this, ret, msg_sender);
		}else{
			ret = new PedirMonedas(this, ret, msg_sender);
		}
		
		return ret;
	}

	@Override
	public Distrito getDistritoConstruir() {
		Distrito ret = null;
		Distrito[] dist = getDistritosConstruibles();
		if(dist != null && dist.length > 0){
			ret = dist[dado.nextInt(dist.length)];
		}
		return ret;
	}

	@Override
	public Distrito[] descartaDistritos(List distritos) {
		Distrito[] descartado = new Distrito[distritos.size()-1];
		int selecc = dado.nextInt(distritos.size());
		int i = 0;
		Iterator it = distritos.iterator();
		Distrito d;
		while(it.hasNext()){
			d = (Distrito)it.next();
			if(i == selecc){
				mano.add(d);
				selecc = -1;
			}else{
				descartado[i++] = d;
			}
		}
		return descartado;
	}

	@Override
	public boolean seleccionarMonedasOCartas() {
		if(cartasManoNoConstruidas()==0){
			return true;
		}
		return false;
	}

	@Override
	public Personaje getPersonajeMatar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jugador seleccionarJugadorCambiarCartas(Jugador[] jug) {
		// TODO Auto-generated method stub
		int sel = (int)Math.random()*4;
		if(sel < jug.length)
			return jug[sel];
		else
			return jug[0];
	}
	
	

}
