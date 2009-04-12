package comportamientos_jugador;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.CobrarDistritos;
import acciones.PedirConstruirDistrito;
import conceptos.Distrito;

public class ConstruirDistritoJugador extends Behaviour {

	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public ConstruirDistritoJugador(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}

	@Override
	public void action() {
		Distrito dist = _agj.getDistritoConstruir();
		if (dist != null) {
			PedirConstruirDistrito pcd = new PedirConstruirDistrito();
			pcd.setDistrito(dist);
			pcd.setJugador(_agj.getJugador());
			pcd.setPersonaje(_agj.getPj_actual());
			_agj.sendMSG(ACLMessage.REQUEST, raid, pcd,
					Filtros.PEDIRCONSTRUIRDISTRITO);

			ACLMessage msg = _agj.reciveBlockingMessage(
					Filtros.COBRARDISTRITOS, false);

			try {
				CobrarDistritos contenido = (CobrarDistritos) _agj
						.getContentManager().extractContent(msg);
				int cantidad = contenido.getCantidad().intValue();
				if (cantidad >= 0) {
					_agj.addMonedas(-cantidad);
					_agj.construir(dist);
				} else {
					System.out.println("NO PUEDES CONSTRUIR EL DISTRITO");
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
		}
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}

}
