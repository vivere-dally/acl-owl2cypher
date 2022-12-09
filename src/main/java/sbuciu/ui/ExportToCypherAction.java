package sbuciu.ui;

import sbuciu.converter.OntologyConverter;
import lombok.extern.slf4j.Slf4j;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.awt.event.ActionEvent;

@Slf4j
public class ExportToCypherAction extends ProtegeOWLAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        final OWLOntology owlOntology = this.getOWLModelManager().getActiveOntology();
        final OWLOntologyManager owlOntologyManager = owlOntology.getOWLOntologyManager();
        final String logOntoName = owlOntology.isAnonymous() ? "Anonymous" : owlOntology.getOntologyID().getOntologyIRI().get().toString();

        log.info("Converting ontology {}", logOntoName);
        final OntologyConverter converter = new OntologyConverter(owlOntology, owlOntologyManager);
        converter.convert();
        log.info("Successful conversion");
    }

    @Override
    public void initialise() throws Exception {
    }

    @Override
    public void dispose() throws Exception {
    }
}
