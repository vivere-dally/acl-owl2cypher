package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class IndividualCypherNode extends CypherNode {
    public final OWLNamedIndividual individual;

    public IndividualCypherNode(OWLNamedIndividual individual) {
        this.individual = individual;
    }

    @Override
    public String getName() {
        return "Individual";
    }

    @Override
    protected HasIRI getIRI() {
        return individual;
    }
}
