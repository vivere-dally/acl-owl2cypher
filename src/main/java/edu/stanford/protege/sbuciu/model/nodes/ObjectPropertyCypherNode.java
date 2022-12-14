package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class ObjectPropertyCypherNode extends CypherNode {
    public final OWLObjectProperty property;

    public ObjectPropertyCypherNode(OWLObjectProperty property) {
        this.property = property;
    }

    @Override
    public String getName() {
        return "ObjectProperty";
    }

    @Override
    protected HasIRI getIRI() {
        return property;
    }
}
