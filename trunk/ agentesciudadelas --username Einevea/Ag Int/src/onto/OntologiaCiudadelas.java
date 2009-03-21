package onto;

import acciones.*;
import predicados.*;
import conceptos.*;
import conceptos.Error;
import jade.content.onto.*;
import jade.content.schema.*;

public class OntologiaCiudadelas extends Ontology {

	// Nombre de la Ontologia
    public static final String ONTOLOGY_NAME = "OntologiaCiudadelas";
    
    // Nombre de los conceptos y sus atributos
    public static final String tablero = "Tablero";
    private static final String  ronda = "Ronda";
    private static final String  jugador1 = "Jugador1";
    private static final String  jugador2 = "Jugador2";
    private static final String  jugador3 = "Jugador3";
    private static final String  jugador4 = "Jugador4";
    private static final String  corona = "Corona";
    
    
    public static final String personaje = "Personaje";
    public static final String personaje1 = "Personaje";
    public static final String personaje2 = "Personaje";
    private static final String  nombre = "Nombre";
    private static final String  turno = "Turno";
    
    public static final String jugador = "Jugador";
    private static final String  puntos = "Puntos";
    private static final String  monedas = "Monedas";
    
    public static final String error = "Error";
    private static final String  id = "Id";
    private static final String  mensaje = "Mensaje";
    
    public static final String distrito = "Distrito";
    private static final String  coste = "Coste";
    private static final String  color = "Color";
    
    public static final String ciudad = "Ciudad";
    private static final String  distritos = "Distritos"; 
    private static final String  distrito1 = "Distrito1";
    private static final String  distrito2 = "Distrito2";
    private static final String  distrito3 = "Distrito3";
    private static final String  distrito4 = "Distrito4";
    private static final String  distrito5 = "Distrito5";
    private static final String  distrito6 = "Distrito6";
    private static final String  distrito7 = "Distrito7";
    private static final String  distrito8 = "Distrito8";
    
    
    //Nombre de los predicados y sus atributos
    private static final String  poseeCiudad = "PoseeCiudad";
    
    private static final String  poseeMano = "PoseeMano";
    
    private static final String  tieneRol = "TieneRol";
    
    //Nombre de las acciones y sus atributos
    private static final String  cambiarMano = "CambiarMano";
    private static final String  cobrarDistritos = "CobrarDistritos";
    private static final String  cantidad = "Cantidad";
    private static final String  cobrarMercader = "CobrarMercader";
    private static final String  darDistritos = "DarDistritos";
    private static final String  darMano = "DarMano";
    private static final String  darMonedas = "DarMonedas";
    private static final String  darTurno = "DarTurno";
    private static final String  muerto = "Muerto";
    private static final String  robado = "Robado";
    private static final String  decirEstado = "DecirEstado";
    private static final String  descartarDistrito = "DescartarDistrito";
    private static final String  descartarDistritoPersonaje = "DescartarDistritoPersonaje";
    private static final String  descartarPrivadoPersonaje = "DescartarPrivadoPersonaje";
    private static final String  descartarPublicoPersonajes = "DescartarPublicoPersonajes";
    private static final String  destruirDistrito = "DestruirDistrito";
    private static final String  pago = "Pago";
    private static final String  devolverTurno = "DevolverTurno";
    private static final String  elegirPersonaje = "ElegirPersonaje";
    private static final String  matar = "Matar";
    private static final String  notificarAsesinado = "NotificarAsesinado";
    private static final String  notificarCorona = "NotificarCorona";
    private static final String  notificarError = "NotificarError";
    private static final String  notificarRobado = "NotificarRobado";
    private static final String  notificarTurno = "NotificarTurno";
    private static final String  obtenerDistritos = "ObtenerDistritos";
    private static final String  obtenerMano = "ObtenerMano";
    private static final String  obtenerMonedas = "ObtenerMonedas";
    private static final String  ofertarDistritos = "OfertarDistritos";
    private static final String  ofertarPersonajes = "OfertarPersonajes";
    private static final String  pedirCorona = "PedirCorona";
    private static final String  pedirDistritosArquitecto = "PedirDistritosArquitecto";
    private static final String  pedirEstado = "PedirEstado";
    private static final String  robar = "Robar";
    
    // Creamos referencia estatica a la propia onto
    private static OntologiaCiudadelas instance= new OntologiaCiudadelas();
    
    // Creamos método para devolver la referencia estatica
    public static OntologiaCiudadelas getInstance() {
        return instance;
    }
    
 // Constructor de la onto
    public OntologiaCiudadelas() {
    	// Invoca al constructor de clase jade.content.onto.Ontology con 
    	// la referencia estática como argumento
        super(ONTOLOGY_NAME, BasicOntology.getInstance());
        try {
        	
        	// añadimos un conceptschema por cada concepto
        	// relacionandolo con la clase que lo implementa
            add(new ConceptSchema(tablero), Tablero.class);
            add(new ConceptSchema(personaje), Personaje.class);
            add(new ConceptSchema(jugador), Jugador.class);
            add(new ConceptSchema(error), Error.class);
            add(new ConceptSchema(distrito), Distrito.class);
            add(new ConceptSchema(ciudad), Ciudad.class);
            
            // Igual para predicados
            add(new PredicateSchema (poseeCiudad), PoseeCiudad.class);
            add(new PredicateSchema (poseeMano), PoseeMano.class);
            add(new PredicateSchema (tieneRol), TieneRol.class);
            
            
            // Igual para acciones
            add(new AgentActionSchema (cambiarMano), CambiarMano.class);
            add(new AgentActionSchema (cobrarDistritos), CobrarDistritos.class);
            add(new AgentActionSchema (cobrarMercader), CobrarMercader.class);
            add(new AgentActionSchema (darDistritos), DarDistritos.class);
            add(new AgentActionSchema (darMano), DarMano.class);
            add(new AgentActionSchema (darMonedas), DarMonedas.class);
            add(new AgentActionSchema (darTurno), DarTurno.class);
            add(new AgentActionSchema (decirEstado), DecirEstado.class);
            add(new AgentActionSchema (descartarDistrito), DescartarDistrito.class);
            add(new AgentActionSchema (descartarPrivadoPersonaje), DescartarPrivadoPersonaje.class);
            add(new AgentActionSchema (descartarPublicoPersonajes), DescartarPublicoPersonajes.class);
            add(new AgentActionSchema (destruirDistrito), DestruirDistrito.class);
            add(new AgentActionSchema (devolverTurno), DevolverTurno.class);
            add(new AgentActionSchema (elegirPersonaje), ElegirPersonaje.class);
            add(new AgentActionSchema (matar), Matar.class);
            add(new AgentActionSchema (notificarAsesinado), NotificarAsesinado.class);
            add(new AgentActionSchema (notificarCorona), NotificarCorona.class);
            add(new AgentActionSchema (notificarError), NotificarError.class);
            add(new AgentActionSchema (notificarRobado), NotificarRobado.class);
            add(new AgentActionSchema (notificarTurno), NotificarTurno.class);
            add(new AgentActionSchema (obtenerDistritos), ObtenerDistritos.class);
            add(new AgentActionSchema (obtenerMano), ObtenerMano.class);
            add(new AgentActionSchema (obtenerMonedas), ObtenerMonedas.class);
            add(new AgentActionSchema (ofertarDistritos), OfertarDistritos.class);
            add(new AgentActionSchema (ofertarPersonajes), OfertarPersonajes.class);    
            add(new AgentActionSchema (pedirCorona), PedirCorona.class);
            add(new AgentActionSchema (pedirDistritosArquitecto), PedirDistritosArquitecto.class);
            add(new AgentActionSchema (pedirEstado), PedirEstado.class);
            add(new AgentActionSchema (robar), Robar.class);
            
            // Atributos de los conceptos
	        ConceptSchema cs= (ConceptSchema) getSchema(ciudad);
	        cs.add(distrito1, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(distrito2, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(distrito3, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(distrito4, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(distrito5, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(distrito6, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(distrito7, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(distrito8, (PrimitiveSchema) getSchema(BasicOntology.STRING));

	        cs= (ConceptSchema) getSchema(distrito);
	        cs.add(nombre, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(coste, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
	        cs.add(color, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(puntos, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
	        
	        
	        cs= (ConceptSchema) getSchema(error);
	        cs.add(id, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
	        cs.add(mensaje, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        
	        cs= (ConceptSchema) getSchema(jugador);
	        cs.add(nombre, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(puntos, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
	        cs.add(monedas, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
	        
	        cs= (ConceptSchema) getSchema(personaje);
	        cs.add(nombre, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(turno, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
	        cs.add(color, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        
	        cs= (ConceptSchema) getSchema(tablero);
	        cs.add(ronda, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
	        cs.add(jugador1, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(jugador2, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(jugador3, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(jugador4, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	        cs.add(corona, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	    	        
	        // Atributos de los predicados
	        PredicateSchema ps= (PredicateSchema) getSchema(poseeCiudad);
	        ps.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        ps= (PredicateSchema) getSchema(poseeMano);
	        ps.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        ps= (PredicateSchema) getSchema(tieneRol);
	        ps.add(jugador, (ConceptSchema) getSchema(jugador));
	        ps.add(personaje, (ConceptSchema) getSchema(personaje));
	        
	        
	        
	        // Atributos de los predicados
	        AgentActionSchema as= (AgentActionSchema) getSchema(cambiarMano);
	        as.add(jugador1, (ConceptSchema) getSchema(jugador));
	        as.add(jugador2, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(cobrarDistritos);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        as.add(cantidad, (ConceptSchema) getSchema(BasicOntology.INTEGER));
	        
	        as= (AgentActionSchema) getSchema(cobrarMercader);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(darDistritos);
	        as.add(distritos, (ConceptSchema) getSchema(distrito), 0, ObjectSchema.UNLIMITED);

	        as= (AgentActionSchema) getSchema(darMano);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(darMonedas);
	        as.add(monedas, (ConceptSchema) getSchema(BasicOntology.INTEGER));
	        
	        as= (AgentActionSchema) getSchema(darTurno);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        as.add(muerto, (ConceptSchema) getSchema(BasicOntology.BOOLEAN));
	        as.add(robado, (ConceptSchema) getSchema(BasicOntology.BOOLEAN));
	        
	        as= (AgentActionSchema) getSchema(decirEstado);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(descartarDistrito);
	        as.add(distrito1, (ConceptSchema) getSchema(distrito));
	        
	        as= (AgentActionSchema) getSchema(descartarPrivadoPersonaje);
	        as.add(personaje1, (ConceptSchema) getSchema(personaje));
	        
	        as= (AgentActionSchema) getSchema(descartarPublicoPersonajes);
	        as.add(personaje1, (ConceptSchema) getSchema(personaje));
	        as.add(personaje2, (ConceptSchema) getSchema(personaje));
	        
	        as= (AgentActionSchema) getSchema(destruirDistrito);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        as.add(distrito, (ConceptSchema) getSchema(distrito));
	        as.add(pago, (ConceptSchema) getSchema(BasicOntology.INTEGER));
	        
	        as= (AgentActionSchema) getSchema(devolverTurno);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(elegirPersonaje);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        as.add(personaje, (ConceptSchema) getSchema(personaje));
	        
	        as= (AgentActionSchema) getSchema(matar);
	        as.add(personaje, (ConceptSchema) getSchema(personaje));
	        
	        as= (AgentActionSchema) getSchema(notificarAsesinado);
	        as.add(personaje, (ConceptSchema) getSchema(personaje));
	        
	        as= (AgentActionSchema) getSchema(notificarCorona);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(notificarError);
	        as.add(error, (ConceptSchema) getSchema(error));
	        
	        as= (AgentActionSchema) getSchema(notificarRobado);
	        as.add(personaje, (ConceptSchema) getSchema(personaje));
	        
	        as= (AgentActionSchema) getSchema(notificarTurno);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        as.add(personaje, (ConceptSchema) getSchema(personaje));
	        
	        as= (AgentActionSchema) getSchema(obtenerDistritos);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(obtenerMano);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(obtenerMonedas);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(ofertarDistritos);
	        as.add(distrito1, (ConceptSchema) getSchema(distrito));
	        as.add(distrito2, (ConceptSchema) getSchema(distrito));
	        
	        as= (AgentActionSchema) getSchema(ofertarPersonajes);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(pedirCorona);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(pedirDistritosArquitecto);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(pedirEstado);
	        as.add(jugador, (ConceptSchema) getSchema(jugador));
	        
	        as= (AgentActionSchema) getSchema(robar);
	        as.add(personaje, (ConceptSchema) getSchema(personaje));
	        
	        
      }
        catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }
}
