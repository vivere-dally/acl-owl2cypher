package edu.stanford.protege.sbuciu.converter;

import edu.stanford.protege.sbuciu.model.CypherData;
import edu.stanford.protege.sbuciu.model.nodes.AnnotationPropertyNode;
import edu.stanford.protege.sbuciu.model.nodes.ClassNode;
import edu.stanford.protege.sbuciu.model.nodes.DatatypeNode;
import edu.stanford.protege.sbuciu.model.nodes.IndividualNode;
import edu.stanford.protege.sbuciu.visitor.CypherDefaultOWLAxiomVisitor;
import edu.stanford.protege.sbuciu.visitor.CypherOWLClassVisitor;
import edu.stanford.protege.sbuciu.visitor.CypherOWLIndividualVisitor;
import edu.stanford.protege.sbuciu.visitor.CypherOWLObjectWalkerVisitor;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OntologyConverter {
    private static final Logger log = LoggerFactory.getLogger(OntologyConverter.class);

    private static final Set<String> NON_LITERAL_AXIOM_VALUE = new HashSet<>(Arrays.asList("targetElement",
            "subjectElement", "predicateElement", "providesNotation", "subjectDescription", "predicateDescription",
            "objectDescription", "providesView", "viewUsesNotation", "objectElement", "isTypeOf"));

    private final OWLOntology ontology;
    private final OWLOntologyManager manager;
    private final CypherData data = new CypherData();

    public OntologyConverter(OWLOntology ontology, OWLOntologyManager manager) {
        this.ontology = ontology;
        this.manager = manager;
    }

    public void initConversion() {
        //        register annotation properties
        for (final OWLAnnotationProperty value : ontology.getAnnotationPropertiesInSignature(Imports.INCLUDED)) {
            if (value.isAnonymous()) {
                continue;
            }

            data.annotationProperties.put(value.getIRI(), new AnnotationPropertyNode(value.getIRI(), value));
        }

        //        register classes
        for (final OWLClass value : ontology.getClassesInSignature(Imports.INCLUDED)) {
            if (value.isAnonymous()) {
                continue;
            }

            data.classes.put(value.getIRI(), new ClassNode(value.getIRI(), value));
        }

//        //        register data types
//        for (final OWLDatatype value : ontology.getDatatypesInSignature(Imports.INCLUDED)) {
//            if (value.isAnonymous()) {
//                continue;
//            }
//
//            data.dataTypes.put(value.getIRI(), new DatatypeNode(value.getIRI(), value));
//        }

        for (final OWLNamedIndividual value : ontology.getIndividualsInSignature(Imports.INCLUDED)) {
            if (value.isAnonymous()) {
                continue;
            }

            data.individuals.put(value.getIRI(), new IndividualNode(value.getIRI(), value));
        }
    }

    public void convert() {
        log.info("converting...");

        initConversion();

//        for (final OWLAxiom axiom : ontology.getAxioms(Imports.INCLUDED)) {
//            log.info(axiom.toString());
//        }

//        preProcess();
//        process();
//        postProcess();
        log.info("done");
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

            data.addAxiom(subject, property, value);
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
                    final CypherOWLClassVisitor visitor = new CypherOWLClassVisitor(data);
                    axiom.accept(visitor);
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
