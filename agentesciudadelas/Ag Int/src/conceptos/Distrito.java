package conceptos;

import jade.content.Concept;

public class Distrito implements Concept {
	
	private String nombre;
	private Integer coste;
	private String color;
	private Integer puntos;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCoste() {
		return coste;
	}
	public void setCoste(Integer coste) {
		this.coste = coste;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getPuntos() {
		return puntos;
	}
	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	
}
