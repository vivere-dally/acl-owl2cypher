package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;

import java.util.ArrayList;
import java.util.List;

public class AnnotationPropertyNode extends IRINode {
    public final OWLAnnotationProperty annotationProperty;
    public final List<OWLAnnotation> annotations = new ArrayList<>();

    public AnnotationPropertyNode(IRI iri, OWLAnnotationProperty annotationProperty) {
        super(iri);
        this.annotationProperty = annotationProperty;
    }
}
