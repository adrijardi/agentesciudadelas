package comportamientos_jugador;

import acciones.DarTurno;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;

public class JugarPartida extends Behaviour {
	
	private final AgJugador _agj;

	public JugarPartida(AgJugador agj) {
		_agj = agj;
	}

	@Override
	public void action() {
		//Se espera a que el tablero nos entrege el turno
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.NOTIFICARTURNO, false);
		
		if(msg != null){
			try {
				DarTurno msgTurno = (DarTurno) _agj.getContentManager().extractContent(msg);
				if(!msgTurno.getMuerto()){
					//Se realizan las acciones definidas por el agente
					_agj.addBehaviour(_agj.jugarTurno(msg));
					if(msgTurno.getHaymuerto()){
						_agj.set_muerto(msgTurno.getPersonaje());
					}else{
						_agj.set_muerto(null);
					}
					
					if(msgTurno.getHayrobado()){
						_agj.set_robado(msgTurno.getPersonajerobado());
					}else{
						_agj.set_robado(null);
					}
					
				}else{
					// Si estás muerto vuelves a esperar la siguiente elección de jugador
					_agj.addBehaviour(new ElegirPersonajeJugador(_agj));
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
		return true;
	}

}
