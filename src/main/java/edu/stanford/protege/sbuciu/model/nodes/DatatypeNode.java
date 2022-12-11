package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDatatype;

import java.util.ArrayList;
import java.util.List;

public class DatatypeNode extends IRINode {
    public final OWLDatatype datatype;
    public final List<OWLAnnotation> annotations = new ArrayList<>();

    public DatatypeNode(IRI iri, OWLDatatype datatype) {
        super(iri);
        this.datatype = datatype;
    }
}
