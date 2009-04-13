package utils;

public enum TipoDistrito {
	MARAVILLA("maravilla","morado"),
	MILITAR("militar", "rojo"),
	NOBLE("noble","amarillo"),
	RELIGIOSO("religioso","azul"),
	COMERCIAL("comercial","verde"),
	NULL("ninguno","ninguno");
	
	private final String name;
	private final String color;
	
	TipoDistrito(String name, String color){
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public static TipoDistrito getByColor(String color2) {
		TipoDistrito[] distritos = values();
		TipoDistrito dist = NULL;
		for (int i = 0; i < distritos.length; i++) {
			if(distritos[i].getColor().compareTo(color2)==0)
				dist = distritos[i];
		}
		return dist;
	}
}
