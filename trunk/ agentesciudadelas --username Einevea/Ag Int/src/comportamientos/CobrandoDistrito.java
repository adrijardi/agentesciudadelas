package comportamientos;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import tablero.AgTablero;
import tablero.EstadoPartida;
import acciones.CobrarDistritos;
import acciones.PagarDistrito;
import acciones.PedirConstruirDistrito;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

/*
 * este comportamiento recibe el mensaje de que se ha pagado el distrito, y actualiza el estado de la partida actual
 */
public class CobrandoDistrito extends Behaviour {

	private final AgTablero agt;

	public CobrandoDistrito(AgTablero agTablero) {
		agt = agTablero;
	}

	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		block();
		/*
		 * a la espera de q llege un mensaje del agente pidiendo construir el distrito
		 */
		MessageTemplate filtroIdentificador = MessageTemplate.MatchOntology("PagarDistrito");
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.receive(plantilla);
		if(msg!=null){
			/*
			 * comprobar que el distrito que me ha enviado es valido, vamos q no lo tiene ya construido y en ese momento pedirle la pasta o negarselo
			 */
			ContentElement contenido = null;
			try {
				contenido=myAgent.getContentManager().extractContent(msg);
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
			// tengo el mensaje y su contenido, ahora a actualizar el estado actual
			
			PagarDistrito pd=(PagarDistrito)contenido;
			Jugador jg=pd.getJugador();
			int dinero=pd.getCantidad();
			Distrito ds=pd.getDistrito();
			
			/*
			 * deberia entrar siempre, esto es solo por seguridad
			 */
			if(jg.getNombre().equals(ep.getResJugadorActual().getIdentificador().getName())){
				ep.getResJugadorActual().aniadirDistrito(ds);
				ep.getResJugadorActual().setDinero(ep.getResJugadorActual().getDinero()-dinero);
			}else{
				System.out.println("FALLO - el jugador que me esta pidiento eso no es el jugador actual, se ha saltado el filtro");
			}
		
		}
		
	}

	@Override
	public boolean done() {
		return true;// siempre termina
	}
}