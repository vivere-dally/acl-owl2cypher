package sbuciu.converter;

import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.OWLOntologyWalker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class OntologyConverter {
    private static final Set<String> NON_LITERAL_AXIOM_VALUE = new HashSet<>(Arrays.asList("targetElement",
            "subjectElement", "predicateElement", "providesNotation", "subjectDescription", "predicateDescription",
            "objectDescription", "providesView", "viewUsesNotation", "objectElement", "isTypeOf"));

    private final OWLOntology ontology;
    private final OWLOntologyManager manager;

    public OntologyConverter(OWLOntology ontology, OWLOntologyManager manager) {
        this.ontology = ontology;
        this.manager = manager;
    }

    public void convert() {
        log.info("converting ontology");
        preProcess();
        process();
        postProcess();
    }

    private void preProcess() {
        for (final OWLAxiom axiom : ontology.getAxioms(Imports.INCLUDED)) {
            if (!(axiom instanceof OWLAnnotationAxiom)) {
                continue;
            }

            final OWLAnnotationAssertionAxiom assertionAxiom = (OWLAnnotationAssertionAxiom) axiom;
            final String subject = assertionAxiom.getSubject().toString();
            final String property = assertionAxiom.getProperty().getIRI().toString();
            final String value = NON_LITERAL_AXIOM_VALUE.contains(assertionAxiom.getProperty().getIRI().getShortForm()) ?
                    assertionAxiom.getValue().toString() :
                    assertionAxiom.getValue().asLiteral().get().getLiteral();

            log.info("subject {} property {} value {}", subject, property, value);
        }

        try {
            // TODO
            final OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
            final MyOWLObjectVisitor visitor = new MyOWLObjectVisitor();
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
        for (final OWLAnnotationProperty owlAnnotationProperty : ontology.getAnnotationPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLAnnotationAxiom owlAnnotationAxiom : ontology.getAxioms(owlAnnotationProperty, Imports.INCLUDED)) {
                try {
                    final String subject = owlAnnotationProperty.getIRI().toString();
                    final String property = "TODO: some constant";

                    final OWLAnnotationPropertyRangeAxiom rangeAxiom = (OWLAnnotationPropertyRangeAxiom) owlAnnotationAxiom;
                    final String value = rangeAxiom.getRange().toString();

                    log.info("subject {} property {} value {}", subject, property, value);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void processClasses() {
        for (final OWLClass owlClass : ontology.getClassesInSignature(Imports.INCLUDED)) {
            for (final OWLClassAxiom owlClassAxiom : ontology.getAxioms(owlClass, Imports.INCLUDED)) {
                try {
                    // TODO: define OWLClass visitor
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void processObjectProperties() {
        for (final OWLObjectProperty owlObjectProperty : ontology.getObjectPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLObjectPropertyAxiom owlObjectPropertyAxiom : ontology.getAxioms(owlObjectProperty, Imports.INCLUDED)) {
                try {
                    // TODO: define OWLObjectProperty visitor
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void processDataProperties() {
        for (final OWLDataProperty owlDataProperty : ontology.getDataPropertiesInSignature(Imports.INCLUDED)) {
            for (final OWLDataPropertyAxiom owlDataPropertyAxiom : ontology.getAxioms(owlDataProperty, Imports.INCLUDED)) {
                try {
                    // TODO: define OWLObjectProperty visitor
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void processIndividuals() {
        for (final OWLClass owlClass : ontology.getClassesInSignature(Imports.INCLUDED)) {
            for (final OWLOntology owlOntology : manager.getOntologies()) {
                for (final OWLIndividual owlIndividual : EntitySearcher.getIndividuals(owlClass, owlOntology)) {
                    try {
                        // TODO: define OWLIndividual visitor
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    private void processGenericAxioms() {
        for (final OWLClassAxiom owlClassAxiom : ontology.getGeneralClassAxioms()) {
            try {
                // TODO: define GenericOWLClassAxiom visitor
            } catch (Exception ignored) {
            }
        }
    }
}
