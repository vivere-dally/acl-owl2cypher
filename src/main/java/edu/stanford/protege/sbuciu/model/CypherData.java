package edu.stanford.protege.sbuciu.model;

import edu.stanford.protege.sbuciu.model.nodes.*;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;

import java.util.*;

public class CypherData {
    private final Map<String, Map<String, String>> axioms;

    //    Ontology global annotation properties
    public final Map<IRI, AnnotationPropertyCypherNode> annotationProperties = new HashMap<>();

    //    Classes
    public final Map<IRI, ClassCypherNode> classes = new HashMap<>();

    //    Data types
    public final Map<IRI, DatatypeCypherNode> dataTypes = new HashMap<>();

    //    Individuals
    public final Map<IRI, IndividualCypherNode> individuals = new HashMap<>();

    //    Object Properties
    public final Map<IRI, ObjectPropertyCypherNode> objectProperties = new HashMap<>();

    //    Data Properties
    public final Map<IRI, DataPropertyCypherNode> dataProperties = new HashMap<>();

    public List<Map<IRI, ? extends CypherNode>> getMaps() {
        return Arrays.asList(classes, dataTypes, individuals, objectProperties, dataProperties);
    }

    public List<Collection<? extends CypherNode>> getValues() {
        return Arrays.asList(classes.values(), dataTypes.values(), individuals.values(), objectProperties.values(), dataProperties.values());
    }

    public void putAnnotation(IRI iri, OWLAnnotation annotation) {
        for (Map<IRI, ? extends CypherNode> map : getMaps()) {
            if (map.containsKey(iri)) {
                map.get(iri).annotations.add(annotation);
                break;
            }
        }
    }

    public CypherData() {
        axioms = new HashMap<>();
    }


    public void addAxiom(String subject, String property, String value) {
        if (!axioms.containsKey(subject)) {
            final Map<String, String> axiom = new HashMap<>();
            axiom.put(property, value);

            axioms.put(subject, axiom);
        }

        axioms.get(subject).put(property, value);
    }
}
