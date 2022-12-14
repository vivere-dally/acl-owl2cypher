package edu.stanford.protege.sbuciu.converter;

import edu.stanford.protege.sbuciu.model.CypherData;
import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.neo4j.cypherdsl.core.Cypher.*;

public class CypherWriter {
    private static final Logger log = LoggerFactory.getLogger(CypherWriter.class);

    private final CypherData data;

    public CypherWriter(CypherData data) {
        this.data = data;
    }

    public void write(String path) {
        writeEntities();
    }

    private void writeEntities() {
        for (final CypherNode node : data.classes.values()) {
            final Node n = node(node.getName()).withProperties("iri", node.getIRIAsString(), "iriShort", node.getShortForm()).named("n");
            final Statement s = Cypher.create(n).build();
            log.info(s.getCypher());
        }
    }
}
