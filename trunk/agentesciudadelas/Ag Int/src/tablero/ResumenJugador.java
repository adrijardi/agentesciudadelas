package tablero;

import jade.core.AID;

import java.util.LinkedList;
import java.util.Vector;

import utils.TipoDistrito;

import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;


public class ResumenJugador {
	
	private final AID identificador;
	private int dinero;
	private int puntos;
	private final LinkedList<Distrito> cartasMano;
	private Personaje personaje;
	private int[] colores= new int[5]; // amar, az, mar, roj, verd
	private Vector<conceptos.Distrito> construido=new Vector<conceptos.Distrito>();
	private int construidos;
	private int numVecesMuerto = 0;
	private int numVecesRobado = 0;
	private boolean jugo;
	
	
	
	public boolean getJugo() {
		return jugo;
	}

	public void setJugo(boolean jugo) {
		this.jugo = jugo;
	}

	public void addVecesRobado(){
		numVecesRobado++;
	}
	
	public int getNumVecesRobado() {
		return numVecesRobado;
	}

	public void addVecesMuerto(){
		numVecesMuerto++;
	}
	
	public int getNumVecesMuerto() {
		return numVecesMuerto;
	}

	ResumenJugador(AID id){
		Mazo mazo = Mazo.getInstance();
		cartasMano = new LinkedList<Distrito>();
		cartasMano.add(mazo.getDistrito());
		cartasMano.add(mazo.getDistrito());
		cartasMano.add(mazo.getDistrito());
		cartasMano.add(mazo.getDistrito());
		dinero=2;
		puntos=0;
		identificador = id;
		for(int i=0;i<colores.length;i++) colores[i]=0;
		construidos = 0;
	}
	
	public AID getIdentificador() {
		return identificador;
	}

	public Distrito[] getCartasMano() {
		return cartasMano.toArray(new Distrito[0]);
	}
	
	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	public int getPuntos() {
		int pnts = 0;
		pnts += EstadoPartida.getInstance().getPnts8dist();
		if(construidos5Colores())
			pnts += 3;
		for (Distrito d : construido) {
			pnts += d.getPuntos();
		}
		puntos = pnts;
		return pnts;
	}
	
	public boolean construidos5Colores(){
		String color;
		boolean azul, rojo, amarillo, morado, verde;
		azul = rojo = amarillo = morado = verde = false;
		for (Distrito d : construido) {
			switch (TipoDistrito.getByColor(d.getColor())) {
			case COMERCIAL:
				verde = true;
				break;
			case MARAVILLA:
				morado = true;
				break;
			case MILITAR:
				rojo = true;
				break;
			case NOBLE:
				amarillo = true;
				break;
			case RELIGIOSO:
				azul = true;
				break;
			}
		}
		
		
		return azul && rojo && amarillo && morado && verde;
	}

	public int getNumCartasColor(TipoDistrito tipo) {
		switch (tipo) {
		case NOBLE:
			return colores[0];
		case RELIGIOSO:
			return colores[1];
		case  MARAVILLA:
			return colores[2];
		case MILITAR:
			return colores[3];
		case COMERCIAL:
			return colores[4];
		default:
			return -1;
		}
	}
	
	public int aniadirDistrito(conceptos.Distrito carta){
		
	//	if(construido.contains(carta)) return -1; //creo q no vale, xq puede haber varios objetos con el mismo nombre
		for(int i=0;i<construido.size();i++){
			if(construido.get(i).getNombre().equalsIgnoreCase(carta.getNombre()))
				return -1;
		}
		
		if(carta.getColor().equalsIgnoreCase("amarillo")) {
			colores[0]++;
		}else if(carta.getColor().equalsIgnoreCase("azul")) {
			colores[1]++;
		}else if(carta.getColor().equalsIgnoreCase("maravilla")) {
			colores[2]++;
		}else if(carta.getColor().equalsIgnoreCase("rojo")) {
			colores[3]++;
		}else if(carta.getColor().equalsIgnoreCase("verde")) {
			colores[4]++;
		}
		
		boolean temp=true;
		for(int i=0;i<colores.length;i++){
			if(colores[i]==0) temp=false;
		}
		if(temp) puntos+=3;
		puntos=puntos+carta.getPuntos();
		construido.add(carta);
		return 1;
	}
	
	public int quitarDistrito(conceptos.Distrito carta){
		
		boolean esta=false;	
		for(int i=0;i<construido.size();i++){
			if(construido.get(i).getNombre().equalsIgnoreCase(carta.getNombre()))
				esta=true;
		}

		if(!esta) return -1;
		
		if(carta.getColor().equalsIgnoreCase("amarillo")) {
			colores[0]--;
		}else if(carta.getColor().equalsIgnoreCase("azul")) {
			colores[1]--;
		}else if(carta.getColor().equalsIgnoreCase("maravilla")) {
			colores[2]--;
		}else if(carta.getColor().equalsIgnoreCase("rojo")) {
			colores[3]--;
		}else if(carta.getColor().equalsIgnoreCase("verde")) {
			colores[4]--;
		}

		boolean temp=true;
		for(int i=0;i<colores.length;i++){
			if(colores[i]==0) temp=false;
		}
		if(temp) puntos+=3;
		puntos=puntos+carta.getPuntos();
		construido.remove(carta);
		return 1;
	}

	public Vector<conceptos.Distrito> getConstruido() {
		return construido;
	}

	public Distrito[] getConstruido2(){
		Distrito[] d = new Distrito[construido.size()];
		for(int i = 0; i < construido.size(); i++){
			d[i] = construido.get(i);
		}
		return d;
	}
		
	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public Jugador getJugador(){
		Jugador j = new Jugador();
		j.setMano(cartasMano.size());
		j.setMonedas(dinero);
		j.setNombre(identificador.getName());
		j.setPuntos(puntos);
		return j;
	}
	
	public boolean esJugador(Jugador nombre){
		return nombre.getNombre().compareTo(identificador.getName())==0;
	}
	public boolean tieneDistrito(Distrito d){
		for(int i=0;i<construido.size();i++){
			if(construido.get(i).getNombre().equalsIgnoreCase(d.getNombre()))
				return true;
		}
		return false;
	}
	
	public void cambiarMano(ResumenJugador rj){
		Distrito[] orig=getCartasMano();
		Distrito[] dest=rj.getCartasMano();
		cartasMano.clear();
		for (int i=0;i<dest.length;i++){
			cartasMano.add(dest[i]);
		}
		rj.cartasMano.clear();
		for (int i=0;i<orig.length;i++){
			rj.cartasMano.add(orig[i]);
		}
	}
	
	// Sin probar
	public void quitarCartaMano(conceptos.Distrito carta){
		cartasMano.remove((conceptos.Distrito)carta);
	}
	public void anyadirCartaMano(conceptos.Distrito carta){
		cartasMano.add(carta);
	}

	public void construir(Distrito distrito) {
		cartasMano.remove(distrito);
		construido.add(distrito);
		dinero -= distrito.getCoste();
		construidos++;
	}

	public boolean puedeConstruir(Distrito distrito) {
		boolean ret = false;
		ret = this.cartasMano.contains(distrito);
		ret = !this.construido.contains(distrito);
		return ret;
	}

	public int getConstruidos() {
		return construidos;
	}
}
