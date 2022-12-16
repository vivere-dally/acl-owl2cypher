package edu.stanford.protege.sbuciu.visitor;

import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationship;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationshipType;
import edu.stanford.protege.sbuciu.model.nodes.P;
import org.semanticweb.owlapi.model.*;

import java.util.Map;

public class CypherOWLObjectPropertyVisitor extends CypherDefaultVisitor {
    private final P from;

    public CypherOWLObjectPropertyVisitor(Map<CypherNode, P> m, OWLProperty property) throws Exception {
        super(m);
        from = m.get(CypherNode.of(property.getIRI()));
        if (from == null) {
            throw new Exception();
        }
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        if (axiom.isAnonymous() || axiom.getDomain().isAnonymous()) return;

        final P to = m.get(CypherNode.of(axiom.getDomain().asOWLClass().getIRI()));
        if (to == null) return;

        from.rel.add(new CypherRelationship("HAS_OUTGOING_OBJECT_PROPERTY", to.n));
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        if (axiom.isAnonymous() || axiom.getRange().isAnonymous()) return;

        final P to = m.get(CypherNode.of(axiom.getRange().asOWLClass().getIRI()));
        if (to == null) return;

        from.rel.add(new CypherRelationship("HAS_INGOING_OBJECT_PROPERTY", to.n));
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        if (axiom.isAnonymous() || axiom.getFirstProperty().isAnonymous() || axiom.getSecondProperty().isAnonymous())
            return;

        final P a = m.get(CypherNode.of(axiom.getFirstProperty().asOWLObjectProperty().getIRI()));
        if (a == null) return;

        final P b = m.get(CypherNode.of(axiom.getSecondProperty().asOWLObjectProperty().getIRI()));
        if (b == null) return;

        a.rel.add(new CypherRelationship("HAS_INVERSE_OBJECT_PROPERTY", b.n, CypherRelationshipType.BETWEEN));
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        if (axiom.isAnonymous() || axiom.getSubProperty().isAnonymous() || axiom.getSuperProperty().isAnonymous())
            return;

        final P a = m.get(CypherNode.of(axiom.getSubProperty().asOWLObjectProperty().getIRI()));
        if (a == null) return;

        final P b = m.get(CypherNode.of(axiom.getSuperProperty().asOWLObjectProperty().getIRI()));
        if (b == null) return;

        a.rel.add(new CypherRelationship("HAS_SUPER_OBJECT_PROPERTY", b.n));
        b.rel.add(new CypherRelationship("HAS_SUB_OBJECT_PROPERTY", a.n));
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        if (axiom.isAnonymous()) return;

        for (OWLObjectPropertyExpression e : axiom.getProperties()) {
            if (e.isAnonymous() || e.asOWLObjectProperty().getIRI().equals(from.n.iri)) continue;

            final P to = m.get(CypherNode.of(e.asOWLObjectProperty().getIRI()));
            if (to == null) return;

            from.rel.add(new CypherRelationship("HAS_EQUIVALENT_OBJECT_PROPERTY", to.n));
        }
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        from.n.properties.put("isAsymmetricObjectProperty", axiom.getProperty().asOWLObjectProperty().getIRI());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        from.n.properties.put("isSymmetricObjectProperty", axiom.getProperty().asOWLObjectProperty().getIRI());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        from.n.properties.put("isReflexiveObjectProperty", axiom.getProperty().asOWLObjectProperty().getIRI());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        from.n.properties.put("isTransitiveObjectProperty", axiom.getProperty().asOWLObjectProperty().getIRI());
    }
}
