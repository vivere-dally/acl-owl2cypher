package edu.stanford.protege.sbuciu.model.nodes2;

public enum CypherNodeType {
    INDIVIDUAL("individual"),
    DATA_TYPE("dataType"),
    CLASS("class"),
    OBJECT_PROPERTY("objectProperty"),
    DATA_PROPERTY("dataProperty"),
    ANNOTATION_PROPERTY("annotationProperty");

    private final String type;

    CypherNodeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
