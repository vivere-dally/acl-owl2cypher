package edu.stanford.protege.sbuciu.model.nodes;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Relationship;

import java.util.HashMap;
import java.util.Map;

import static org.neo4j.cypherdsl.core.Cypher.*;

public class CypherRelationship {
    public final String name;
    public final Map<String, Object> properties = new HashMap<>();
    public final CypherNode to;
    public final CypherRelationshipType type;

    public CypherRelationship(String name, CypherNode to) {
        this.name = name;
        this.to = to;
        this.type = CypherRelationshipType.TO;
    }

//    public CypherRelationship(String name, CypherNode to, CypherRelationshipType type) {
//        this.name = name;
//        this.to = to;
//        this.type = type;
//    }

    public String toCypher(CypherNode from) {
        Node nfrom = node(from.type.getType()).named("from").withProperties("iri", literalOf(from.iri.toString()));
        Node nto = node(to.type.getType()).named("to").withProperties("iri", literalOf(to.iri.toString()));
        Relationship relationship = nfrom.relationshipTo(nto, name).named("r").withProperties(properties);
        return Cypher.match(nfrom, nto).create(relationship).build().getCypher();
    }
}
