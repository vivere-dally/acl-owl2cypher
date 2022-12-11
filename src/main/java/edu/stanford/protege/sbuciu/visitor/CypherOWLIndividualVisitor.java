package edu.stanford.protege.sbuciu.visitor;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class CypherOWLIndividualVisitor implements OWLIndividualVisitor {
    private static final Logger log = LoggerFactory.getLogger(CypherOWLIndividualVisitor.class);

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        log.info(individual.toString());
    }

    @Override
    public void visit(@Nonnull OWLAnonymousIndividual individual) {
        log.info(individual.toString());
    }
}
