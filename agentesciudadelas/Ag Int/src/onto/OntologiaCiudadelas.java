package onto;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;
import acciones.CambiarMano;
import acciones.CartasJugadores;
import acciones.CobrarDistritos;
import acciones.CobrarDistritosCondotierro;
import acciones.CobrarDistritosMercader;
import acciones.CobrarDistritosObispo;
import acciones.CobrarDistritosRey;
import acciones.CobrarPorDistritos;
import acciones.CobrarPorMercader;
import acciones.DarDistritos;
import acciones.DarMonedas;
import acciones.DarTurno;
import acciones.DecirEstado;
import acciones.DestruirDistrito;
import acciones.ElegirPersonaje;
import acciones.InfoPartida;
import acciones.Matar;
import acciones.Monedas;
import acciones.NotificarAsesinado;
import acciones.NotificarCorona;
import acciones.NotificarDescartados;
import acciones.NotificarFinTurnoJugador;
import acciones.NotificarRobado;
import acciones.ObtenerDistritos;
import acciones.ObtenerMonedas;
import acciones.OfertarPersonajes;
import acciones.PagarDistrito;
import acciones.PedirConstruirDistrito;
import acciones.PedirDistritoJugadores;
import acciones.PedirDistritosArquitecto;
import acciones.Robar;
import acciones.SustituirMano;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class OntologiaCiudadelas extends Ontology {

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
	public static final String CAMBIARMANO_JUGADOR = "jugador";
	
	public static final String OBTENER_MONEDAS = "ObtenerMonedas";
	public static final String OBTENER_MONEDAS_JUGADOR = "jugador";
	
	public static final String OBTENER_DISTRITOS = "ObtenerDistritos";
	public static final String OBTENER_DISTRITOS_JUGADOR = "jugador";
	
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
	
	public static final String DARTURNO = "DarTurno";
	public static final String DARTURNO_JUGADOR = "jugador";
	public static final String DARTURNO_MUERTO = "muerto";
	public static final String DARTURNO_ROBADO = "robado";
	public static final String DARTURNO_PERSONAJE = "personaje";
	public static final String DARTURNO_HAYMUERTO = "haymuerto";
	public static final String DARTURNO_PERSONAJEROBADO = "personajerobado";
	public static final String DARTURNO_HAYROBADO = "hayrobado";
	
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

	public static final String  NOTIFICARDESCARTADOS = "NotificarDescartados";
	public static final String  NOTIFICARDESCARTADOS_DESTAPADOS = "destapados";
	
	public static final String  PEDIRDISTRITOSARQUITECTO = "PedirDistritosArquitecto";
	public static final String  PEDIRDISTRITOSARQUITECTO_JUGADOR = "jugador";
	
	public static final String  NOTIFICARROBADO = "NotificarRobado";
	public static final String  NOTIFICARROBADO_PERSONAJE = "personaje";
	
	public static final String  ROBAR = "Robar";
	public static final String  ROBAR_PERSONAJE = "personaje";
	
	public static final String  MATAR = "Matar";
	public static final String  MATAR_PERSONAJE = "personaje";
	
	public static final String  NOTIFICARASESINADO = "NotificarAsesinado";
	public static final String  NOTIFICARASESINADO_PERSONAJE = "personaje";
	
	public static final String  DECIRESTADO = "DecirEstado";
	public static final String  DECIRESTADO_JUGADOR = "jugador";
	
	public static final String PEDIRDISTRITOJUGADORES = "PedirDistritoJugadores";
	public static final String PEDIRDISTRITOJUGADORES_JUGADOR1 = "jugador1";
	public static final String PEDIRDISTRITOJUGADORES_DISTRITOS1 = "distritos1";
	public static final String PEDIRDISTRITOJUGADORES_PERSONAJE1 = "personaje1";
	public static final String PEDIRDISTRITOJUGADORES_JUGADOR2 = "jugador2";
	public static final String PEDIRDISTRITOJUGADORES_DISTRITOS2 = "distritos2";
	public static final String PEDIRDISTRITOJUGADORES_PERSONAJE2 = "personaje2";
	public static final String PEDIRDISTRITOJUGADORES_JUGADOR3 = "jugador3";
	public static final String PEDIRDISTRITOJUGADORES_DISTRITOS3 = "distritos3";
	public static final String PEDIRDISTRITOJUGADORES_PERSONAJE3 = "personaje3";
	
	public static final String MONEDAS = "monedas";
	public static final String MONEDAS_DINERO = "dinero";
	
	public static final String CARTASJUGADORES = "CartasJugadores";
	public static final String CARTASJUGADORES_JUGADOR1 = "jugador1";
	public static final String CARTASJUGADORES_JUGADOR2 = "jugador2";
	public static final String CARTASJUGADORES_JUGADOR3 = "jugador3";
	
	public static final String INFOPARTIDA = "InfoPartida";
	public static final String INFOPARTIDA_JUGADOR1 = "jugador1";
	public static final String INFOPARTIDA_JUGADOR2 = "jugador2";
	public static final String INFOPARTIDA_JUGADOR3 = "jugador3";
	public static final String INFOPARTIDA_JUGADOR4 = "jugador4";
	public static final String INFOPARTIDA_PERSONAJE1 = "personaje1";
	public static final String INFOPARTIDA_PERSONAJE2 = "personaje2";
	public static final String INFOPARTIDA_PERSONAJE3 = "personaje3";
	public static final String INFOPARTIDA_PERSONAJE4 = "personaje4";
	public static final String INFOPARTIDA_DISTRITOSJ1 = "distritosJ1";
	public static final String INFOPARTIDA_DISTRITOSJ2 = "distritosJ2";
	public static final String INFOPARTIDA_DISTRITOSJ3 = "distritosJ3";
	public static final String INFOPARTIDA_DISTRITOSJ4 = "distritosJ4";
	public static final String INFOPARTIDA_JUGOP2 = "jugoP1";
	public static final String INFOPARTIDA_JUGOP3 = "jugoP2";
	public static final String INFOPARTIDA_JUGOP4 = "jugoP3";
	public static final String INFOPARTIDA_JUGOP1 = "jugoP4";
	
	
	// Protected constructor is sufficient to suppress unauthorized calls to the
	// constructor
	private OntologiaCiudadelas() {
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
			add(new AgentActionSchema(DARTURNO), DarTurno.class);
			add(new AgentActionSchema(NOTIFICARFINTURNOJUGADOR), NotificarFinTurnoJugador.class);
			add(new AgentActionSchema(PAGARDISTRITO), PagarDistrito.class);
			add(new AgentActionSchema(PEDIRCONSTRUIRDISTRITO), PedirConstruirDistrito.class);
			add(new AgentActionSchema(SUSTITUIRMANO), SustituirMano.class);
			add(new AgentActionSchema(OFERTARPERSONAJES), OfertarPersonajes.class);
			add(new AgentActionSchema(OBTENER_MONEDAS), ObtenerMonedas.class);
			add(new AgentActionSchema(OBTENER_DISTRITOS), ObtenerDistritos.class);
			add(new AgentActionSchema(NOTIFICARDESCARTADOS), NotificarDescartados.class);
			add(new AgentActionSchema(PEDIRDISTRITOSARQUITECTO), PedirDistritosArquitecto.class);
			add(new AgentActionSchema(NOTIFICARROBADO), NotificarRobado.class);
			add(new AgentActionSchema(ROBAR), Robar.class);
			add(new AgentActionSchema(MATAR), Matar.class);
			add(new AgentActionSchema(NOTIFICARASESINADO), NotificarAsesinado.class);
			add(new AgentActionSchema(DECIRESTADO), DecirEstado.class);
			add(new AgentActionSchema(CARTASJUGADORES), CartasJugadores.class);
			add(new AgentActionSchema(INFOPARTIDA), InfoPartida.class);
			add(new AgentActionSchema(PEDIRDISTRITOJUGADORES), PedirDistritoJugadores.class);
			add(new AgentActionSchema(MONEDAS), Monedas.class);
			

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
			
			as = (AgentActionSchema) getSchema(OBTENER_MONEDAS);
			as.add(OBTENER_MONEDAS_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(OBTENER_DISTRITOS);
			as.add(OBTENER_DISTRITOS_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(NOTIFICARCORONA);
			as.add(NOTIFICARCORONA_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(DARTURNO);
			as.add(DARTURNO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			as.add(DARTURNO_MUERTO, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			as.add(DARTURNO_ROBADO, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			as.add(DARTURNO_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			as.add(DARTURNO_HAYMUERTO, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			as.add(DARTURNO_PERSONAJEROBADO, (ConceptSchema) getSchema(PERSONAJE));
			as.add(DARTURNO_HAYROBADO, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			
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
			as.add(CAMBIARMANO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			

			
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
			as.add(OFERTARPERSONAJES_DISPONIBLES, (ConceptSchema) getSchema(PERSONAJE), 0, ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(NOTIFICARDESCARTADOS);
			as.add(NOTIFICARDESCARTADOS_DESTAPADOS, (ConceptSchema) getSchema(PERSONAJE), 0, ObjectSchema.UNLIMITED);
			
			as = (AgentActionSchema) getSchema(PEDIRDISTRITOSARQUITECTO);
			as.add(PEDIRDISTRITOSARQUITECTO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(NOTIFICARROBADO);
			as.add(NOTIFICARROBADO_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			
			as = (AgentActionSchema) getSchema(ROBAR);
			as.add(ROBAR_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			
			as = (AgentActionSchema) getSchema(MATAR);
			as.add(MATAR_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			
			as = (AgentActionSchema) getSchema(NOTIFICARASESINADO);
			as.add(NOTIFICARASESINADO_PERSONAJE, (ConceptSchema) getSchema(PERSONAJE));
			
			as = (AgentActionSchema) getSchema(DECIRESTADO);
			as.add(DECIRESTADO_JUGADOR, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(PEDIRDISTRITOJUGADORES);
			as.add(PEDIRDISTRITOJUGADORES_JUGADOR1, (ConceptSchema) getSchema(JUGADOR));
			as.add(PEDIRDISTRITOJUGADORES_DISTRITOS1, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			as.add(PEDIRDISTRITOJUGADORES_PERSONAJE1, (ConceptSchema) getSchema(PERSONAJE));
			as.add(PEDIRDISTRITOJUGADORES_JUGADOR2, (ConceptSchema) getSchema(JUGADOR));
			as.add(PEDIRDISTRITOJUGADORES_DISTRITOS2, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			as.add(PEDIRDISTRITOJUGADORES_PERSONAJE2, (ConceptSchema) getSchema(PERSONAJE));
			as.add(PEDIRDISTRITOJUGADORES_JUGADOR3, (ConceptSchema) getSchema(JUGADOR));
			as.add(PEDIRDISTRITOJUGADORES_DISTRITOS3, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			as.add(PEDIRDISTRITOJUGADORES_PERSONAJE3, (ConceptSchema) getSchema(PERSONAJE));
			
			as = (AgentActionSchema) getSchema(MONEDAS);
			as.add(MONEDAS_DINERO, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			
			as = (AgentActionSchema) getSchema(CARTASJUGADORES);
			as.add(CARTASJUGADORES_JUGADOR1, (ConceptSchema) getSchema(JUGADOR));
			as.add(CARTASJUGADORES_JUGADOR2, (ConceptSchema) getSchema(JUGADOR));
			as.add(CARTASJUGADORES_JUGADOR3, (ConceptSchema) getSchema(JUGADOR));
			
			as = (AgentActionSchema) getSchema(INFOPARTIDA);
			as.add(INFOPARTIDA_JUGADOR1, (ConceptSchema) getSchema(JUGADOR));
			as.add(INFOPARTIDA_JUGADOR2, (ConceptSchema) getSchema(JUGADOR));
			as.add(INFOPARTIDA_JUGADOR3, (ConceptSchema) getSchema(JUGADOR));
			as.add(INFOPARTIDA_JUGADOR4, (ConceptSchema) getSchema(JUGADOR));
			as.add(INFOPARTIDA_PERSONAJE1, (ConceptSchema) getSchema(PERSONAJE));
			as.add(INFOPARTIDA_PERSONAJE2, (ConceptSchema) getSchema(PERSONAJE));
			as.add(INFOPARTIDA_PERSONAJE3, (ConceptSchema) getSchema(PERSONAJE));
			as.add(INFOPARTIDA_PERSONAJE4, (ConceptSchema) getSchema(PERSONAJE));
			as.add(INFOPARTIDA_DISTRITOSJ1, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			as.add(INFOPARTIDA_DISTRITOSJ2, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			as.add(INFOPARTIDA_DISTRITOSJ3, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			as.add(INFOPARTIDA_DISTRITOSJ4, (ConceptSchema) getSchema(DISTRITO), 0, ObjectSchema.UNLIMITED);
			as.add(INFOPARTIDA_JUGOP1, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			as.add(INFOPARTIDA_JUGOP2, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			as.add(INFOPARTIDA_JUGOP3, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			as.add(INFOPARTIDA_JUGOP4, (PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			
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
	private static OntologiaCiudadelas instance = new OntologiaCiudadelas();

	// Creamos m�todo para devolver la referencia estatica
	public static OntologiaCiudadelas getInstance() {
		return instance;
	}

}
