package edu.stanford.protege.sbuciu.model.nodes2;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Relationship;

import static org.neo4j.cypherdsl.core.Cypher.*;

public class CypherRelationship {
    public final CypherRelationshipType type;
    public final String value;
    public final CypherNode to;

    public CypherRelationship(CypherRelationshipType type, String value, CypherNode to) {
        this.type = type;
        this.value = value;
        this.to = to;
    }

    public String toCypher(CypherNode from) {
        Node nfrom = node(from.type.getType()).named("from");
        Node nto = node(to.type.getType()).named("to");
        Relationship relationship = nfrom.relationshipTo(nto).named("r").withProperties(type.getType(), value);
        return Cypher.create(relationship).build().getCypher();
    }
}
