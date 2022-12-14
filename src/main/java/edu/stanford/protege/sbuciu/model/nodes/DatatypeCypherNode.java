package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.OWLDatatype;

public class DatatypeCypherNode extends CypherNode {
    public final OWLDatatype datatype;

    public DatatypeCypherNode(OWLDatatype datatype) {
        this.datatype = datatype;
    }

    @Override
    public String getName() {
        return "DataType";
    }

    @Override
    protected HasIRI getIRI() {
        return datatype;
    }
}
