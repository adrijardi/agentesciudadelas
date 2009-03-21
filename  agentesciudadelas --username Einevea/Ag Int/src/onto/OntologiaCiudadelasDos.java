package onto;

import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.NotificarCorona;
import conceptos.Distrito;
import conceptos.Jugador;
import jade.content.onto.*;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;

public class OntologiaCiudadelasDos extends Ontology {

	// Nombre de la Ontologia
	public static final String ONTOLOGY_NAME = "OntologiaCiudadelasDos";

	// Vocabulario de la ontología
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
	
	// ACCIONES
	public static final String DARDISTRITOS = "DarDistritos";
	public static final String DARDISTRITOS_DISTRITOS = "distritos";

	public static final String DARMONEDAS = "DarMonedas";
	public static final String DARMONEDAS_MONEDAS = "monedas";
	
	public static final String NOTIFICARCORONA = "NotificarCorona";
	public static final String NOTIFICARCORONA_JUGADOR = "jugador";

	// Protected constructor is sufficient to suppress unauthorized calls to the
	// constructor
	private OntologiaCiudadelasDos() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		
		try {
			ConceptSchema cs;
			AgentActionSchema as;
			
			// Añade los elementos
			add(new ConceptSchema(DISTRITO), Distrito.class);
			add(new ConceptSchema(JUGADOR), Jugador.class);
			// add(new PredicateSchema(OFERTA), Oferta.class);
			add(new AgentActionSchema(DARDISTRITOS), DarDistritos.class);
			add(new AgentActionSchema(DARMONEDAS), DarMonedas.class);
			add(new AgentActionSchema(NOTIFICARCORONA), NotificarCorona.class);

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

			// Estructura del esquema para el predicado
			// PredicateSchema ps = (PredicateSchema) getSchema(OFERTA);
			// ps.add(OFERTA_FRUTA, (ConceptSchema) getSchema(FRUTA));

			// Estructura del esquema para la acción
			as = (AgentActionSchema) getSchema(DARDISTRITOS);
			/* accion.añadir(<nombre del atributo>, <tipo_atrib>, <min>,<max>) */
			as.add(DARDISTRITOS_DISTRITOS, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(DARMONEDAS);
			as.add(DARMONEDAS_MONEDAS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			
			as = (AgentActionSchema) getSchema(NOTIFICARCORONA);
			as.add(NOTIFICARCORONA_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			

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

	// Creamos método para devolver la referencia estatica
	public static OntologiaCiudadelasDos getInstance() {
		return instance;
	}

}
