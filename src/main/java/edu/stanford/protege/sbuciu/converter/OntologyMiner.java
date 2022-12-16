package edu.stanford.protege.sbuciu.converter;

import edu.stanford.protege.sbuciu.model.nodes.*;
import edu.stanford.protege.sbuciu.visitor.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class OntologyMiner {
    private static final Logger log = LoggerFactory.getLogger(OntologyMiner.class);

    private final OWLOntology ontology;
    private final OWLOntologyManager manager;
    private final Map<CypherNode, P> m = new HashMap<>();

    public OntologyMiner(OWLOntology ontology, OWLOntologyManager manager) {
        this.ontology = ontology;
        this.manager = manager;
    }

    public Map<CypherNode, P> mine() {
        mineEntities();
        mineAssertionAxioms();
        mineClassAxioms();
        mineObjectProperties();
        mineDataProperties();
        mineIndividuals();
        mineGenericAxioms();
        return m;
    }

    private <T extends OWLNamedObject> void mineGeneric(Set<T> s, CypherNodeType type) {
        for (final T value : s) {
            if (!value.isAnonymous()) {
                CypherNode n = new CypherNode(value.getIRI(), type);
                m.put(n, new P(n));
            }
        }
    }

    private void mineEntities() {
        mineGeneric(ontology.getAnnotationPropertiesInSignature(Imports.INCLUDED), CypherNodeType.ANNOTATION_PROPERTY);
        mineGeneric(ontology.getClassesInSignature(Imports.INCLUDED), CypherNodeType.CLASS);
        mineGeneric(ontology.getDatatypesInSignature(Imports.INCLUDED), CypherNodeType.DATA_TYPE);
        mineGeneric(ontology.getIndividualsInSignature(Imports.INCLUDED), CypherNodeType.INDIVIDUAL);
        mineGeneric(ontology.getObjectPropertiesInSignature(Imports.INCLUDED), CypherNodeType.OBJECT_PROPERTY);
        mineGeneric(ontology.getDataPropertiesInSignature(Imports.INCLUDED), CypherNodeType.DATA_PROPERTY);
    }

    private void mineAssertionAxioms() {
        for (final OWLAxiom axiom : ontology.getAxioms(Imports.INCLUDED)) {
            if (!(axiom instanceof OWLAnnotationAssertionAxiom)) {
                continue;
            }

            final OWLAnnotationAssertionAxiom assertionAxiom = (OWLAnnotationAssertionAxiom) axiom;
            if (!assertionAxiom.getProperty().getIRI().getRemainder().isPresent()) {
                continue;
            }

            IRI iri = ((IRI) assertionAxiom.getSubject());
            final P from = m.get(CypherNode.of(iri));
            if (from == null) {
                continue;
            }

            final P to = m.get(CypherNode.of(assertionAxiom.getProperty().getIRI()));
            if (to == null) {
                continue;
            }

            String relName = "HAS_" + assertionAxiom.getProperty().getIRI().getRemainder().get().toUpperCase();
            if (assertionAxiom.getValue().asLiteral().isPresent()) {
                final OWLLiteral lit = assertionAxiom.getValue().asLiteral().get();
                final CypherRelationship rel = new CypherRelationship(relName, to.n);
                rel.properties.put("value", lit.getLiteral());
                rel.properties.put("lang", lit.getLang());
                rel.properties.put("dataType", lit.getDatatype().getIRI().toString());
                from.rel.add(rel);
            }
        }
    }

    private void mineClassAxioms() {
        for (final OWLClass clazz : ontology.getClassesInSignature(Imports.INCLUDED)) {
            for (final OWLClassAxiom axiom : ontology.getAxioms(clazz, Imports.INCLUDED)) {
                try {
                    CypherOWLClassVisitor visitor = new CypherOWLClassVisitor(m);
                    axiom.accept(visitor);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void mineObjectProperties() {
        for (final OWLObjectProperty property : ontology.getObjectPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLObjectPropertyAxiom axiom : ontology.getAxioms(property, Imports.INCLUDED)) {
                try {
                    CypherOWLObjectPropertyVisitor visitor = new CypherOWLObjectPropertyVisitor(m, property);
                    axiom.accept(visitor);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void mineDataProperties() {
        for (final OWLDataProperty property : ontology.getDataPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLDataPropertyAxiom axiom : ontology.getAxioms(property, Imports.INCLUDED)) {
                try {
                    CypherOWLDataPropertyVisitor visitor = new CypherOWLDataPropertyVisitor(m, property);
                    axiom.accept(visitor);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void mineIndividuals() {
        for (final OWLClass clazz : ontology.getClassesInSignature(Imports.INCLUDED)) {
            for (final OWLOntology owlOntology : manager.getOntologies()) {
                for (final OWLIndividual individual : EntitySearcher.getIndividuals(clazz, owlOntology)) {
                    try {
                        final CypherOWLIndividualVisitor visitor = new CypherOWLIndividualVisitor(m, clazz.getIRI());
                        individual.accept(visitor);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    private void mineGenericAxioms() {
        for (final OWLClassAxiom axiom : ontology.getGeneralClassAxioms()) {
            try {
                final CypherOWLGenericClassVisitor visitor = new CypherOWLGenericClassVisitor(m);
                axiom.accept(visitor);
            } catch (Exception ignored) {
            }
        }
    }
}
