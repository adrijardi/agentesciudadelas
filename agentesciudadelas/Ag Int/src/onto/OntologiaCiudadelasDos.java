package onto;

import acciones.*;
import conceptos.*;
import jade.content.onto.*;
import jade.content.schema.*;

public class OntologiaCiudadelasDos extends Ontology {

	// Nombre de la Ontologia
	public static final String ONTOLOGY_NAME = "OntologiaCiudadelasDos";

	// Vocabulario de la ontologia
	// /CONCEPTOS:
	public static final String DISTRITO = "Distrito";
	public static final String DISTRITO_NOMBRE = "nombre";
	public static final String DISTRITO_COSTE = "coste";
	public static final String DISTRITO_COLOR = "color";
	public static final String DISTRITO_PUNTOS = "puntos";
	
	public static final String JUGADOR = "Jugador";
	public static final String JUGADOR_NOMBRE = "nombre";
	public static final String JUGADOR_PUNTOS = "puntos";
	public static final String JUGADOR_MANO = "mano";
	public static final String JUGADOR_MONEDAS = "monedas";
	
	public static final String PERSONAJE = "Personaje";
	public static final String PERSONAJE_NOMBRE = "nombre";
	public static final String PERSONAJE_TURNO = "turno";
	public static final String PERSONAJE_COLOR = "color";
	
	// ACCIONES

	public static final String CAMBIARMANO = "CambiarMano";
	public static final String CAMBIARMANO_JUGADOR1 = "jugador1";
	public static final String CAMBIARMANO_JUGADOR2 = "jugador2";
	
	public static final String COBRARDISTRITOS = "CobrarDistritos";
	public static final String COBRARDISTRITOS_JUGADOR = "jugador"; 
	public static final String COBRARDISTRITOS_CANTIDAD = "cantidad";
	public static final String COBRARDISTRITOS_DISTRITO = "distrito";
	
	public static final String COBRARDISTRITOSREY = "CobrarDistritosRey";
	public static final String COBRARDISTRITOSREY_JUGADOR = "jugador";
	
	public static final String COBRARDISTRITOSOBISPO = "CobrarDistritosObispo";
	public static final String COBRARDISTRITOSOBISPO_JUGADOR = "jugador";
	
	public static final String COBRARDISTRITOSMERCADER = "CobrarDistritosMercader";
	public static final String COBRARDISTRITOSMERCADER_JUGADOR = "jugador";
	
	public static final String COBRARDISTRITOSCONDOTIERRO = "CobrarDistritosCondotierro";
	public static final String COBRARDISTRITOSCONDOTIERRO_JUGADOR = "jugador";
	
	public static final String COBRARPORDISTRITOS = "CobrarPorDistritos";
	public static final String COBRARPORDISTRITOS_JUGADOR = "jugador"; 
	public static final String COBRARPORDISTRITOS_CANTIDAD = "cantidad";
	
	public static final String COBRARPORMERCADER = "CobrarPorMercader";
	public static final String COBRARPORMERCADER_JUGADOR = "jugador"; 
	public static final String COBRARPORMERCADER_CANTIDAD = "cantidad";
	
	public static final String DARDISTRITOS = "DarDistritos";
	public static final String DARDISTRITOS_DISTRITOS = "distritos";

	public static final String DARMONEDAS = "DarMonedas";
	public static final String DARMONEDAS_MONEDAS = "monedas";
	
	public static final String DESTRUIRDISTRITO = "DestruirDistrito";
	public static final String DESTRUIRDISTRITO_JUGADOR = "jugador";
	public static final String DESTRUIRDISTRITO_DISTRITO = "distrito";
	public static final String DESTRUIRDISTRITO_PAGO = "pago";
	
	public static final String ELEGIRPERSONAJE = "ElegirPersonaje";
	public static final String ELEGIRPERSONAJE_PERSONAJE = "personaje";
	public static final String ELEGIRPERSONAJE_JUGADOR = "jugador";
	
	public static final String NOTIFICARCORONA = "NotificarCorona";
	public static final String NOTIFICARCORONA_JUGADOR = "jugador";
	
	public static final String NOTIFICARFINTURNOJUGADOR = "NotificarFinTurnoJugador";
	public static final String NOTIFICARFINTURNOJUGADOR_PERSONAJE = "personaje";
	public static final String NOTIFICARFINTURNOJUGADOR_JUGADOR = "jugador";
	
	public static final String PAGARDISTRITO = "PagarDistrito";
	public static final String PAGARDISTRITO_JUGADOR = "jugador"; 
	public static final String PAGARDISTRITO_CANTIDAD = "cantidad";
	public static final String PAGARDISTRITO_DISTRITO = "distrito";
	
	public static final String PEDIRCONSTRUIRDISTRITO = "PedirConstruirDistrito";
	public static final String PEDIRCONSTRUIRDISTRITO_JUGADOR = "jugador"; 
	public static final String PEDIRCONSTRUIRDISTRITO_PERSONAJE = "personaje";
	public static final String PEDIRCONSTRUIRDISTRITO_DISTRITO = "distrito";
	
	public static final String SUSTITUIRMANO = "SustituirMano";
	public static final String SUSTITUIRMANO_DISTRITOS = "distritos";
	
	public static final String  OFERTARPERSONAJES = "OfertarPersonajes";
	public static final String  OFERTARPERSONAJES_JUGADOR = "jugador";
	public static final String  OFERTARPERSONAJES_DISPONIBLES = "disponibles";

	
	public static final String  SELECCIONARPERSONAJE  = "SeleccionarPersonaje";
	public static final String  SELECCIONARPERSONAJE_ID_JUGADOR = "id_jugador";


	public static final String  NOTIFICARDESCARTADOS = "NotificarDescartados";
	public static final String  NOTIFICARDESCARTADOS_DESTAPADO = "destapado";
	
	// Protected constructor is sufficient to suppress unauthorized calls to the
	// constructor
	private OntologiaCiudadelasDos() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		
		try {
			ConceptSchema cs;
			AgentActionSchema as;
			
			// Anyade los elementos
			add(new ConceptSchema(DISTRITO), Distrito.class);
			add(new ConceptSchema(JUGADOR), Jugador.class);
			add(new ConceptSchema(PERSONAJE), Personaje.class);
			
			// add(new PredicateSchema(OFERTA), Oferta.class);
			add(new AgentActionSchema(CAMBIARMANO), CambiarMano.class);
			add(new AgentActionSchema(COBRARDISTRITOS), CobrarDistritos.class);
			add(new AgentActionSchema(COBRARPORDISTRITOS), CobrarPorDistritos.class);
			add(new AgentActionSchema(COBRARDISTRITOSREY), CobrarDistritosRey.class);
			add(new AgentActionSchema(COBRARDISTRITOSOBISPO), CobrarDistritosObispo.class);
			add(new AgentActionSchema(COBRARDISTRITOSMERCADER), CobrarDistritosMercader.class);
			add(new AgentActionSchema(COBRARDISTRITOSCONDOTIERRO), CobrarDistritosCondotierro.class);
			add(new AgentActionSchema(COBRARPORMERCADER), CobrarPorMercader.class);
			add(new AgentActionSchema(DARDISTRITOS), DarDistritos.class);
			add(new AgentActionSchema(DARMONEDAS), DarMonedas.class);
			add(new AgentActionSchema(DESTRUIRDISTRITO), DestruirDistrito.class);
			add(new AgentActionSchema(ELEGIRPERSONAJE), ElegirPersonaje.class);
			add(new AgentActionSchema(NOTIFICARCORONA), NotificarCorona.class);
			add(new AgentActionSchema(NOTIFICARFINTURNOJUGADOR), NotificarFinTurnoJugador.class);
			add(new AgentActionSchema(PAGARDISTRITO), PagarDistrito.class);
			add(new AgentActionSchema(PEDIRCONSTRUIRDISTRITO), PedirConstruirDistrito.class);
			add(new AgentActionSchema(SUSTITUIRMANO), SustituirMano.class);
			add(new AgentActionSchema(OFERTARPERSONAJES), OfertarPersonajes.class);
			add(new AgentActionSchema(SELECCIONARPERSONAJE), SeleccionarPersonaje.class);
			add(new AgentActionSchema(NOTIFICARDESCARTADOS), NotificarDescartados.class);
			

			// Estructura del esquema para el concepto DISTRITO
			cs = (ConceptSchema) getSchema(DISTRITO);
			cs.add(DISTRITO_NOMBRE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DISTRITO_COSTE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(DISTRITO_COLOR, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DISTRITO_PUNTOS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			
			cs = (ConceptSchema) getSchema(JUGADOR);
			cs.add(JUGADOR_NOMBRE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(JUGADOR_PUNTOS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(JUGADOR_MANO, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(JUGADOR_MONEDAS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			
			cs = (ConceptSchema) getSchema(PERSONAJE);
			cs.add(PERSONAJE_NOMBRE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(PERSONAJE_TURNO, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(PERSONAJE_COLOR, (PrimitiveSchema) getSchema(BasicOntology.STRING));

			// Estructura del esquema para el predicado
			// PredicateSchema ps = (PredicateSchema) getSchema(OFERTA);
			// ps.add(OFERTA_FRUTA, (ConceptSchema) getSchema(FRUTA));

			// Estructura del esquema para la accion
			as = (AgentActionSchema) getSchema(DARDISTRITOS);
			/* accion.anyadir(<nombre del atributo>, <tipo_atrib>, <min>,<max>) */
			as.add(DARDISTRITOS_DISTRITOS, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(DARMONEDAS);
			as.add(DARMONEDAS_MONEDAS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			
			as = (AgentActionSchema) getSchema(NOTIFICARCORONA);
			as.add(NOTIFICARCORONA_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(NOTIFICARFINTURNOJUGADOR);
			as.add(NOTIFICARFINTURNOJUGADOR_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			as.add(NOTIFICARFINTURNOJUGADOR_JUGADOR, (ConceptSchema) getSchema(JUGADOR));

			as = (AgentActionSchema) getSchema(PEDIRCONSTRUIRDISTRITO);
			as.add(PEDIRCONSTRUIRDISTRITO_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			as.add(PEDIRCONSTRUIRDISTRITO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(PEDIRCONSTRUIRDISTRITO_DISTRITO, (ConceptSchema) getSchema(DISTRITO));
			
			as = (AgentActionSchema) getSchema(PEDIRCONSTRUIRDISTRITO);
			as.add(PEDIRCONSTRUIRDISTRITO_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			as.add(PEDIRCONSTRUIRDISTRITO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(PEDIRCONSTRUIRDISTRITO_DISTRITO, (ConceptSchema) getSchema(DISTRITO));
			
			as = (AgentActionSchema) getSchema(COBRARDISTRITOS);
			as.add(COBRARDISTRITOS_CANTIDAD, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(COBRARDISTRITOS_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(COBRARDISTRITOS_DISTRITO, (ConceptSchema) getSchema(DISTRITO));
			
			as = (AgentActionSchema) getSchema(PAGARDISTRITO);
			as.add(PAGARDISTRITO_CANTIDAD, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(PAGARDISTRITO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(PAGARDISTRITO_DISTRITO, (ConceptSchema) getSchema(DISTRITO));
			
			as = (AgentActionSchema) getSchema(COBRARDISTRITOSREY);
			as.add(COBRARDISTRITOSREY_JUGADOR, (ConceptSchema) getSchema(JUGADOR));

			as = (AgentActionSchema) getSchema(COBRARDISTRITOSOBISPO);
			as.add(COBRARDISTRITOSOBISPO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));

			as = (AgentActionSchema) getSchema(COBRARDISTRITOSMERCADER);
			as.add(COBRARDISTRITOSMERCADER_JUGADOR, (ConceptSchema) getSchema(JUGADOR));

			as = (AgentActionSchema) getSchema(COBRARDISTRITOSCONDOTIERRO);
			as.add(COBRARDISTRITOSCONDOTIERRO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));


			as = (AgentActionSchema) getSchema(COBRARPORDISTRITOS);
			as.add(COBRARPORDISTRITOS_CANTIDAD, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(COBRARPORDISTRITOS_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(COBRARPORMERCADER);
			as.add(COBRARPORMERCADER_CANTIDAD, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(COBRARPORMERCADER_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(CAMBIARMANO);
			as.add(CAMBIARMANO_JUGADOR1, (ConceptSchema) getSchema(JUGADOR));
			as.add(CAMBIARMANO_JUGADOR2, (ConceptSchema) getSchema(JUGADOR));

			
			as = (AgentActionSchema) getSchema(SUSTITUIRMANO);
			as.add(SUSTITUIRMANO_DISTRITOS, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			
			as = (AgentActionSchema) getSchema(DESTRUIRDISTRITO);
			as.add(DESTRUIRDISTRITO_DISTRITO, (ConceptSchema) getSchema(DISTRITO));
			as.add(DESTRUIRDISTRITO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(DESTRUIRDISTRITO_PAGO, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			
			as = (AgentActionSchema) getSchema(ELEGIRPERSONAJE);
			as.add(ELEGIRPERSONAJE_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(ELEGIRPERSONAJE_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			
			as = (AgentActionSchema) getSchema(OFERTARPERSONAJES);
			as.add(OFERTARPERSONAJES_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(OFERTARPERSONAJES_DISPONIBLES, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), 0, ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(SELECCIONARPERSONAJE);
			as.add(SELECCIONARPERSONAJE_ID_JUGADOR, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));

			as = (AgentActionSchema) getSchema(NOTIFICARDESCARTADOS);
			as.add(NOTIFICARDESCARTADOS_DESTAPADO, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			
		} catch (OntologyException oe) {
			oe.printStackTrace();
		}

	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	/*
	 * private static class SingletonHolder { private final static
	 * OntologiaCiudadelasDos INSTANCE = new OntologiaCiudadelasDos(); }
	 * 
	 * public static OntologiaCiudadelasDos getInstance() { return
	 * SingletonHolder.INSTANCE; }
	 */

	// Creamos referencia estatica a la propia onto
	private static OntologiaCiudadelasDos instance = new OntologiaCiudadelasDos();

	// Creamos m�todo para devolver la referencia estatica
	public static OntologiaCiudadelasDos getInstance() {
		return instance;
	}

}
