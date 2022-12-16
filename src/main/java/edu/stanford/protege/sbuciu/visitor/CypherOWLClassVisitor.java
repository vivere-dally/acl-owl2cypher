package edu.stanford.protege.sbuciu.visitor;

import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationship;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationshipType;
import edu.stanford.protege.sbuciu.model.nodes.P;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Map;

public class CypherOWLClassVisitor extends CypherDefaultVisitor {
    private static final Logger log = LoggerFactory.getLogger(CypherOWLClassVisitor.class);

    public CypherOWLClassVisitor(Map<CypherNode, P> m) {
        super(m);
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        if (axiom.isAnonymous() || axiom.getSubClass().isAnonymous() || axiom.getSuperClass().isAnonymous()) {
            return;
        }

        P subClassP = m.get(CypherNode.of(axiom.getSubClass().asOWLClass().getIRI()));
        if (subClassP == null) {
            return;
        }

        P superClassP = m.get(CypherNode.of(axiom.getSuperClass().asOWLClass().getIRI()));
        if (superClassP == null) {
            return;
        }

        subClassP.rel.add(new CypherRelationship("SUB_CLASS_OF", superClassP.n));
        superClassP.rel.add(new CypherRelationship("SUPER_CLASS_OF", subClassP.n));
    }


    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
//        if (axiom.isAnonymous()) return;
//
//        for (final OWLDisjointClassesAxiom paxiom : axiom.asPairwiseAxioms()) {
//            P p1 = m.get(CypherNode.of(paxiom.getClassExpressionsAsList().get(0).asOWLClass().getIRI()));
//            if (p1 == null) continue;
//
//            P p2 = m.get(CypherNode.of(paxiom.getClassExpressionsAsList().get(1).asOWLClass().getIRI()));
//            if (p2 == null) continue;
//
//            p1.rel.add(new CypherRelationship("DISJOINT_WITH", p2.n, CypherRelationshipType.BETWEEN));
//        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        if (axiom.isAnonymous()) return;

        P from = m.get(CypherNode.of(axiom.getOWLClass().getIRI()));
        if (from == null) return;

        for (final OWLClassExpression ex : axiom.getClassExpressions()) {
            P to = m.get(CypherNode.of(ex.asOWLClass().getIRI()));
            if (to == null) continue;

            from.rel.add(new CypherRelationship("DISJOINT_UNION_WITH", to.n));
        }
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
//        if (axiom.isAnonymous()) {
//            return;
//        }
//
//        for (OWLEquivalentClassesAxiom eq : axiom.asPairwiseAxioms()) {
//            if (eq.getClassExpressionsAsList().size() > 2) {
//                log.info(eq.toString());
//                continue;
//            }
//
//            P from = m.get(CypherNode.of(((OWLClass) eq.getClassExpressionsAsList().get(0)).getIRI()));
//            if (from == null) continue;
//
//            OWLObjectSomeValuesFrom values = (OWLObjectSomeValuesFrom) eq.getClassExpressionsAsList().get(1);
//
//            IRI propIRI = (IRI) values.getProperty();
//            if (!propIRI.getRemainder().isPresent()) continue;
//
//            P prop = m.get(CypherNode.of(propIRI));
//            if (prop == null) continue;
//
//            P to = m.get(CypherNode.of((IRI) values.getFiller()));
//            if (to == null) continue;
//
//            final CypherRelationship rel = new CypherRelationship(propIRI.getRemainder().get(), to.n);
//            rel.properties.put("objectProperty", propIRI.toString());
//            from.rel.add(rel);
//        }
//
//        log.info(axiom.toString());
    }
}
