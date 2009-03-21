package utils;

public enum TipoDistrito {
	MARAVILLA("maravilla","morado"),
	MILITAR("militar", "rojo"),
	NOBLE("noble","amarillo"),
	RELIGIOSO("religioso","azul"),
	COMERCIAL("comercial","verde");
	
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
}
