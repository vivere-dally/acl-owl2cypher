package edu.stanford.protege.sbuciu.model.nodes;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.semanticweb.owlapi.model.IRI;

import java.util.HashMap;
import java.util.Map;

import static org.neo4j.cypherdsl.core.Cypher.literalOf;
import static org.neo4j.cypherdsl.core.Cypher.node;

public class CypherNode {
    public final IRI iri;
    public final CypherNodeType type;
    public final Map<String, Object> properties = new HashMap<>();

    public CypherNode(IRI iri, CypherNodeType type) {
        this.iri = iri;
        this.type = type;
    }

    public String getCypher() {
        properties.put("iriShortForm", literalOf(iri.getShortForm()));
        properties.put("iri", literalOf(iri.toString()));
        final Node n = node(type.getType())
                .named("n")
                .withProperties(properties);

        return Cypher.create(n).build().getCypher();
    }

    public static CypherNode of(IRI iri) {
        return new CypherNode(iri, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CypherNode that = (CypherNode) o;
        return iri.equals(that.iri);
    }

    @Override
    public int hashCode() {
        return iri.hashCode();
    }
}
