package tablero;

import jade.core.AID;

import java.util.LinkedList;
import java.util.Vector;
import conceptos.Distrito;
import conceptos.Jugador;


public class ResumenJugador {
	
	private final AID identificador;
	private int dinero;
	private int puntos;
	private final LinkedList<Distrito> cartasMano;
	private String personaje;
	private int[] colores= new int[5]; // amar, az, mar, roj, verd
	private Vector<conceptos.Distrito> construido=new Vector<conceptos.Distrito>();
	
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
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int[] getColores() {
		return colores;
	}

	public void setColores(int[] colores) {
		this.colores = colores;
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

	public void setConstruido(Vector<conceptos.Distrito> construido) {
		this.construido = construido;
	}

	public String getPersonaje() {
		return personaje;
	}

	public void setPersonaje(String personaje) {
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
	
	
}
