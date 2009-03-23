package utils;

import conceptos.Distrito;

/*
 * Esto esta hecho de manera burda,
 * Definición de los distritos disponibles
 */
public enum Distritos {
	//Cantidad, nombre, coste, tipo, puntos
	//MARAVILLAS
	ESCUELA_MAGIA(1,"Escuela de magia", 6, TipoDistrito.MARAVILLA),
	CEMENTERIO(1,"Cementerio", 5, TipoDistrito.MARAVILLA),
	PUERTA_DRAGON(1,"Puerta Dragón", 6, TipoDistrito.MARAVILLA, 8),
	FABRICA(1,"Fábrica", 5, TipoDistrito.MARAVILLA),
	LABORATORIO(1,"Laboratorio", 5, TipoDistrito.MARAVILLA),
	OBSERVATORIO(1,"Observatorio", 5, TipoDistrito.MARAVILLA),
	FUENTE_DESEOS(1,"Fuente de los deseos", 5, TipoDistrito.MARAVILLA),
	CANTERA(1,"Cantera", 5, TipoDistrito.MARAVILLA),
	TESORO_IMPERIAL(1,"Tesoro imperial", 4, TipoDistrito.MARAVILLA),
	TORREON(1,"Torreón", 3, TipoDistrito.MARAVILLA),
	GRAN_MURALLA(1,"Gran muralla", 6, TipoDistrito.MARAVILLA),
	BIBLIOTECA(1,"Biblioteca", 6, TipoDistrito.MARAVILLA),
	UNIVERSIDAD(1,"Universidad", 6, TipoDistrito.MARAVILLA, 8),
	PATIO_MILAGROS(1,"Patio de los milagros", 2, TipoDistrito.MARAVILLA),
	//CONDOTIERO
	ATALAYA(3,"Atalaya", 1, TipoDistrito.MILITAR),
	PRISION(3,"Prision", 2, TipoDistrito.MILITAR),
	CUARTEL(3,"Cuartel", 3, TipoDistrito.MILITAR),
	FORTALEZA(3,"Fortaleza", 5, TipoDistrito.MILITAR),
	//OBISPO
	TEMPLO(3,"Templo", 1, TipoDistrito.RELIGIOSO),
	IGLESIA(3,"Iglesia", 2, TipoDistrito.RELIGIOSO),
	MONASTERIO(4,"Monasterio", 3, TipoDistrito.RELIGIOSO),
	CATEDRAL(2,"Catedral", 5, TipoDistrito.RELIGIOSO),
	//REY
	SENYORIO(5,"Señorio", 3, TipoDistrito.NOBLE),
	CASTILLO(5,"Castillo", 4, TipoDistrito.NOBLE),
	PALACIO(2,"Palacio", 5, TipoDistrito.NOBLE),
	//MERCADER
	TABERNA(5,"Taberna", 1, TipoDistrito.COMERCIAL),
	MERCADO(4,"Mercado", 2, TipoDistrito.COMERCIAL),
	TIENDA(4,"Tienda", 2, TipoDistrito.COMERCIAL),
	ALMACENES(3,"Almacenes", 3, TipoDistrito.COMERCIAL),
	PUERTO(3,"Puerto", 4, TipoDistrito.COMERCIAL),
	AYUNTAMIENTO(2,"Ayuntamiento", 5, TipoDistrito.COMERCIAL);

	private final Distrito d;
	private final int cantidad;
	private final TipoDistrito tp;
	
	private Distritos(int cantidad, String name, int coste, TipoDistrito tp, int puntos){
		d = new Distrito();
		d.setNombre(name);
		d.setCoste(coste);
		this.tp = tp;
		d.setColor(tp.getColor());
		d.setPuntos(puntos);
		this.cantidad = cantidad;
	}
	
	private Distritos(int cantidad, String name, int coste, TipoDistrito tp){
		this(cantidad, name, coste, tp, coste);
	}

	public int getCantidad() {
		return cantidad;
	}

	public Distrito getDistrito() {
		return d;
	}
	
	public TipoDistrito getTipoDistrito() {
		return tp;
	}
}
