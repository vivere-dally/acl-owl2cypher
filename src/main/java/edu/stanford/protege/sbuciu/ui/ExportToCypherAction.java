package edu.stanford.protege.sbuciu.ui;

import edu.stanford.protege.sbuciu.converter.OntologyMiner;
import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import edu.stanford.protege.sbuciu.model.nodes.CypherRelationship;
import edu.stanford.protege.sbuciu.model.nodes.P;
import org.protege.editor.core.ui.error.ErrorLogPanel;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ExportToCypherAction extends ProtegeOWLAction {
    private static final long serialVersionUID = 1753770760598088272L;
    private static final Logger log = LoggerFactory.getLogger(ExportToCypherAction.class);

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            final OWLOntology owlOntology = this.getOWLModelManager().getActiveOntology();
            final OWLOntologyManager owlOntologyManager = owlOntology.getOWLOntologyManager();
            final String logOntoName = owlOntology.isAnonymous() ? "Anonymous" : owlOntology.getOntologyID().getOntologyIRI().get().toString();

            log.info("Converting ontology {}", logOntoName);

            OWLReasoner reasoner = new StructuralReasoner(owlOntology, new SimpleConfiguration(), BufferingMode.BUFFERING);

            final File file = ExportToCypherDialog.showDialog(getOWLEditorKit());
            if (file == null) {
                throw new Exception("Please select a valid txt file.");
            }

            final OntologyMiner converter = new OntologyMiner(owlOntology, owlOntologyManager);
            final Map<CypherNode, P> m = converter.mine();
            writeToFile(file, m);

            log.info("Successful conversion");
        } catch (Exception err) {
            ErrorLogPanel.showErrorDialog(err);
            log.info("Conversion failed");
        }
    }

    private void writeToFile(final File file, final Map<CypherNode, P> m) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (final CypherNode n : m.keySet()) {
                writer.write(n.getCypher());
                writer.write(';');
                writer.newLine();
            }

            for (final Map.Entry<CypherNode, P> e : m.entrySet()) {
                for (final CypherRelationship r : e.getValue().rel) {
                    writer.write(r.toCypher(e.getKey()));
                    writer.write(';');
                    writer.newLine();
                }
            }
        }
    }

    @Override
    public void initialise() throws Exception {
    }

    @Override
    public void dispose() throws Exception {
    }
}
