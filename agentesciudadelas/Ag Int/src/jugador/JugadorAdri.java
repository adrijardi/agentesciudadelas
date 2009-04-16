package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;
import utils.Personajes;
import utils.TipoDistrito;
import acciones.Matar;
import acciones.OfertarPersonajes;

import comportamientos_jugador.AsesinarPersonaje;
import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.HabilidadArquitecto;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirCobrarCondotierro;
import comportamientos_jugador.PedirCobrarMercader;
import comportamientos_jugador.PedirCobrarObispo;
import comportamientos_jugador.PedirCobrarRey;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Personaje;

public class JugadorAdri extends AgJugador {
	
	@Override
	public Distrito[] descartaDistritos(List distritos) {
		Distrito elegido = (Distrito)distritos.get(0);
		boolean tengoColor = tengoColor(elegido);
		int coste = elegido.getCoste();
		
		for (int i = 1; i < distritos.size(); i++) {
			if(!tengoColor && !tengoColor((Distrito)distritos.get(i))){
				if(coste < ((Distrito)distritos.get(i)).getCoste()){
					elegido = (Distrito)distritos.get(i);
					tengoColor = tengoColor(elegido);
					coste = elegido.getCoste();
				}
			}
		}
		distritos.remove(elegido);
		
		Distrito[] ret = new Distrito[distritos.size()];
		for (int i = 0; i < distritos.size(); i++) {
			ret[i] = (Distrito)distritos.get(i);
		}
		return ret;
	}

	@Override
	public Distrito getDistritoConstruir() {
		Distrito ret = null;
		Distrito [] construibles = getDistritosConstruibles();
		boolean nuevoColor = false;
		for (Distrito distrito : construibles) {
			if(ret != null){
				if(!nuevoColor || !tengoColor(distrito)){
					if(ret.getCoste() < distrito.getCoste())
						ret = distrito;
				}
			}else
				ret = distrito;
		}
		return ret;
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
		
		switch(Personajes.getPersonajeByPJ(pj_actual)){
			case ASESINO:
				ret = new AsesinarPersonaje(this, ret, msg_sender);
				break;
			case LADRON:
				break;
			case MAGO:
				break;
			case REY:
				ret = new PedirCobrarRey(this, ret, msg_sender);
				break;
			case OBISPO:
				ret = new PedirCobrarObispo(this, ret, msg_sender);
				break;
			case MERCADER:
				ret = new PedirCobrarMercader(this, ret, msg_sender);
				break;
			case ARQUITECTO:
				ret = new HabilidadArquitecto(this, ret, msg_sender);
				break;
			case CONDOTIERO:
				ret = new PedirCobrarCondotierro(this, ret, msg_sender);
				break;
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
			return pj_actual = Personajes.MAGO.getPj();
		
		if(monedas < 6 && personajes.contains(Personajes.MERCADER.getPj()))
			return pj_actual = Personajes.MERCADER.getPj();
		
		if(monedas > 4 && personajes.contains(Personajes.ARQUITECTO.getPj()))
			return pj_actual = Personajes.ARQUITECTO.getPj();
		
		if(personajes.size() <= 2 && personajes.contains(Personajes.REY.getPj()))
			return pj_actual = Personajes.REY.getPj();
		
		if(cuentaColor(TipoDistrito.COMERCIAL) > 2 && personajes.contains(Personajes.MERCADER.getPj()))
			return pj_actual = Personajes.MERCADER.getPj();
		
		if(cuentaColor(TipoDistrito.NOBLE) > 2 && personajes.contains(Personajes.REY.getPj()))
			return pj_actual = Personajes.REY.getPj();
		
		if(cuentaColor(TipoDistrito.MILITAR) > 2 && personajes.contains(Personajes.CONDOTIERO.getPj()))
			return pj_actual = Personajes.CONDOTIERO.getPj();
		
		if(cuentaColor(TipoDistrito.RELIGIOSO) > 2 && personajes.contains(Personajes.OBISPO.getPj()))
			return pj_actual = Personajes.OBISPO.getPj();
		
		if(personajes.contains(Personajes.ASESINO.getPj()))
			return pj_actual = Personajes.ASESINO.getPj();
		
		if(personajes.contains(Personajes.LADRON.getPj()))
			return pj_actual = Personajes.LADRON.getPj();
		
		return pj_actual = (Personaje)personajes.get(0);
		
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
