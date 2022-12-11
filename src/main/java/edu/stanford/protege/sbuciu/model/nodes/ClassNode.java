package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.ArrayList;
import java.util.List;

public class ClassNode extends IRINode {
    public final OWLClass owlClass;
    public final List<OWLAnnotation> annotations = new ArrayList<>();

    public ClassNode(IRI iri, OWLClass owlClass) {
        super(iri);
        this.owlClass = owlClass;
    }
}
