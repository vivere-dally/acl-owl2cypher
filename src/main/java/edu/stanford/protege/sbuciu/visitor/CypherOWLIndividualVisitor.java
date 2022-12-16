package edu.stanford.protege.sbuciu.visitor;

import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationship;
import edu.stanford.protege.sbuciu.model.nodes.P;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import javax.annotation.Nonnull;
import java.util.Map;

public class CypherOWLIndividualVisitor implements OWLIndividualVisitor {
    protected final Map<CypherNode, P> m;
    private final P from;

    public CypherOWLIndividualVisitor(Map<CypherNode, P> m, IRI from) throws Exception {
        this.m = m;
        this.from = m.get(CypherNode.of(from));
        if (this.from == null) {
            throw new Exception();
        }
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual i) {
        final P to = m.get(CypherNode.of(i.getIRI()));
        from.rel.add(new CypherRelationship("HAS_INDIVIDUAL", to.n));
    }

    @Override
    public void visit(@Nonnull OWLAnonymousIndividual owlAnonymousIndividual) {

    }
}
