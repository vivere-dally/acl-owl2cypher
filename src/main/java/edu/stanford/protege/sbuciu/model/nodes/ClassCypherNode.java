package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.OWLClass;

public class ClassCypherNode extends CypherNode {
    public final OWLClass owlClass;

    public ClassCypherNode(OWLClass owlClass) {
        this.owlClass = owlClass;
    }

    @Override
    public String getName() {
        return "Class";
    }

    @Override
    protected HasIRI getIRI() {
        return owlClass;
    }
}
