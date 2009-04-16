package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;
import utils.Personajes;
import utils.TipoDistrito;
import acciones.OfertarPersonajes;

import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Personaje;

public class JugadorAdri extends AgJugador {
	
	@Override
	public Distrito[] descartaDistritos(List distritos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Distrito getDistritoConstruir() {
		Distrito ret = null;
		boolean nuevoColor = false;
		for (Distrito distrito : mano) {
			int coste = distrito.getCoste();
			if(ret == null || (coste > ret.getCoste() && coste <= monedas) ){
				if(!nuevoColor || !tengoColor(distrito))
					ret = distrito;
			}
		}
		return ret;
		// TODO puede devolver null, en ese caso no se enviaría el mensaje.
	}

	@Override
	public Personaje getPersonajeMatar() {
		int objetivo = (int)(Math.random()*2);
		if(objetivo == 0)
			return Personajes.ARQUITECTO.getPj();
		return Personajes.MERCADER.getPj();
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
		if(seleccionarMonedasOCartas()){
			ret = new PedirCartas(this, ret, msg_sender);
		}else{
			ret = new PedirMonedas(this, ret, msg_sender);
		}
		
		return ret;
	}

	@Override
	public boolean seleccionarMonedasOCartas() {
		if(mano.size() == 0 && pj_actual != Personajes.MAGO.getPj())
			return true;
		return false;
	}

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		List personajes = contenido.getDisponibles();
		if(mano.size() == 0 && personajes.contains(Personajes.MAGO.getPj()))
			return Personajes.MAGO.getPj();
		
		if(monedas < 6 && personajes.contains(Personajes.MERCADER.getPj()))
			return Personajes.MERCADER.getPj();
		
		if(monedas > 4 && personajes.contains(Personajes.ARQUITECTO.getPj()))
			return Personajes.ARQUITECTO.getPj();
		
		if(personajes.size() <= 2 && personajes.contains(Personajes.REY.getPj()))
			return Personajes.REY.getPj();
		
		if(cuentaColor(TipoDistrito.COMERCIAL) > 2 && personajes.contains(Personajes.MERCADER.getPj()))
			return Personajes.MERCADER.getPj();
		
		if(cuentaColor(TipoDistrito.NOBLE) > 2 && personajes.contains(Personajes.REY.getPj()))
			return Personajes.REY.getPj();
		
		if(cuentaColor(TipoDistrito.MILITAR) > 2 && personajes.contains(Personajes.CONDOTIERO.getPj()))
			return Personajes.CONDOTIERO.getPj();
		
		if(cuentaColor(TipoDistrito.RELIGIOSO) > 2 && personajes.contains(Personajes.OBISPO.getPj()))
			return Personajes.OBISPO.getPj();
		
		if(personajes.contains(Personajes.ASESINO.getPj()))
			return Personajes.ASESINO.getPj();
		
		return Personajes.LADRON.getPj();
	}
	
	private boolean tengoColor(Distrito d){
		boolean ret = false;
		for (Distrito construido : construidas) {
			if(d.getColor().compareTo(construido.getColor()) == 0)
				ret = true;
		}
		return ret;
	}
	
	private int cuentaColor(TipoDistrito color){
		int ret = 0;
		for (Distrito construido : construidas) {
			if(color.getColor().compareTo(construido.getColor()) == 0)
				ret++;
		}
		return ret;
	}

}
