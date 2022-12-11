package edu.stanford.protege.sbuciu.model;

import edu.stanford.protege.sbuciu.model.nodes.AnnotationPropertyNode;
import edu.stanford.protege.sbuciu.model.nodes.ClassNode;
import edu.stanford.protege.sbuciu.model.nodes.DatatypeNode;
import edu.stanford.protege.sbuciu.model.nodes.IndividualNode;
import org.semanticweb.owlapi.model.IRI;

import java.util.HashMap;
import java.util.Map;

public class CypherData {
    private final Map<String, Map<String, String>> axioms;

    //    Ontology global annotation properties
    public final Map<IRI, AnnotationPropertyNode> annotationProperties = new HashMap<>();

    //    Classes
    public final Map<IRI, ClassNode> classes = new HashMap<>();

    //    Data types
    public final Map<IRI, DatatypeNode> dataTypes = new HashMap<>();

    //    Individuals
    public final Map<IRI, IndividualNode> individuals = new HashMap<>();

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
