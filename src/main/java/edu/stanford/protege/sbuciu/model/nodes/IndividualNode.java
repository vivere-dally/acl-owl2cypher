package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.ArrayList;
import java.util.List;

public class IndividualNode extends IRINode {
    public final OWLNamedIndividual individual;
    public final List<OWLAnnotation> annotations = new ArrayList<>();

    public IndividualNode(IRI iri, OWLNamedIndividual individual) {
        super(iri);
        this.individual = individual;
    }
}
