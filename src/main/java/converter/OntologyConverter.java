package converter;

import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
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
}
