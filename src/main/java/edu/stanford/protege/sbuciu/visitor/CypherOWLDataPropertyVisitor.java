package edu.stanford.protege.sbuciu.visitor;

import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationship;
import edu.stanford.protege.sbuciu.model.nodes.P;
import org.semanticweb.owlapi.model.*;

import java.util.Map;

public class CypherOWLDataPropertyVisitor extends CypherDefaultVisitor {
    private final P from;

    public CypherOWLDataPropertyVisitor(Map<CypherNode, P> m, OWLProperty property) throws Exception {
        super(m);
        from = m.get(CypherNode.of(property.getIRI()));
        if (from == null) {
            throw new Exception();
        }
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        if (axiom.isAnonymous()) return;

        for (OWLDataPropertyExpression e : axiom.getProperties()) {
            if (e.isAnonymous() || e.asOWLDataProperty().getIRI().equals(from.n.iri)) continue;

            final P to = m.get(CypherNode.of(e.asOWLDataProperty().getIRI()));
            if (to == null) return;

            from.rel.add(new CypherRelationship("HAS_EQUIVALENT_DATA_PROPERTY", to.n));
        }
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        from.n.properties.put("isFunctionalDataProperty", axiom.getProperty().asOWLDataProperty().getIRI());
    }
}
