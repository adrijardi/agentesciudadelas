package jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.List;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import utils.Distritos;
import utils.Personajes;
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
import comportamientos_jugador.PagarRobo;
import comportamientos_jugador.PedirCartas;
import comportamientos_jugador.PedirCobrarCondotierro;
import comportamientos_jugador.PedirCobrarMercader;
import comportamientos_jugador.PedirCobrarObispo;
import comportamientos_jugador.PedirCobrarRey;
import comportamientos_jugador.PedirMonedas;

import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class JugadorDani extends AgJugador {
	private final Random dado = new Random();
	private LinkedList<Desire> lld;
	private LinkedList<Risk> llr;
	private int[] buildedinfo = new int[5];
	
	private HashMap<String, InfoJugador> hmInfo = new HashMap<String, InfoJugador>();
	private int asesino = 0;
	private int ladron = 0;
	private int mago = 0;
	private int rey = 0;
	private int obispo = 0;
	private int mercader = 0;
	private int arquitecto = 0;
	private int condotiero = 0;

	private final int red = 0;
	private final int blue = 1;
	private final int yellow = 2;
	private final int green = 3;
	private final int purple = 4;
	private int mispuntos = 0;
	
	private boolean asesinoDestapado;
	private boolean ladronDestapado;
	private boolean magoDestapado;
	
	private int maxbuilded = 0;
	
	private int pos;

	@Override
	public Personaje selectPersonaje(OfertarPersonajes contenido) {
		List pjDisponibles = contenido.getDisponibles();
		Personaje seleccionado = null;
		Personaje parcial;
		double fitness = 0;
		double fitnessParcial;
		
		// Se obtiene la informacion de las cartas construidas
		getInfoBuildedCards();
		// Se determinan los deseos
		modifyDesire();
		
		// Se determinan las amenazas;
		asesinoDestapado = estaDestapado(Personajes.ASESINO);
		ladronDestapado = estaDestapado(Personajes.LADRON);
		magoDestapado = estaDestapado(Personajes.MAGO);
		modifyRisk();
		
		pos = 6 - pjDisponibles.size();

		// Se selecciona un personaje en funcion de las amenazas, los deseos y
		// los personajes ofertados
		for (int i = 0; i < pjDisponibles.size(); i++) {
			parcial = (Personaje) pjDisponibles.get(i);
			fitnessParcial = calcularFitness(parcial);
			System.out.println(pos + "@@@@@ "+parcial.getNombre()+" "+fitnessParcial);
			if (seleccionado == null || fitness < fitnessParcial) {
				seleccionado = parcial;
				fitness = fitnessParcial;
			}
		}

		pj_actual = seleccionado;
		mostrarHistSelecc(pj_actual);
		return pj_actual;
	}
	
	private void mostrarHistSelecc(Personaje pj_actual) {
		switch (Personajes.getPersonajeByPJ(pj_actual)) {
		case ARQUITECTO:
			arquitecto++;
			break;
		case CONDOTIERO:
			condotiero++;
			break;
		case ASESINO:
			asesino++;
			break;
		case LADRON:
			ladron++;
			break;
		case MAGO:
			mago++;
			break;
		case MERCADER:
			mercader++;
			break;
		case OBISPO:
			obispo++;
			break;
		case REY:
			rey++;
			break;
		}
		System.out.println("asesino "+asesino);
		System.out.println("ladron "+ladron);
		System.out.println("mago "+mago);
		System.out.println("rey "+rey);
		System.out.println("obispo "+obispo);
		System.out.println("mercader "+mercader);
		System.out.println("arquitecto "+arquitecto);
		System.out.println("condotiero "+condotiero);		
	}

	private boolean estaDestapado(Personajes pj){
		boolean ret = false;
		for (int i = 0; i < destapados.length; i++) {
			if(destapados[i] != null && destapados[i].compareTo(pj)== 0){
				ret = true;
			}
		}
		return ret;
	}

	private double calcularFitness(Personaje parcial) {
		double fitness = 0;
		switch (Personajes.getPersonajeByPJ(parcial)) {
		case ARQUITECTO:
			fitness = 1 * Desire.CARDS.weight;
			fitness += fitness * 0.5 * Desire.COLORS.weight;
			fitness += 0.2 * Desire.MONEY.weight;
			fitness -= fitness *(7/8 * Risk.DELEGATE_TURN.weight);
			
			if(!magoDestapado)
				fitness -= fitness*(1/5 * Risk.CHANGED.weight);
			
			if(!ladronDestapado)
				fitness -=  fitness *(1/5 * Risk.STOLEN.weight);
			
			if(!asesinoDestapado)
				fitness -= fitness *(0.5 + (0.5  * Risk.KILLED.weight));
				
			break;
		case ASESINO:
			fitness = 0.1 * Desire.CARDS.weight;
			fitness += 0.1 * Desire.MONEY.weight;
			fitness += 0.15 * Desire.COLORS.weight;
			
			if(!ladronDestapado)
				fitness +=  1 * Risk.STOLEN.weight;
			
			break;
		case CONDOTIERO:
			fitness = 0.2 * Desire.CARDS.weight;
			fitness += 0.2 * Desire.COLORS.weight;
			fitness += (0.2+(0.15*buildedinfo[red])) * Desire.MONEY.weight;
			fitness -= fitness *(1 * Risk.DELEGATE_TURN.weight);
			
			if(!magoDestapado)
				fitness -= fitness *(1/6 * Risk.CHANGED.weight);
			
			if(!ladronDestapado)
				fitness -=  fitness *((0.1+(0.05*buildedinfo[red])) * Risk.STOLEN.weight);
			
			if(!asesinoDestapado)
				fitness -= fitness *((0.1+(0.05*buildedinfo[red])) * Risk.KILLED.weight);
		
			break;
		case LADRON:
			fitness = 0.2 * Desire.CARDS.weight;
			fitness += 0.2 * Desire.COLORS.weight;
			fitness += (0.2+(0.2*getProbMoney())) * Desire.MONEY.weight;
			fitness -= fitness *(2/8 * Risk.DELEGATE_TURN.weight);
			
			if(!magoDestapado)
				fitness -= fitness *(1/6 * Risk.CHANGED.weight);
			
			if(!asesinoDestapado)
				fitness -= fitness *(1/5 * Risk.KILLED.weight);

			break;
		case MAGO:
			fitness = (0.2+(0.1*getMaxCartas())) * Desire.CARDS.weight;
			fitness += (0.2) * Desire.COLORS.weight;
			fitness += (0.2+0.15+(0.15*buildedinfo[green])) * Desire.MONEY.weight;
			fitness -= fitness *(6/8 * Risk.DELEGATE_TURN.weight);
			
			if(!ladronDestapado)
				fitness -=  fitness*(1/5 * Risk.STOLEN.weight);
			
			if(!asesinoDestapado)
				fitness -= fitness*(1/5 * Risk.KILLED.weight);
			
			break;
		case MERCADER:
			fitness = 0.2 * Desire.CARDS.weight;
			fitness += 0.2 * Desire.COLORS.weight;
			fitness += (0.2+0.15+(0.15*buildedinfo[green])) * Desire.MONEY.weight;
			fitness -= fitness*(6/8 * Risk.DELEGATE_TURN.weight);
			
			if(!magoDestapado)
				fitness -= fitness*(1/5 * Risk.CHANGED.weight);
			
			if(!ladronDestapado)
				fitness -= fitness*( (0.1+(0.1*buildedinfo[green])) * Risk.STOLEN.weight);
			
			if(!asesinoDestapado)
				fitness -= fitness *((0.1+0.1+(0.05*buildedinfo[green])) * Risk.KILLED.weight);
			
			break;
		case OBISPO:
			fitness = 0.2 * Desire.CARDS.weight;
			fitness += 0.2 * Desire.COLORS.weight;
			fitness += (0.2+(0.15*buildedinfo[blue])) * Desire.MONEY.weight;
			fitness -= fitness*(5/8 * Risk.DELEGATE_TURN.weight);
			
			if(!magoDestapado)
				fitness -= fitness*(1/5 * Risk.CHANGED.weight);
			
			if(!ladronDestapado)
				fitness -=  fitness *((0.1+(0.05*buildedinfo[blue])) * Risk.STOLEN.weight);
			
			if(!asesinoDestapado)
				fitness -= fitness *((0.1+(0.05*buildedinfo[blue])) * Risk.KILLED.weight);
			
			break;
		case REY:
			fitness = 0.2 * Desire.CARDS.weight;
			fitness += 0.2 * Desire.COLORS.weight;
			fitness += (0.2+(0.15*buildedinfo[yellow])) * Desire.MONEY.weight;
			fitness += 0.1 *pos;
			fitness -= fitness *(4/8 * Risk.DELEGATE_TURN.weight);
			
			if(!magoDestapado)
				fitness -= fitness*(1/5 * Risk.CHANGED.weight);
			
			if(!ladronDestapado)
				fitness -=  fitness *((0.1+(0.05*buildedinfo[yellow])) * Risk.STOLEN.weight);
			
			if(!asesinoDestapado)
				fitness -= fitness *((0.1+(0.05*buildedinfo[yellow])) * Risk.KILLED.weight);
			break;
		}
		return fitness;
	}

	private double getMaxCartas() {
		double ret = 0;
		
		Set<String> k = hmInfo.keySet();
		InfoJugador ij;
		for (String string : k) {
			ij = hmInfo.get(string);
			if(ij.mano != 0){
				if(ret == 0)
					ret = ij.mano;
				else if(ij.mano > ret)
					ret = ij.mano;
			}
		}
		return ret;
	}

	private double getProbMoney() {
		double ret = 0;
		int zeros = 1;
		
		Set<String> k = hmInfo.keySet();
		InfoJugador ij;
		for (String string : k) {
			ij = hmInfo.get(string);
			if(ij.monedas ==0)
				zeros++;
			else
				ret += 0.1 *ij.monedas;
		}
		ret /= zeros;
		return ret;
	}

	@Override
	public Behaviour jugarTurno(ACLMessage msg) {
		Behaviour ret;
		printEstado();
		msg_sender = msg.getSender();

		ret = new FinTurno(this, msg_sender);

		// Construir distrito
		ret = new ConstruirDistritoJugador(this, ret, msg_sender);

		// Accion jugador falta PedirCartas
		if (seleccionarMonedasOCartas()) {
			ret = new PedirCartas(this, ret, msg_sender);
		} else {
			ret = new PedirMonedas(this, ret, msg_sender);
		}

		switch (Personajes.getPersonajeByPJ(pj_actual)) {
		case ASESINO:
			ret = new AsesinarPersonaje(this, ret, msg_sender);
			break;
		case LADRON:
			ret = new HabilidadLadron(this, ret, msg_sender);
			break;
		case MAGO:
			if (cartasManoNoConstruidas() == 0)
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
			ret = new PedirCobrarCondotierro(this, ret, msg_sender);
			break;
		}

		if (this.get_robado() != null) {
			if (Personajes.getPersonajeByPJ(pj_actual) == Personajes.getPersonajeByPJ(this.get_robado()))
				System.out.println("Entra en el pers jugado");
			ret = new PagarRobo(this, ret, msg_sender);
		}

		return ret;
	}

	@Override
	public Distrito getDistritoConstruir() {
		Distrito ret = null;
		Distrito[] dist = getDistritosConstruibles();
		if(maxbuilded <= 3){
			if(noMasCaraQueMondeas() || monedas > 6){
				for (int i = 0; i < dist.length; i++) {
					if(ret == null){
						ret = dist[i];
					}else if(noTengoColor(dist[i].getColor())){
						if(!noTengoColor(ret.getColor()))
							ret = dist[i];
						else if(dist[i].getPuntos() > ret.getPuntos())
							ret = dist[i];
					}else if(dist[i].getPuntos() > ret.getPuntos())
						ret = dist[i];
				}
			}
		}else{
			for (int i = 0; i < dist.length; i++) {
				if(ret == null)
					ret = dist[i];
				else if(noTengoColor(dist[i].getColor()))
					if(!noTengoColor(ret.getColor()))
						ret = dist[i];
					else if(dist[i].getPuntos() > ret.getPuntos())
						ret = dist[i];
				else if(dist[i].getPuntos() > ret.getPuntos())
					ret = dist[i];
			}
		}
		return ret;
	}

	private boolean noMasCaraQueMondeas() {
		boolean ret = false;
		for (Distrito d : construidas) {
			if(d.getCoste() > monedas)
				ret = true;
		}
		return ret;
	}

	private boolean noTengoColor(String color) {
		boolean ret = false;
		switch (TipoDistrito.getByColor(color)) {
		case COMERCIAL:
			ret = buildedinfo[green] ==0;
			break;
		case MARAVILLA:
			ret = buildedinfo[purple] ==0;
			break;
		case MILITAR:
			ret = buildedinfo[red] ==0;
			break;
		case NOBLE:
			ret = buildedinfo[yellow] ==0;
			break;
		case RELIGIOSO:
			ret = buildedinfo[blue] ==0;
			break;
		}
		return ret;
	}

	@Override
	public Distrito[] descartaDistritos(List distritos) {
		Distrito[] descartado = new Distrito[distritos.size() - 1];
		Iterator it = distritos.iterator();
		Distrito selec = null;
		Distrito d;
		int i = 0;
		while (it.hasNext()) {
			d = (Distrito) it.next();
			if (selec == null) {
				selec = d;
			} else {
				if(noTengo(d) && d.getPuntos() > selec.getPuntos()){
					descartado[i++] = selec;
					selec = d;
				}else{
					descartado[i++] = d;
				}
			}
		}
		return descartado;
	}

	private boolean noTengo(Distrito d) {
		boolean ret = false;
		for (Distrito dc : construidas) {
			if(dc.getNombre().compareTo(d.getNombre())==0)
				ret = true;
		}
		if(!ret){
			for (Distrito dc : mano) {
				if(dc.getNombre().compareTo(d.getNombre())==0)
					ret = true;
			}
		}
		return ret;
	}

	@Override
	public boolean seleccionarMonedasOCartas() {
		if (cartasManoNoConstruidas() == 0 && noSoy(Personajes.MAGO) && noSoy(Personajes.ARQUITECTO)) {
			return true;
		}
		return false;
	}

	private boolean noSoy(Personajes pj) {
		boolean ret = true;
		if(pj_actual.compareTo(pj.getPj())==0)
			ret = false;
		return ret;
	}

	@Override
	public Personaje getPersonajeMatar() {
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i].getPj());
		}
		if(llp.contains(Personajes.ARQUITECTO.getPj()))
			return Personajes.ARQUITECTO.getPj();
		else if(llp.contains(Personajes.MERCADER.getPj()))
			return Personajes.MERCADER.getPj();
		else
			return llp.get(dado.nextInt(llp.size()));
	}

	@Override
	public Jugador seleccionarJugadorCambiarCartas(Jugador jug1, Jugador jug2,Jugador jug3) {
		Jugador ret = null;
		ret = jug1;
		if(monedas >= 4){
			if(ret.getMonedas() < jug2.getMonedas())
				ret = jug2;
			if(ret.getMonedas() < jug3.getMonedas())
				ret = jug3;
		}else{
			if(ret.getMano() < jug2.getMano())
				ret = jug2;
			if(ret.getMano() < jug3.getMano())
				ret = jug3;
		}
		return ret;
	}

	@Override
	public void getDistritoDestruir(PedirDistritoJugadores pd,
			DestruirDistrito dd) {
		Distrito basura = Distritos.TABERNA.getDistrito();
		dd.setJugador(pd.getJugador1());
		dd.setDistrito(basura);
		dd.setPago(-1);
	}

	@Override
	public Personaje seleccionarPersonajeRobo() {
		LinkedList<Personaje> llp = Personajes.getNewListaPersonajes();
		llp.remove(Personajes.ASESINO.getPj());
		llp.remove(Personajes.LADRON.getPj());
		for (int i = 0; i < destapados.length; i++) {
			llp.remove(destapados[i].getPj());
		}
		return llp.get(dado.nextInt(llp.size()));
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
		InfoJugador ij;
		if(j.getNombre().compareTo(this.getName())!=0){
			if(hmInfo.containsKey(j.getNombre())){
				ij = hmInfo.get(j.getNombre());
				ij.setJugador(j);
				if(list.size() > maxbuilded)
					maxbuilded = list.size();
				ij.setPersonaje(personaje);
				ij.setConstruidos(list);
				ij.setJugado(b);
				ij.setPuntos(j.getPuntos());
			}else{
				ij = new InfoJugador(j, personaje, list, b);
				if(list.size() > maxbuilded)
					maxbuilded = list.size();
				hmInfo.put(j.getNombre(), ij);
			}
		}else{
			mispuntos = j.getPuntos();
		}
	}

	/******************** Codigo de apoyo para el agente deliberativo **********************/
	
	private class InfoJugador{
		private final String name;
		private int monedas;
		private int mano;
		private int color;
		private Personaje pj;
		private List construidos;
		private boolean jugado;
		private int puntos;
		
		public InfoJugador(Jugador j, Personaje personaje, List list, boolean b) {
			name = j.getNombre();
			monedas = j.getMonedas();
			mano = j.getMano();
			pj = personaje;
			construidos = list;
			jugado = b;
			puntos = j.getPuntos();
		}
		
		public void setPuntos(Integer puntos2) {
			puntos = puntos2;
		}
		public void setJugador(Jugador j) {
			monedas = j.getMonedas();
			mano = j.getMano();
		}
		public void setJugado(boolean b) {
			jugado = b;
		}
		public void setPersonaje(Personaje personaje) {
			pj = personaje;
		}
		
		public void setConstruidos(List list) {
			construidos = list;
		}
	}

	private enum Desire {
		MONEY, CARDS, COLORS, TURN;
		double weight = 0.5;
	}

	private enum Risk {
		KILLED, STOLEN, CHANGED, DELEGATE_TURN;
		double weight;
	}

	private enum Characters {
		ASESINO("Asesino", 1, TipoDistrito.NULL, 0, 0), LADRON("Ladron", 2,
				TipoDistrito.NULL, 0.25, 0), MAGO("Mago", 3, TipoDistrito.NULL,
				0.5, 0.5), REY("Rey", 4, TipoDistrito.NOBLE, 0.6, 0.5), OBISPO(
				"Obispo", 5, TipoDistrito.RELIGIOSO, 0.2, 0.5), MERCADER(
				"Mercader", 6, TipoDistrito.COMERCIAL, 0.75, 0.75), ARQUITECTO(
				"Arquitecto", 7, TipoDistrito.NULL, 0.75, 0.75), CONDOTIERO(
				"Condotiero", 8, TipoDistrito.MILITAR, 0.2, 0.5);

		private final Personaje pj;
		private final int turno;
		private final double killed;
		private final double stolen;

		public Personaje getPj() {
			return pj;
		}

		private Characters(String nombre, int turno, TipoDistrito tp,
				double killed, double stolen) {
			pj = new Personaje();
			pj.setNombre(nombre);
			pj.setTurno(turno);
			pj.setColor(tp.getColor());
			this.killed = killed;
			this.stolen = stolen;
			this.turno = turno;
		}
	}

	private void modifyRisk() {
		if (llr != null) {
			Risk.KILLED.weight = getRiskKilled();
			Risk.STOLEN.weight = getRiskStolen();
			Risk.DELEGATE_TURN.weight = getRiskDelegate();
			Risk.CHANGED.weight = getRiskChanged();
		} else {
			// Se inicializan los peligros
			llr = new LinkedList<Risk>();
			Risk[] r = Risk.values();
			for (int i = 0; i < r.length; i++) {
				if (r[i].compareTo(Risk.KILLED) == 0)
					r[i].weight = 1;
				llr.add(r[i]);
			}
		}
	}

	private double getRiskChanged() {
		double ret = 0.1;
		if(mano.size() > 1)
			ret += 0.1 * alguienSinCartas();
		return ret;
	}

	private double alguienSinCartas() {
		int ret = 0;
		
		Set<String> k = hmInfo.keySet();
		InfoJugador ij;
		for (String string : k) {
			ij = hmInfo.get(string);
			if(ij.mano == 0)
				ret++;
		}
		return ret;
	}

	private double getRiskDelegate() {
		return maxbuilded * (1/8);
	}

	private double getRiskStolen() {
		double ret = 0.2;
		if(getProbMoney() > 0.5 )
			ret += 0.6;
		else if(monedas >= 4)
			ret += 0.8;
		return ret;
	}

	private double getRiskKilled() {
		double ret = 0.2;
		if(soyMasPuntos())
			ret += 0.6;
		return ret;
	}

	private boolean soyMasPuntos() {
		int ret = 0;
		
		Set<String> k = hmInfo.keySet();
		InfoJugador ij;
		for (String string : k) {
			ij = hmInfo.get(string);
			if(ij.puntos >0)
				ret = ij.puntos;
		}
		return mispuntos >= ret;
	}

	private void modifyDesire() {
		if (lld != null) {
			Desire.MONEY.weight = getDesireMoney();
			Desire.CARDS.weight = getDesireCards();
			Desire.COLORS.weight = getDesireColors();
		} else {
			// Se inicializan los deseos
			lld = new LinkedList<Desire>();
			Desire[] d = Desire.values();
			for (int i = 0; i < d.length; i++) {
				if (d[i].compareTo(Desire.MONEY) == 0)
					d[i].weight = 1;
				lld.add(d[i]);
			}
		}
	}

	private double getDesireColors() {
		double ret = 1;
		
		for (int i = 0; i < buildedinfo.length; i++) {
			if(buildedinfo[i] == 0)
				ret -= 0.2;
		}

		return ret;
	}

	private double getDesireCards() {
		double ret = 0.25;
		switch (getDistritosConstruibles().length) {
		case 0:
			ret = 1;
			break;
		case 1:
			ret = 0.75;
			break;
		}
		return ret;
	}

	private double getDesireMoney() {
		double ret = 0.25;
		if (monedas == 0) {
			ret = 1;
		} else {
			int coste = 0;
			Distrito[] d = getDistritosConstruibles();
			for (int i = 0; i < d.length; i++) {
				if (d[i].getCoste() > coste)
					coste = d[i].getCoste();
			}
			switch (coste - monedas) {
			case 3:
				ret = 0.5;
				break;
			case 4:
				ret = 0.75;
				break;
			}
		}
		return ret;
	}

	private void getInfoBuildedCards() {
		buildedinfo[red] = 0;
		buildedinfo[yellow] = 0;
		buildedinfo[blue] = 0;
		buildedinfo[green] = 0;
		buildedinfo[purple] = 0;
		String aux;
		for (Distrito dist : construidas) {
			switch (TipoDistrito.getByColor(dist.getColor())) {
			case COMERCIAL:
				buildedinfo[green]++;
				break;
			case MARAVILLA:
				buildedinfo[purple]++;
				break;
			case MILITAR:
				buildedinfo[red]++;
				break;
			case NOBLE:
				buildedinfo[yellow]++;
				break;
			case RELIGIOSO:
				buildedinfo[blue]++;
				break;

			}

		}

	}

}
