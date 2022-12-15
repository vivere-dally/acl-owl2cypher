package edu.stanford.protege.sbuciu.model.nodes;

public enum CypherRelationshipType {
    HAS_LABEL("HAS_LABEL"),
    ;

    private final String type;

    CypherRelationshipType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
