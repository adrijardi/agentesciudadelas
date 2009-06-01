package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import utils.Personajes;
import utils.TipoDistrito;
import acciones.DestruirDistrito;
import acciones.InfoPartida;
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
	
	private Objetivos _objetivos = new Objetivos();
	
	private boolean _asesinado;
	private boolean _robado;
	
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
		
		// Elimina al personaje más odiado
		JugadorObjetivo[] jugadoresPorOdio = _objetivos.jugadoresPorOdio();
		for (JugadorObjetivo jugadorObjetivo : jugadoresPorOdio) {
			Personajes persObjetivo = jugadorObjetivo.getPersonajeEstimado();
			if(destapados[0] != persObjetivo && destapados[1] != persObjetivo && persObjetivo != Personajes.ASESINO)
				return persObjetivo.getPj();
		}
		

		// Elimina al arquitecto o al mercader
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
		Distrito [] distritos = null;
		
		// Se elige el distrito de menos coste del jugador más odiado
		JugadorObjetivo[] jugadoresPorOdio = _objetivos.jugadoresPorOdio();
		if(jugadoresPorOdio[0].nombre.compareTo(pd.getJugador1().getNombre()) == 0){
			jelec = pd.getJugador1();
			distritos = (Distrito[])pd.getDistritos1().toArray();
		}
		if(jugadoresPorOdio[0].nombre.compareTo(pd.getJugador2().getNombre()) == 0){
			jelec = pd.getJugador2();
			distritos = (Distrito[])pd.getDistritos2().toArray();
		}
		if(jugadoresPorOdio[0].nombre.compareTo(pd.getJugador3().getNombre()) == 0){
			jelec = pd.getJugador3();
			distritos = (Distrito[])pd.getDistritos3().toArray();
		}
		
		int coste = 0;
		if(distritos != null){
			for (Distrito distrito : distritos) {
				if(elegido == null || distrito.getCoste() < coste){
					coste = distrito.getCoste();
					elegido = distrito;
				}
			}
		}
		
		if(elegido == null){
			// Se elige un distrito de coste 1
			distritos = (Distrito[])pd.getDistritos1().toArray();
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
		}
		if(elegido != null){
			dd.setDistrito(elegido);
			dd.setJugador(jelec);
			dd.setPago(elegido.getCoste()-1);
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
		JugadorObjetivo[] jugadoresPorOdio = _objetivos.jugadoresPorOdio();
		Jugador objetivo = null;
		
		for (JugadorObjetivo actual : jugadoresPorOdio) {
			if(actual.nombre.compareTo(jug1.getNombre()) == 0 && jug1.getMano() > 0)
				objetivo = jug1;
			if(actual.nombre.compareTo(jug2.getNombre()) == 0 && jug2.getMano() > 0)
				objetivo = jug2;
			if(actual.nombre.compareTo(jug3.getNombre()) == 0 && jug3.getMano() > 0)
				objetivo = jug3;
		}
		
		if(objetivo == null){
			
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
		}
		return objetivo;
	}

	@Override
	public Personaje seleccionarPersonajeRobo() {
		// Roba al personaje más odiado
		JugadorObjetivo[] jugadoresPorOdio = _objetivos.jugadoresPorOdio();
		for (JugadorObjetivo jugadorObjetivo : jugadoresPorOdio) {
			if(jugadorObjetivo.monedas > 0){
				Personajes persObjetivo = jugadorObjetivo.getPersonajeEstimado();
				if(persObjetivo != Personajes.ASESINO && persObjetivo != Personajes.LADRON && destapados[0] != persObjetivo && destapados[1] != persObjetivo)
					return persObjetivo.getPj();
			}
		}
		
		
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

	@Override
	public void setInfo(InfoPartida msgInfo) {
		//Se actualiza la info del j1;
		Jugador j = msgInfo.getJugador1();
		setConcreteInfo(j, msgInfo.getPersonaje1(), msgInfo.getDistritosJ1(), msgInfo.getJugoP1());
		//Se actualiza la info del j2;
		j = msgInfo.getJugador2();
		setConcreteInfo(j, msgInfo.getPersonaje2(), msgInfo.getDistritosJ2(), msgInfo.getJugoP2());
		//Se actualiza la info del j3;
		j = msgInfo.getJugador3();
		setConcreteInfo(j, msgInfo.getPersonaje3(),msgInfo.getDistritosJ3(), msgInfo.getJugoP3());
		//Se actualiza la info del j4;
		j = msgInfo.getJugador4();
		setConcreteInfo(j, msgInfo.getPersonaje4(),msgInfo.getDistritosJ4(), msgInfo.getJugoP4());
	}
	
	private void setConcreteInfo(Jugador j, Personaje personaje, List list, boolean b) {
		// Si es un contrincante
		if(j.getNombre().compareTo(this.getName())!=0){
			JugadorObjetivo jugador = _objetivos.getJugador(j.getNombre());
			jugador.jugado = b;
			jugador.puntos = j.getPuntos();
			jugador.monedas = j.getMonedas();
			jugador.cartas = j.getMano();
			
			if(personaje == Personajes.ASESINO.getPj()){
				jugador.personajesElegidos[0]++;
				if(_asesinado)
					jugador.odio+=2;
			}else if(personaje == Personajes.LADRON.getPj()){
				jugador.personajesElegidos[1]++;
				if(_robado)
					jugador.odio++;
			}else if(personaje == Personajes.MAGO.getPj()){
				jugador.personajesElegidos[2]++;
			}else if(personaje == Personajes.REY.getPj()){
				jugador.personajesElegidos[3]++;
			}else if(personaje == Personajes.OBISPO.getPj()){
				jugador.personajesElegidos[4]++;
			}else if(personaje == Personajes.MERCADER.getPj()){
				jugador.personajesElegidos[5]++;
			}else if(personaje == Personajes.ARQUITECTO.getPj()){
				jugador.personajesElegidos[6]++;
			}else if(personaje == Personajes.CONDOTIERO.getPj()){
				jugador.personajesElegidos[7]++;
			}
		}
		_asesinado = false;
		_robado = false;
	}
	
	class Objetivos{
		Vector<JugadorObjetivo> objetivos = new Vector<JugadorObjetivo>(3);
		
		public Objetivos(){}
		
		JugadorObjetivo getJugador(String name){
			for (int i = 0; i < objetivos.size(); i++) {
				if(objetivos.get(i).nombre.compareTo(name) == 0)
					return objetivos.get(i);
			}
			JugadorObjetivo nuevo = new JugadorObjetivo();
			nuevo.nombre = name;
			objetivos.add(nuevo);
			return nuevo;
		}
		
		JugadorObjetivo[] jugadoresPorOdio(){
			JugadorObjetivo[] objetivosPorOdio = new JugadorObjetivo[3];
			objetivosPorOdio[0] = objetivos.get(0);
			if(objetivos.get(1).calcularOdioFinal() > objetivosPorOdio[0].calcularOdioFinal()){
				objetivosPorOdio[1] = objetivosPorOdio[0];
				objetivosPorOdio[0] = objetivos.get(1);
			}else{
				objetivosPorOdio[1] = objetivos.get(1);
			}
			
			if(objetivos.get(2).calcularOdioFinal() > objetivosPorOdio[0].calcularOdioFinal()){
				objetivosPorOdio[2] = objetivosPorOdio[1];
				objetivosPorOdio[1] = objetivosPorOdio[0];
				objetivosPorOdio[0] = objetivos.get(2);
			}else{
				if(objetivos.get(2).calcularOdioFinal() > objetivosPorOdio[1].calcularOdioFinal()){
					objetivosPorOdio[2] = objetivosPorOdio[1];
					objetivosPorOdio[1] = objetivos.get(2);
				}else{
					objetivosPorOdio[2] = objetivos.get(2);
				}
			}
			return objetivosPorOdio;
		}
	}
	
	class JugadorObjetivo implements Comparable<JugadorObjetivo>{
		String nombre;
		int puntos;
		int cartas;
		int monedas;
		int odio;
		boolean jugado;
		int personajesElegidos[] = new int[8];
		
		int calcularOdioFinal(){
			return odio+puntos/2+monedas+4+cartas/4;
		}
		
		Personajes getPersonajeEstimado(){
			Personajes p = Personajes.ASESINO;
			int actual = personajesElegidos[0];
			if(personajesElegidos[1] > actual){
				p = Personajes.LADRON;
				actual = personajesElegidos[1];
			}
			if(personajesElegidos[2] > actual){
				p = Personajes.MAGO;
				actual = personajesElegidos[2];
			}
			if(personajesElegidos[3] > actual){
				p = Personajes.REY;
				actual = personajesElegidos[3];
			}
			if(personajesElegidos[4] > actual){
				p = Personajes.OBISPO;
				actual = personajesElegidos[4];
			}
			if(personajesElegidos[5] > actual){
				p = Personajes.MERCADER;
				actual = personajesElegidos[5];
			}
			if(personajesElegidos[6] > actual){
				p = Personajes.ARQUITECTO;
				actual = personajesElegidos[6];
			}
			if(personajesElegidos[7] > actual){
				p = Personajes.CONDOTIERO;
				actual = personajesElegidos[7];
			}
			return p;
		}
		
		@Override
		public int compareTo(JugadorObjetivo o) {
			return nombre.compareTo(o.nombre);
		}
		public int compareTo(String o) {
			return nombre.compareTo(o);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof JugadorObjetivo)
				if(nombre.compareTo(((JugadorObjetivo) obj).nombre) == 0)
					return true;
			return nombre.equals(obj);
		}
	}

	@Override
	public void set_muerto(Personaje muerto) {
		super.set_muerto(muerto);
		if(muerto != null && pj_actual.compareTo(muerto) == 0)
			_asesinado = true;
	}

	@Override
	public void set_robado(Personaje robado) {
		super.set_robado(robado);
		if(robado != null && pj_actual.compareTo(robado) == 0)
			_robado = true;
	}
	
	
}
