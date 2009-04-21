package comportamientos_jugador;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.DarDistritos;

public class ActualizarCartas extends Behaviour{
	private final AgJugador _agj;
	private boolean fin = false;

	public ActualizarCartas(AgJugador agj) {
		_agj = agj;
	}

	@Override
	public void action() {
		System.out.println("-----------------EMPIEZA---------------");

		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.NOTIFICARMANO,  false, 100);
		fin = false;
		try {
			System.out.println("FUERAAAA");
			if(msg != null){
				fin = true;
				System.out.println("ENTRAAA");
				DarDistritos contenido = (DarDistritos) _agj.getContentManager().extractContent(msg);
				_agj.addDistritos(contenido.getDistritos());
				_agj.cambiarMano(contenido.getDistritos());
			}
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-----------------TERMINA---------------");
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return fin;
	}

}


