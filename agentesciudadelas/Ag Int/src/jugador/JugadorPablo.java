package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.Iterator;
import java.util.Random;

import utils.Distritos;
import utils.Personajes;
import utils.TipoDistrito;

import acciones.DestruirDistrito;
import acciones.OfertarPersonajes;
import acciones.PedirDistritoJugadores;

import comportamientos_jugador.AsesinarPersonaje;
import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.DestruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.HabilidadArquitecto;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirCobrarCondotierro;
import comportamientos_jugador.PedirCobrarMercader;
import comportamientos_jugador.PedirCobrarObispo;
import comportamientos_jugador.PedirCobrarRey;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class JugadorPablo extends AgJugador {
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
		
		// Accion jugador falta PedirCartas 
		if(seleccionarMonedasOCartas()){
			ret = new PedirCartas(this, ret, msg_sender);
		}else{
			ret = new PedirMonedas(this, ret, msg_sender);
		}
		
		// Construir distrito
		ret = new ConstruirDistritoJugador(this, ret, msg_sender);		
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
			// añade el comportamiento del condotiero 
			ret = new PedirCobrarCondotierro(this, ret, msg_sender);
			ret=new DestruirDistritoJugador(this, ret, msg_sender);
			break;
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
	public void getDistritoDestruir(PedirDistritoJugadores pd, DestruirDistrito dd) {
		// TODO Auto-generated method stub
		int num=(int)(Math.random()*3);
		int aux=0;
		int dinero=this.monedas;
		
		Distrito[] val= new Distrito[0];
		switch (num) {
		case 0:
			if(Personajes.OBISPO.toString().compareToIgnoreCase(pd.getPersonaje1().getNombre())==0) {
				// si el jug 1 es el obispo cogo el jug 2
				dd.setJugador(pd.getJugador2());
				val=validos(pd.getDistritos2());
			}else{
				dd.setJugador(pd.getJugador1());
				val=validos(pd.getDistritos1());
			}
			break;
		case 1:
			if(Personajes.OBISPO.toString().compareToIgnoreCase(pd.getPersonaje2().getNombre())==0) {
				// si el jug 2 es el obispo cogo el jug 3
				dd.setJugador(pd.getJugador3());
				val=validos(pd.getDistritos3());
			}else{
				dd.setJugador(pd.getJugador2());
				val=validos(pd.getDistritos2());
			}
			break;
		case 2:
			if(Personajes.OBISPO.toString().compareToIgnoreCase(pd.getPersonaje3().getNombre())==0) {
				// si el jug 3 es el obispo cogo el jug 1
				dd.setJugador(pd.getJugador1());
				val=validos(pd.getDistritos1());				
			}else{
				dd.setJugador(pd.getJugador3());
				val=validos(pd.getDistritos3());
			}
			break;
		default:
			break;
		}
		
		if(val.length>0){
			aux=(int)(Math.random()*val.length);
			dd.setDistrito(val[aux]);
			dd.setPago(dd.getDistrito().getCoste()-1);
			// Disminuyo las monedas xq las gasto en destruir un distrito 
			this.monedas=this.monedas-dd.getPago();
		}else{
			dd.setDistrito(Distritos.ALMACENES.getDistrito());
			dd.setPago(-1);
		}
	}	
	
	
	@Override
	public boolean seleccionarMonedasOCartas() {
		/*
		 * solo pide cartas si no tengo en la mano
		 */
		if(cartasManoNoConstruidas()==0){
			return true;
		}
		return false;
	}

	@Override
	public Personaje getPersonajeMatar() {
		int num=destapados.length+1;
		Personaje[] validos=new Personaje[8-num];
		
		boolean esta=false;
		int cont=0;
		for(int i=0;i<8;i++){
			esta=false;
			for(int j=0;j<destapados.length;j++){
				if(Personajes.getNewListaPersonajes().get(i).getNombre().compareTo(destapados[j].getPj().getNombre())==0) 
					esta=true;
				if(Personajes.getNewListaPersonajes().get(i).getNombre().compareTo(pj_actual.getNombre())==0)
					esta=true;
			}
			if(!esta){
				validos[cont]=Personajes.getNewListaPersonajes().get(i);
			}
		}
		
		int objetivo = (int)(Math.random()*validos.length);
		return validos[objetivo];
	}

	@Override
	public Jugador seleccionarJugadorCambiarCartas(Jugador jug1, Jugador jug2,Jugador jug3) {
		// TODO Auto-generated method stub
		return null;
	}
}
