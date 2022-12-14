package edu.stanford.protege.sbuciu.converter;

import edu.stanford.protege.sbuciu.model.CypherData;
import edu.stanford.protege.sbuciu.model.nodes.CypherNode;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

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
        for (final Collection<? extends CypherNode> nodes : data.getValues()) {
            for (final CypherNode node : nodes) {
                final Node n = node(node.getName())
                        .named("n")
                        .withProperties(
                                "iri", literalOf(node.getIRIAsString()),
                                "displayName", literalOf(node.getDisplayName()));
                final Statement s = Cypher.create(n).build();
                log.info(s.getCypher());
            }
        }
    }
}
