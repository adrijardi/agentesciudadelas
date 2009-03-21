package tablero;

import java.util.LinkedList;
import java.util.Random;

import utils.Distritos;

import conceptos.Distrito;

public class Mazo {
	private final LinkedList<Distrito> distritos;
	private final LinkedList<Distrito> descarte;

	// Protected constructor is sufficient to suppress unauthorized calls to the
	// constructor
	private Mazo() {
		distritos = new LinkedList<Distrito>();
		descarte = new LinkedList<Distrito>();
		newPartida();
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		private final static Mazo INSTANCE = new Mazo();
	}

	public static Mazo getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private void newPartida() {
		descarte.clear();
		LinkedList<Distrito> mazo = new LinkedList<Distrito>();
		anyadirDistritos(mazo, Distritos.ALMACENES);
		anyadirDistritos(mazo, Distritos.ATALAYA);
		anyadirDistritos(mazo, Distritos.AYUNTAMIENTO);
		anyadirDistritos(mazo, Distritos.BIBLIOTECA);
		anyadirDistritos(mazo, Distritos.CANTERA);
		anyadirDistritos(mazo, Distritos.CASTILLO);
		anyadirDistritos(mazo, Distritos.CATEDRAL);
		anyadirDistritos(mazo, Distritos.CEMENTERIO);
		anyadirDistritos(mazo, Distritos.CUARTEL);
		anyadirDistritos(mazo, Distritos.ESCUELA_MAGIA);
		anyadirDistritos(mazo, Distritos.FABRICA);
		anyadirDistritos(mazo, Distritos.FORTALEZA);
		anyadirDistritos(mazo, Distritos.FUENTE_DESEOS);
		anyadirDistritos(mazo, Distritos.GRAN_MURALLA);
		anyadirDistritos(mazo, Distritos.IGLESIA);
		anyadirDistritos(mazo, Distritos.LABORATORIO);
		anyadirDistritos(mazo, Distritos.MERCADO);
		anyadirDistritos(mazo, Distritos.MONASTERIO);
		anyadirDistritos(mazo, Distritos.OBSERVATORIO);
		anyadirDistritos(mazo, Distritos.PALACIO);
		anyadirDistritos(mazo, Distritos.PATIO_MILAGROS);
		anyadirDistritos(mazo, Distritos.PRISION);
		anyadirDistritos(mazo, Distritos.PUERTA_DRAGON);
		anyadirDistritos(mazo, Distritos.PUERTO);
		anyadirDistritos(mazo, Distritos.SENYORIO);
		anyadirDistritos(mazo, Distritos.TABERNA);
		anyadirDistritos(mazo, Distritos.TEMPLO);
		anyadirDistritos(mazo, Distritos.TESORO_IMPERIAL);
		anyadirDistritos(mazo, Distritos.TIENDA);
		anyadirDistritos(mazo, Distritos.TORREON);
		anyadirDistritos(mazo, Distritos.UNIVERSIDAD);
		barajarDistritos(mazo.toArray(new Distrito[0]));
	}

	private void barajarDistritos(Distrito[] mazo) {
		Distrito aux;
		int times = mazo.length * 2;
		int num1;
		int num2;
		Random r = new Random();
		for (int i = 0; i < times; i++) {
			num1 = r.nextInt(mazo.length);
			num2 = r.nextInt(mazo.length);
			aux = mazo[num1];
			mazo[num1] = mazo[num2];
			mazo[num2] = aux;
		}

		distritos.clear();
		for (int i = 0; i < mazo.length; i++) {
			distritos.add(mazo[i]);
		}
	}

	private void anyadirDistritos(LinkedList<Distrito> mazo, Distritos dist) {
		for (int i = 0; i < dist.getCantidad(); i++) {
			mazo.add(dist.getDistrito());
		}
	}

	public Distrito getDistrito() {
		Distrito ret;
		if (distritos.size() > 0) {
			ret = distritos.removeFirst();
		} else {
			Distrito[] mazo = descarte.toArray(new Distrito[0]);
			descarte.clear();
			barajarDistritos(mazo);
			ret = distritos.removeFirst();
		}
		return ret;
	}
	
	public void trashDistrito(Distrito d){
		descarte.push(d);
	}
	
	public void printMazo(){
		for (Distrito d : distritos) {
			System.out.println(d.getNombre());
		}
	}
}
