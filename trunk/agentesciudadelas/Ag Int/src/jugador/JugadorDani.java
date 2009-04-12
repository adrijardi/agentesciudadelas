package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

import acciones.OfertarPersonajes;

import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Personaje;

public class JugadorDani extends AgJugador {
	
	 
	

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		// Se selecciona un personaje aleatorio de los que llegan:
		int sel = new Random().nextInt(contenido.getDisponibles().size());
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
		
		// Construir distrito
		ret = new ConstruirDistritoJugador(this, ret, msg_sender);
		
		// Accion jugador falta PedirCartas 
		if(mano.size()==0){
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
			Random r= new Random();
			ret = dist[r.nextInt(dist.length)];
		}
		return ret;
	}
	
	

}
