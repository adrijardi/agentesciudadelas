package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import tablero.ResumenJugador;
import utils.Personajes;

import acciones.DestruirDistrito;
import acciones.OfertarPersonajes;
import acciones.PedirDistritoJugadores;

import comportamientos.CambiarCartas;
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
		
		switch(Personajes.getPersonajeByPJ(pj_actual)){
		case ASESINO:
			ret = new AsesinarPersonaje(this, ret, msg_sender);
			break;
		case LADRON:
			ret = new HabilidadLadron(this, ret, msg_sender);
			break;
		case MAGO:
			ret=new CambiarCartasJugador(this, ret, msg_sender);
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
			// añade el comportamiento del condotiero 
			//ret = new PedirCobrarCondotierro(this, ret, msg_sender);
			//ret=new DestruirDistritoJugador(this, ret, msg_sender);
			break;
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
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i]);
			}
		return llp.get(dado.nextInt(llp.size()));
	}

	@Override
	public Jugador seleccionarJugadorCambiarCartas(Jugador jug1, Jugador jug2, Jugador jug3) {
		// TODO Auto-generated method stub
		Jugador[] jugs = new Jugador[3];
		jugs[0] = jug1;
		jugs[1] = jug2;
		jugs[2] = jug3;
		int sel = (int)Math.random()*4;
		if(sel < 4)
			return jugs[sel];
		else
			return jugs[0];
	}

	@Override
	public void getDistritoDestruir(PedirDistritoJugadores pd,
			DestruirDistrito dd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Personaje seleccionarPersonajeRobo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
