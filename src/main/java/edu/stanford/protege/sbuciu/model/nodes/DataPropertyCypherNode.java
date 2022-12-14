package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.OWLDataProperty;

public class DataPropertyCypherNode extends CypherNode {
    public final OWLDataProperty property;

    public DataPropertyCypherNode(OWLDataProperty property) {
        this.property = property;
    }

    @Override
    public String getName() {
        return "DataProperty";
    }

    @Override
    protected HasIRI getIRI() {
        return property;
    }
}
