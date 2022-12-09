package ui;

import lombok.extern.slf4j.Slf4j;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.awt.event.ActionEvent;

@Slf4j
public class ExportToCypherAction extends ProtegeOWLAction {
    private OWLOntology owlOntology;

    @Override
    public void actionPerformed(ActionEvent e) {
        final OWLOntology owlOntology = this.getOWLModelManager().getActiveOntology();
        final OWLOntologyManager owlOntologyManager = owlOntology.getOWLOntologyManager();
        final String logOntoName = owlOntology.isAnonymous() ? "Anonymous" : owlOntology.getOntologyID().getOntologyIRI().get().toString();

        log.info("Converting ontology {}", logOntoName);
    }

    @Override
    public void initialise() throws Exception {
        this.owlOntology = getOWLModelManager().getActiveOntology();
    }

    @Override
    public void dispose() throws Exception {
        this.owlOntology = null;
    }
}
