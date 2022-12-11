package edu.stanford.protege.sbuciu.visitor;

import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class CypherDefaultOWLAxiomVisitor implements OWLAxiomVisitor {
    private static final Logger log = LoggerFactory.getLogger(CypherDefaultOWLAxiomVisitor.class);

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        log.info(axiom.toString());
    }


    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        log.info(axiom.toString());
    }

    @Override
    public void visit(@Nonnull SWRLRule axiom) {
        log.info(axiom.toString());
    }
}
