package conceptos;

import jade.content.Concept;

public class Error implements Concept {
	
	private Integer id;
	private String mensaje;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
