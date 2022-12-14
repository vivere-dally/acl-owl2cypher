package edu.stanford.protege.sbuciu.model.nodes2;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.semanticweb.owlapi.model.IRI;

import static org.neo4j.cypherdsl.core.Cypher.literalOf;
import static org.neo4j.cypherdsl.core.Cypher.node;

public class CypherNode {
    public final IRI iri;
    public final CypherNodeType type;

    public CypherNode(IRI iri, CypherNodeType type) {
        this.iri = iri;
        this.type = type;
    }

    public String getCypher() {
        final Node n = node(type.getType())
                .named("n")
                .withProperties(
                        "iri", literalOf(iri.toString()),
                        "iriShortForm", literalOf(iri.getShortForm()));

        return Cypher.create(n).build().getCypher();
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
