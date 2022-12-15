package edu.stanford.protege.sbuciu.converter;

import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import edu.stanford.protege.sbuciu.model.nodes.CypherNodeType;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationship;
import edu.stanford.protege.sbuciu.visitor.CypherDefaultOWLAxiomVisitor;
import edu.stanford.protege.sbuciu.visitor.CypherOWLIndividualVisitor;
import edu.stanford.protege.sbuciu.visitor.CypherOWLObjectWalkerVisitor;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class OntologyMiner {
    private static final Logger log = LoggerFactory.getLogger(OntologyMiner.class);

    private static final Set<String> NON_LITERAL_AXIOM_VALUE = new HashSet<>(Arrays.asList("targetElement",
            "subjectElement", "predicateElement", "providesNotation", "subjectDescription", "predicateDescription",
            "objectDescription", "providesView", "viewUsesNotation", "objectElement", "isTypeOf"));

    private final OWLOntology ontology;
    private final OWLOntologyManager manager;
    private final Map<CypherNode, List<CypherRelationship>> m = new HashMap<>();

    public OntologyMiner(OWLOntology ontology, OWLOntologyManager manager) {
        this.ontology = ontology;
        this.manager = manager;
    }

    public Map<CypherNode, List<CypherRelationship>> mine() {
        mineEntities();
        mineAnnotations();

//        for (final OWLAxiom axiom : ontology.getAxioms(Imports.INCLUDED)) {
//            log.info(axiom.toString());
//        }

//        preProcess();
//        process();
//        postProcess();
        log.info("done");
        return m;
    }

    private <T extends OWLNamedObject> void mineGeneric(Set<T> s, CypherNodeType type) {
        for (final T value : s) {
            if (!value.isAnonymous()) {
                m.put(new CypherNode(value.getIRI(), type), new ArrayList<>());
            }
        }
    }

    private void mineEntities() {
        log.info("mining entities...");
        mineGeneric(ontology.getAnnotationPropertiesInSignature(Imports.INCLUDED), CypherNodeType.ANNOTATION_PROPERTY);
        mineGeneric(ontology.getClassesInSignature(Imports.INCLUDED), CypherNodeType.CLASS);
        mineGeneric(ontology.getDatatypesInSignature(Imports.INCLUDED), CypherNodeType.DATA_TYPE);
        mineGeneric(ontology.getIndividualsInSignature(Imports.INCLUDED), CypherNodeType.INDIVIDUAL);
        mineGeneric(ontology.getObjectPropertiesInSignature(Imports.INCLUDED), CypherNodeType.OBJECT_PROPERTY);
        mineGeneric(ontology.getDataPropertiesInSignature(Imports.INCLUDED), CypherNodeType.DATA_PROPERTY);
//
//        for (final OWLAnnotationProperty value : ontology.getAnnotationPropertiesInSignature(Imports.INCLUDED)) {
//            if (value.isAnonymous()) {
//                continue;
//            }
//
//            m.put(new CypherNode(value.getIRI(), CypherNodeType.ANNOTATION_PROPERTY), new ArrayList<>());
//        }
//
//        for (final OWLClass value : ontology.getClassesInSignature(Imports.INCLUDED)) {
//            if (value.isAnonymous()) {
//                continue;
//            }
//
//            m.put(new CypherNode(value.getIRI(), CypherNodeType.CLASS), new ArrayList<>());
//        }
//
//        for (final OWLDatatype value : ontology.getDatatypesInSignature(Imports.INCLUDED)) {
//            if (value.isAnonymous()) {
//                continue;
//            }
//
//            m.put(new CypherNode(value.getIRI(), CypherNodeType.DATA_TYPE), new ArrayList<>());
//        }
//
//        for (final OWLNamedIndividual value : ontology.getIndividualsInSignature(Imports.INCLUDED)) {
//            if (value.isAnonymous()) {
//                continue;
//            }
//
//            m.put(new CypherNode(value.getIRI(), CypherNodeType.INDIVIDUAL), new ArrayList<>());
//        }
//
//        for (final OWLObjectProperty value : ontology.getObjectPropertiesInSignature(Imports.INCLUDED)) {
//            if (value.isAnonymous()) {
//                continue;
//            }
//
//            m.put(new CypherNode(value.getIRI(), CypherNodeType.OBJECT_PROPERTY), new ArrayList<>());
//        }
//
//        for (final OWLDataProperty value : ontology.getDataPropertiesInSignature(Imports.INCLUDED)) {
//            if (value.isAnonymous()) {
//                continue;
//            }
//
//            m.put(new CypherNode(value.getIRI(), CypherNodeType.DATA_PROPERTY), new ArrayList<>());
//        }
    }

    private void mineAnnotations() {
        log.info("mining annotations...");


        for (final OWLAxiom axiom : ontology.getAxioms(Imports.INCLUDED)) {
            if (!(axiom instanceof OWLAnnotationAssertionAxiom)) {
                continue;
            }

            final OWLAnnotationAssertionAxiom assertionAxiom = (OWLAnnotationAssertionAxiom) axiom;
            final IRI from = ((IRI) assertionAxiom.getSubject());
            final IRI to = assertionAxiom.getProperty().getIRI();
            if (assertionAxiom.getValue().asLiteral().isPresent()) {
                final OWLLiteral lit = assertionAxiom.getValue().asLiteral().get();
            }

            log.info("hm");
        }

//        for (final ClassCypherNode value : data.classes.values()) {
//            for (final OWLAxiom axiom : ontology.getAxioms(value.owlClass, Imports.INCLUDED)) {
//                if (!(axiom instanceof OWLAnnotationAssertionAxiom)) {
//                    continue;
//                }
//
//                value.annotations.add((OWLAnnotation) axiom);
//            }
//        }
    }

    private void preProcess() {
        for (final OWLAxiom axiom : ontology.getAxioms(Imports.INCLUDED)) {
            if (!(axiom instanceof OWLAnnotationAssertionAxiom)) {
                continue;
            }

            final OWLAnnotationAssertionAxiom assertionAxiom = (OWLAnnotationAssertionAxiom) axiom;
            String value;
            if (NON_LITERAL_AXIOM_VALUE.contains(assertionAxiom.getProperty().getIRI().getShortForm())) {
                value = assertionAxiom.getValue().toString();
            } else {
                if (!assertionAxiom.getValue().asLiteral().isPresent()) {
                    continue;
                }

                value = assertionAxiom.getValue().asLiteral().get().getLiteral();
            }

            final String subject = assertionAxiom.getSubject().toString();
            final String property = assertionAxiom.getProperty().getIRI().toString();

        }

        try {
            final OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
            final CypherOWLObjectWalkerVisitor visitor = new CypherOWLObjectWalkerVisitor();
            walker.walkStructure(visitor);

            log.info("Walk structure success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    private void process() {
        processAnnotationProperties();
        processClasses();
        processObjectProperties();
        processDataProperties();
        processIndividuals();
        processGenericAxioms();
    }

    private void postProcess() {

    }

    private void processAnnotationProperties() {
        for (final OWLAnnotationProperty property : ontology.getAnnotationPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLAnnotationAxiom axiom : ontology.getAxioms(property, Imports.INCLUDED)) {
                try {
                    final String subject = property.getIRI().toString();
                    final String prop = "TODO: some constant";

                    final OWLAnnotationPropertyRangeAxiom rangeAxiom = (OWLAnnotationPropertyRangeAxiom) axiom;
                    final String value = rangeAxiom.getRange().toString();

                    log.info("subject={} property={} value={}", subject, prop, value);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void processClasses() {
        for (final OWLClass clazz : ontology.getClassesInSignature(Imports.INCLUDED)) {
            for (final OWLClassAxiom axiom : ontology.getAxioms(clazz, Imports.INCLUDED)) {
                try {
//                    final CypherDefaultOWLAxiomVisitor visitor = new CypherDefaultOWLAxiomVisitor();
                } catch (Exception ignored) {
                }
            }
        }

        log.info("done processing classes");
    }

    private void processObjectProperties() {
        for (final OWLObjectProperty property : ontology.getObjectPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLObjectPropertyAxiom axiom : ontology.getAxioms(property, Imports.INCLUDED)) {
                try {
                    // TODO: define OWLObjectProperty visitor
                    final CypherDefaultOWLAxiomVisitor visitor = new CypherDefaultOWLAxiomVisitor();
                    axiom.accept(visitor);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void processDataProperties() {
        for (final OWLDataProperty property : ontology.getDataPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLDataPropertyAxiom axiom : ontology.getAxioms(property, Imports.INCLUDED)) {
                try {
                    // TODO: define OWLObjectProperty visitor
                    final CypherDefaultOWLAxiomVisitor visitor = new CypherDefaultOWLAxiomVisitor();
                    axiom.accept(visitor);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void processIndividuals() {
        for (final OWLClass clazz : ontology.getClassesInSignature(Imports.INCLUDED)) {
            for (final OWLOntology owlOntology : manager.getOntologies()) {
                for (final OWLIndividual individual : EntitySearcher.getIndividuals(clazz, owlOntology)) {
                    try {
                        // TODO: define OWLIndividual visitor
                        final CypherOWLIndividualVisitor visitor = new CypherOWLIndividualVisitor();
                        individual.accept(visitor);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    private void processGenericAxioms() {
        for (final OWLClassAxiom axiom : ontology.getGeneralClassAxioms()) {
            try {
                // TODO: define GenericOWLClassAxiom visitor
                final CypherDefaultOWLAxiomVisitor visitor = new CypherDefaultOWLAxiomVisitor();
                axiom.accept(visitor);
            } catch (Exception ignored) {
            }
        }
    }
}
