package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;

public class AnnotationPropertyCypherNode extends CypherNode {
    public final OWLAnnotationProperty annotationProperty;

    public AnnotationPropertyCypherNode(OWLAnnotationProperty annotationProperty) {
        this.annotationProperty = annotationProperty;
    }

    @Override
    public String getName() {
        return "AnnotationProperty";
    }

    @Override
    protected HasIRI getIRI() {
        return annotationProperty;
    }
}
