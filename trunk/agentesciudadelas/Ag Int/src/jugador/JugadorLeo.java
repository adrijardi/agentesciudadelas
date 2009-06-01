package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import utils.Personajes;
import utils.ResumenInfoPartida;
import utils.TipoDistrito;
import acciones.DestruirDistrito;
import acciones.InfoPartida;
import acciones.OfertarPersonajes;
import acciones.PedirDistritoJugadores;

import comportamientos_jugador.AsesinarPersonaje;
import comportamientos_jugador.CambiarCartasJugador;
import comportamientos_jugador.ConstruirDistritoJugador;
import comportamientos_jugador.FinTurno;
import comportamientos_jugador.HabilidadArquitecto;
import comportamientos_jugador.HabilidadLadron;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirCobrarMercader;
import comportamientos_jugador.PedirCobrarObispo;
import comportamientos_jugador.PedirCobrarRey;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class JugadorLeo extends AgJugador {
	private final Random dado =  new Random();
	private int[] ordenPersonajes = new int[8];
	private ResumenInfoPartida res;
	private boolean iniciadoResumen;

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		pj_actual = seleccionarPersonaje(contenido);
		return pj_actual;
	}

	public void initOrdenPersonajes(){
		for(int i = 0; i < this.ordenPersonajes.length; i++){
			this.ordenPersonajes[i] = 1;
		}
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
		if(llp.contains(Personajes.ARQUITECTO)){
			return llp.get(llp.indexOf(Personajes.ARQUITECTO));
		}
		if(llp.contains(Personajes.MERCADER)){
			return llp.get(llp.indexOf(Personajes.MERCADER));
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
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		llp.remove(Personajes.LADRON.getPj());
		llp.remove(Personajes.MAGO.getPj());
		llp.remove(Personajes.OBISPO.getPj());
		if(this._muerto!=null) llp.remove(this._muerto);
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i]);
		}
		return llp.get(((int)(Math.random()))*llp.size());
	}

	@Override
	public void setInfo(InfoPartida msgInfo) {
		if(!iniciadoResumen)
			res = ResumenInfoPartida.getInstance(msgInfo, this.getName());
		else{
			if(res.isInicializado())
				res.darValores(this.getName());
			res.actualizarPartida(msgInfo);
		}
		
		initOrdenPersonajes();
		if(res.isInicializado()){
			prioridadLadron();
			prioridadMago();
			prioridadArquitecto();
			prioridadAsesino();
			prioridadRey();
			prioridadPorColor();
			prioridadMercader();
		}
	}
	
	private void prioridadMago(){
		int media=0;
		for(int i = 0; i < res.get_jugadores().length;i++){
			media += res.get_jugadores()[i].getMano();
		}
		if(res.get_jugadores()[res.get_miPosicion()].getMano() < 3){
			if(media >= 10)
				ordenPersonajes[Personajes.MAGO.getPosision()] += 10;
			if(media >= 5 && media < 10)
				ordenPersonajes[Personajes.MAGO.getPosision()] += 6;
			if(media >= 1 && media < 5)
				ordenPersonajes[Personajes.MAGO.getPosision()] += 1;
			if(media == 0)
				ordenPersonajes[Personajes.MAGO.getPosision()] -= 1;
			}else{
				if(media >= 10)
					ordenPersonajes[Personajes.MAGO.getPosision()] += 7;
				if(media >= 5 && media < 10)
					ordenPersonajes[Personajes.MAGO.getPosision()] += 4;
				if(media >= 1 && media < 5)
					ordenPersonajes[Personajes.MAGO.getPosision()] += 3;
				if(media == 0)
					ordenPersonajes[Personajes.MAGO.getPosision()] -= 2;	
			}
		}
		
	private void prioridadLadron() {
		int media=0;
		for(int i = 0; i < res.get_jugadores().length;i++){
			media += res.get_jugadores()[i].getMonedas();
			if(res.get_jugadores()[i].getMonedas()>= 6)
				ordenPersonajes[Personajes.LADRON.getPosision()] += 5;
			if(res.get_jugadores()[i].getMonedas()>= 4 && res.get_jugadores()[i].getMonedas()< 6)
				ordenPersonajes[Personajes.LADRON.getPosision()] += 3;
			if(res.get_jugadores()[i].getMonedas() == 3)
				ordenPersonajes[Personajes.LADRON.getPosision()] += 2;
		}
		media=media/4;
		if(media >= 10)
			ordenPersonajes[Personajes.LADRON.getPosision()] += 10;
		if(media >= 5 && media < 10)
			ordenPersonajes[Personajes.LADRON.getPosision()] += 6;
		if(media >= 1 && media < 5)
			ordenPersonajes[Personajes.LADRON.getPosision()] += 1;
		if(media == 0)
			ordenPersonajes[Personajes.LADRON.getPosision()] -= 1;
		if(res.get_jugadores()[res.get_miPosicion()].getMonedas()<3)
			ordenPersonajes[Personajes.LADRON.getPosision()] += 3;
		if(res.get_jugadores()[res.get_miPosicion()].getMonedas() <= 1)
			ordenPersonajes[Personajes.LADRON.getPosision()] += 7;
	}
	
	public void prioridadRey() {
		ordenPersonajes[Personajes.REY.getPosision()] += 8;
	}
	
	public void prioridadAsesino() {
		ordenPersonajes[Personajes.ASESINO.getPosision()] += 11;
	}
	
	public void prioridadMercader() {
		ordenPersonajes[Personajes.MERCADER.getPosision()] += 6;
	}
	
	private void prioridadArquitecto(){
		if(res.get_jugadores()[res.get_miPosicion()].getMano() > 2){
			if(res.get_jugadores()[res.get_miPosicion()].getMonedas()>5){
				if(res.get_jugadores()[res.get_miPosicion()].getMano() < 2)
					ordenPersonajes[Personajes.ARQUITECTO.getPosision()] += 8;
				else
					ordenPersonajes[Personajes.ARQUITECTO.getPosision()] += 5;
			}
		}
		
		if(res.getDistritos(res.get_miPosicion()).size() == 6 &&
				res.get_jugadores()[res.get_miPosicion()].getMonedas() > 5)
			ordenPersonajes[Personajes.ARQUITECTO.getPosision()]+= 12;
		if(res.getDistritos(res.get_miPosicion()).size() == 6 &&
				res.get_jugadores()[res.get_miPosicion()].getMonedas() < 5)
			ordenPersonajes[Personajes.ARQUITECTO.getPosision()]+= 8;
	}
	
	
	private void prioridadPorColor(){
		int rojo=0;
		int verde=0;
		int azul=0;
		int amarillo=0;
		jade.util.leap.LinkedList lista = res.getDistritos(res.get_miPosicion());
		for(int i=0;i<lista.size();i++){
			if(((Distrito)lista.get(i)).getColor().equalsIgnoreCase(TipoDistrito.COMERCIAL.getColor())){
				verde++;
			}else if(((Distrito)lista.get(i)).getColor().equalsIgnoreCase(TipoDistrito.NOBLE.getColor())){
				amarillo++;
			}else if(((Distrito)lista.get(i)).getColor().equalsIgnoreCase(TipoDistrito.MILITAR.getColor())){
				rojo++;
			}else if(((Distrito)lista.get(i)).getColor().equalsIgnoreCase(TipoDistrito.RELIGIOSO.getColor())){
				azul++;
			}
		}
		if(verde > 2)
			ordenPersonajes[Personajes.MERCADER.getPosision()] += verde + 8;
		else
			ordenPersonajes[Personajes.MERCADER.getPosision()] += verde +5;
		if(azul > 2)
			ordenPersonajes[Personajes.OBISPO.getPosision()] += azul + 5;
		else
			ordenPersonajes[Personajes.OBISPO.getPosision()] += azul + 3;
		if(amarillo > 2)
			ordenPersonajes[Personajes.REY.getPosision()] += amarillo + 7;
		else
			ordenPersonajes[Personajes.REY.getPosision()] += amarillo + 4;
		if(rojo > 2)
			ordenPersonajes[Personajes.CONDOTIERO.getPosision()]+= rojo + 5;
		else
			ordenPersonajes[Personajes.CONDOTIERO.getPosision()]+= rojo + 3;
	}
	
	private Personaje seleccionarPersonaje(OfertarPersonajes contenido){
		Personaje salida=null;
		List lista = contenido.getDisponibles();
		int peso = 0;
		int pesoA = -5;
		for(int i=0;i<lista.size();i++){
			peso = ordenPersonajes[((Personaje)lista.get(i)).getTurno()-1];
			if(pesoA < peso){
				salida = (Personaje)lista.get(i);
				pesoA = peso;
			}
		}
		return salida;
	}
}
