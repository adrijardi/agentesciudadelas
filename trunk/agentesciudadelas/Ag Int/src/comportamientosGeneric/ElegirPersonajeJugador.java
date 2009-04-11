package comportamientosGeneric;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.ElegirPersonaje;
import acciones.OfertarPersonajes;
import conceptos.Personaje;

public class ElegirPersonajeJugador extends Behaviour {

	private final AgJugador _agj;

	public ElegirPersonajeJugador(AgJugador agJugador) {
		_agj = agJugador;
	}

	@Override
	public void action() {

		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.OFERTARPERSONAJES, true);

		try {
			OfertarPersonajes contenido = (OfertarPersonajes) _agj.getContentManager().extractContent(msg);
			Personaje seleccionado = _agj.selectPersonaje(contenido);
			
			ElegirPersonaje salida = new ElegirPersonaje();
			salida.setJugador(_agj.getJugador());
			salida.setPersonaje(seleccionado);
			_agj.sendMSG(ACLMessage.REQUEST, msg.getSender(), salida,Filtros.ELEGIRPERSONAJE);
			
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

	}

	@Override
	public boolean done() {
		_agj.addBehaviour(new JugarPartida(_agj));
		return true;
	}

}
