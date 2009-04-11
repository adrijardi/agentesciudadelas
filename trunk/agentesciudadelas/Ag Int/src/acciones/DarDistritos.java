package acciones;

import jade.content.AgentAction;
import jade.util.leap.ArrayList;
import conceptos.Distrito;

public class DarDistritos implements AgentAction {

	private Distrito[] distritos;

	public void setDistritos(jade.util.leap.List l){
		Distrito[] d = new Distrito[l.size()];
		for (int i = 0; i < d.length; i++) {
			d[i] = (Distrito)l.get(i);
		}
		distritos = d;
	}
	
	public void setDistritos(Distrito[] d){
		distritos = d;
	}
	
	public jade.util.leap.List getDistritos(){
		ArrayList al = new ArrayList(distritos.length);
		for (int i = 0; i < distritos.length; i++) {
			al.add(i, distritos[i]);
		}
		return al;
	}
}