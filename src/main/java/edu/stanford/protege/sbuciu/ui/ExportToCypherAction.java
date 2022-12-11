package edu.stanford.protege.sbuciu.ui;

import edu.stanford.protege.sbuciu.converter.OntologyConverter;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.awt.event.ActionEvent;

public class ExportToCypherAction extends ProtegeOWLAction {
    private static final long serialVersionUID = 1753770760598088272L;
    private static final Logger log = LoggerFactory.getLogger(ExportToCypherAction.class);

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