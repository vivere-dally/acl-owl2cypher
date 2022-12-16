package edu.stanford.protege.sbuciu.visitor;

import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import edu.stanford.protege.sbuciu.model.nodes.P;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import java.util.Map;

public class CypherOWLGenericClassVisitor extends CypherDefaultVisitor {
    public CypherOWLGenericClassVisitor(Map<CypherNode, P> m) {
        super(m);
    }


    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        if (!axiom.isGCI() || axiom.getSuperClass().isAnonymous()) return;

        axiom.getSubClass().accept(new CypherOWLClassVisitor(m));
    }
}
