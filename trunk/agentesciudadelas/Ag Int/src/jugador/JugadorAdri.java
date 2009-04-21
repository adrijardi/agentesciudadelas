package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.ArrayList;
import java.util.LinkedList;

import utils.Personajes;
import utils.TipoDistrito;
import acciones.DestruirDistrito;
import acciones.OfertarPersonajes;
import acciones.PedirDistritoJugadores;

import comportamientos_jugador.AsesinarPersonaje;
import comportamientos_jugador.CambiarCartasJugador;
import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.DestruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.HabilidadArquitecto;
import comportamientos_jugador.HabilidadLadron;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirCobrarCondotierro;
import comportamientos_jugador.PedirCobrarMercader;
import comportamientos_jugador.PedirCobrarObispo;
import comportamientos_jugador.PedirCobrarRey;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Jugador;
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
		Personajes aux = Personajes.ARQUITECTO;
		int naux = 0;
		
		if(objetivo == 0 && destapados[0] != aux && destapados[1] != aux)
			return Personajes.ARQUITECTO.getPj();
		
		aux = Personajes.MERCADER;
		if(destapados[0] != aux && destapados[1] != aux)
			return Personajes.MERCADER.getPj();
		
		LinkedList<Personaje> pLista = Personajes.getNewListaPersonajes();
		while(naux < 10){
			objetivo = (int)(Math.random()*6+2);
			if(pLista.get(objetivo) != destapados[0].getPj() && pLista.get(objetivo) != destapados[1].getPj())
				return pLista.get(objetivo);
			naux++;
		}
		return Personajes.MAGO.getPj();
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
				ret = new HabilidadLadron(this, ret, msg_sender);
				break;
			case MAGO:
				if(cartasManoNoConstruidas() == 0)
					ret = new CambiarCartasJugador(this, ret, msg_sender);
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
				ret = new DestruirDistritoJugador(this, ret, msg_sender);
				ret = new PedirCobrarCondotierro(this, ret, msg_sender);
				break;
		}
		
		return ret;
	}

	@Override
	public boolean seleccionarMonedasOCartas() {
		if(cartasManoNoConstruidas() == 0 && pj_actual != Personajes.MAGO.getPj())
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

	@Override
	public void getDistritoDestruir(PedirDistritoJugadores pd, DestruirDistrito dd) {
		Distrito elegido = null;
		Jugador jelec = null;
		Distrito [] distritos = (Distrito[])pd.getDistritos1().toArray();
		for (int i = 0; elegido != null && i < distritos.length; i++) {
			if(distritos[i].getCoste() == 1){
				elegido = distritos[i];
				jelec = pd.getJugador1();
			}
		}
		if(elegido != null){
			distritos = (Distrito[])pd.getDistritos2().toArray();
			for (int i = 0; elegido != null && i < distritos.length; i++) {
				if(distritos[i].getCoste() == 1){
					elegido = distritos[i];
					jelec = pd.getJugador2();
				}
			}
			if(elegido != null){
				distritos = (Distrito[])pd.getDistritos3().toArray();
				for (int i = 0; elegido != null && i < distritos.length; i++) {
					if(distritos[i].getCoste() == 1){
						elegido = distritos[i];
						jelec = pd.getJugador3();
					}
				}
			}
		}
		if(elegido != null){
			dd.setDistrito(elegido);
			dd.setJugador(jelec);
			dd.setPago(0);
		}else{
			dd.setJugador(pd.getJugador1());
			dd.setDistrito((Distrito)pd.getDistritos1().get(0));
			dd.setPago(-1);
		}
	}

	@Override
	public Jugador seleccionarJugadorCambiarCartas(Jugador jug1, Jugador jug2, Jugador jug3) {
		int num;
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		if(jug1.getMano() > 0)
			jugadores.add(jug1);
		if(jug2.getMano() > 0)
			jugadores.add(jug2);
		if(jug3.getMano() > 0)
			jugadores.add(jug3);
		
		if(jugadores.size() != 0){
			num = (int)(Math.random()*jugadores.size());
			return jugadores.get(num);
		}
		return null;
	}

	@Override
	public Personaje seleccionarPersonajeRobo() {
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		llp.remove(Personajes.LADRON.getPj());
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i]);
		}
		if(llp.contains(Personajes.ARQUITECTO.getPj()))
			return Personajes.ARQUITECTO.getPj();
		return llp.get((int)(Math.random()*llp.size()));
	}
}
