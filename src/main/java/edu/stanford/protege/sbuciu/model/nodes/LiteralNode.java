package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

public class LiteralNode extends IRINode {

    public LiteralNode(IRI subject, IRI property, OWLLiteral literal) {
        super(subject);
    }
}
