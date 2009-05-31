package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import java.util.Iterator;
import java.util.LinkedList;

import utils.Distritos;
import utils.Personajes;
import utils.ResumenInfoPartida;
import utils.TipoDistrito;
import acciones.DestruirDistrito;
import acciones.InfoPartida;
import acciones.OfertarPersonajes;
import acciones.PedirDistritoJugadores;

import comportamientos_jugador.AsesinarPersonaje;
import comportamientos_jugador.ConstruirDistritoJugador;
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

public class JugadorPablo extends AgJugador {

	//prioridad para elegir personaje
	public int[] prioridadPersonajes=new int[8];
	ResumenInfoPartida _resumen;
	
	int ronda=0;
	
	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		
		ronda++;
		// Se selecciona un personaje aleatorio de la oferta
		pj_actual = elegir(contenido);
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
		switch(Personajes.getPersonajeByPJ(pj_actual)){
		case ASESINO:
			ret = new AsesinarPersonaje(this, ret, msg_sender);
			break;
		case LADRON:
			ret = new HabilidadLadron(this, ret, msg_sender);
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
			//ret=new DestruirDistritoJugador(this, ret, msg_sender); // falla algunas veces
			ret = new PedirCobrarCondotierro(this, ret, msg_sender);
			break;
		}
		
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
		// la politica es construir la mas cara que pueda costearme
		int max=0;
		int posDistMax=-1;
		Distrito ret = null;
		Distrito[] dist = getDistritosConstruibles();
		if(dist != null && dist.length > 0){
			for(int i=0;i<dist.length;i++){
				if(dist[i].getCoste()>max){
					max=dist[i].getCoste();
					posDistMax=i;
				}
			}
			return dist[posDistMax];
		}
		return ret;
	}

	@Override
	public Distrito[] descartaDistritos(List distritos) {
		//descarto el q tenga construido o uno al azar
		
		int num=distritos.size()-1;
		Distrito[] descartado = new Distrito[num];
		
		int elegido=-1;
		int cont=0;
		
		List construido=_resumen.getDistritos(_resumen.get_miPosicion());
		for(int i=0;i<construido.size();i++){
			if(elegido<0){
				if(((Distrito)construido.get(i)).equals((Distrito)(distritos.get(0))))
					elegido=0;
				else if(((Distrito)construido.get(i)).equals((Distrito)(distritos.get(1))))
					elegido=1;
			}
		}
		
		if(elegido<0){
			elegido=((int)(Math.random()*distritos.size()));
		}
		
		for(int i=0;i<distritos.size();i++){
			if(i==elegido) mano.add((Distrito)(distritos.get(i)));
			else descartado[cont++]=((Distrito)(distritos.get(i)));
		}
		
		return descartado;
	}

	
	@Override
	public void getDistritoDestruir(PedirDistritoJugadores pd, DestruirDistrito dd) {
		// TODO Auto-generated method stub
		int num=(int)(Math.random()*3);
		int aux=0;
		//int dinero=this.monedas;
		
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
		 * solo pide cartas si no tengo en la mano y no soy arquitecto
		 */
		if(this.pj_actual.getTurno()==Personajes.ARQUITECTO.getPj().getTurno()) return false;
		if(cartasManoNoConstruidas()>0){
			return false;
		}
		return true;
	}

	@Override
	public Personaje getPersonajeMatar() {
		
		if(this.monedas>3) return Personajes.LADRON.getPj();
		if(this.mano.size()>2) return Personajes.MAGO.getPj();
		
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i]);
			System.out.println(destapados[i]); // TODO quitar
		}
		return llp.get((int)(Math.random()*llp.size()));
	}
	
	@Override
	public Jugador seleccionarJugadorCambiarCartas(Jugador jug1, Jugador jug2,Jugador jug3) {
		// TODO Auto-generated method stub
		int max=this.cartasManoNoConstruidas();
		int num=0;
		
		int aux=jug1.getMano();
		if(aux>max) num=1;
		
		aux=jug2.getMano();
		if(aux>max) num=2;
		
		aux=jug3.getMano();
		if(aux>max) num=3;
		
		switch (num) {
		case 0:
			return this.getJugador();
		case 1:
			return jug1;
		case 2:
			return jug2;
		default:
			return jug3;
		}
	}

	@Override
	public Personaje seleccionarPersonajeRobo() {
		// TODO Auto-generated method stub
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		llp.remove(Personajes.LADRON.getPj());
		if(this._muerto!=null) llp.remove(this._muerto);
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i]);
		}
		return llp.get(((int)(Math.random()))*llp.size());
	}

	@Override
	public void setInfo(InfoPartida msgInfo) {
		/*
		 * leo la informacion pasada en el mensaje de tipo InfoPartida
		 */
		_resumen=ResumenInfoPartida.getInstance(msgInfo, pj_actual);
		
		/*
		 * se da peso a los personajes preferidos
		 */
		for(int i=0;i<prioridadPersonajes.length;i++) prioridadPersonajes[i]=1;
		//1º hay mucho dinero en la mesa
		prioridadLadron();
		//si hay alguien con 6 o 7 distritos
		muchosDistritos();
		//si tengo pocas cartas
		muchasCartas();
		prioridadArquitecto();
		orden();
		mercaderExtra();
		prioridadPorColor();
	}

	private void prioridadLadron() {
		int media=0;
		for(int i=0;i<_resumen.get_jugadores().length;i++){
			media+=_resumen.get_jugadores()[i].getMonedas();
			if(_resumen.get_jugadores()[i].getMonedas()>=4)
				prioridadPersonajes[Personajes.LADRON.getPosision()]+=5;
		}
		media=media/4;
		if(media>=3)prioridadPersonajes[Personajes.LADRON.getPosision()]+=3;
		if(_resumen.get_jugadores()[_resumen.get_miPosicion()].getMonedas()<3)
			prioridadPersonajes[Personajes.LADRON.getPosision()]+=3;
	}
	
	private void muchosDistritos() {
		for(int i=0;i<_resumen.get_jugadores().length;i++){
			if(_resumen.getDistritos(i).size()==7) 
				prioridadPersonajes[Personajes.ASESINO.getPosision()]+=5;
			else if(_resumen.getDistritos(i).size()==6) 
				prioridadPersonajes[Personajes.ASESINO.getPosision()]+=3;
		}
	}
	
	private void muchasCartas(){
		int diff=0;
		if(_resumen.get_jugadores()[_resumen.get_miPosicion()].getMano()<2){
			for(int i=0;i<_resumen.get_jugadores().length;i++){
				if(i!=_resumen.get_miPosicion()){
					diff=_resumen.get_jugadores()[i].getMano() - _resumen.get_jugadores()[_resumen.get_miPosicion()].getMano();
					if(diff>0)
						prioridadPersonajes[Personajes.MAGO.getPosision()]+=diff;
				}
			}
		}
	}
	
	private void prioridadArquitecto(){
		if(_resumen.get_jugadores()[_resumen.get_miPosicion()].getMano()>2){
			if(_resumen.get_jugadores()[_resumen.get_miPosicion()].getMonedas()>5){
				prioridadPersonajes[Personajes.ARQUITECTO.getPosision()]+=5;
			}
		}
		
		if(_resumen.getDistritos(_resumen.get_miPosicion()).size()==6 &&
				_resumen.get_jugadores()[_resumen.get_miPosicion()].getMonedas()>3)
			prioridadPersonajes[Personajes.ARQUITECTO.getPosision()]+=10;
	}
	
	private void orden(){
		for(int i=0;i<prioridadPersonajes.length;i++){
			prioridadPersonajes[i]+=((prioridadPersonajes.length-1)-i);
		}
	}
	private void mercaderExtra(){
		if(_resumen.get_jugadores()[_resumen.get_miPosicion()].getMonedas()<2)
			prioridadPersonajes[Personajes.MERCADER.getPosision()]+=3;
		
		prioridadPersonajes[Personajes.MERCADER.getPosision()]+=2;
	}
	
	private void prioridadPorColor(){
		int rojo=0;
		int verde=0;
		int azul=0;
		int amarillo=0;
		jade.util.leap.LinkedList lista=_resumen.getDistritos(_resumen.get_miPosicion());
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
		prioridadPersonajes[Personajes.MERCADER.getPosision()]+=verde*2;
		prioridadPersonajes[Personajes.OBISPO.getPosision()]+=azul*2;
		prioridadPersonajes[Personajes.REY.getPosision()]+=amarillo*2;
		prioridadPersonajes[Personajes.CONDOTIERO.getPosision()]+=rojo*2;
	}
	
	private Personaje elegir(OfertarPersonajes contenido){
		Personaje salida=null;
		int contador=0;
		
		List lista = contenido.getDisponibles();
		int[] pesos = new int[lista.size()];
		for(int i=0;i<lista.size();i++){
			pesos[i]=prioridadPersonajes[((Personaje)lista.get(i)).getTurno()-1];
		}
		
		for(int i=0;i<pesos.length;i++){
			contador+=pesos[i];
		}
		
		int inicio=(int)(Math.random()*lista.size());
		int numero=(int)(Math.random()*contador);
		
		for(int i=0;i<pesos.length;i++){
			numero-=pesos[(inicio+i)%pesos.length];
			if(numero<=0){
				salida=(Personaje)lista.get(i);
			}
			
		}
		return salida;
	}
}
