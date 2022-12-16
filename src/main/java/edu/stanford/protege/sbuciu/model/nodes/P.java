package edu.stanford.protege.sbuciu.model.nodes;

import java.util.ArrayList;
import java.util.List;

public class P {
    public final CypherNode n;
    public final List<CypherRelationship> rel;

    public P(CypherNode n) {
        this.n = n;
        this.rel = new ArrayList<>();
    }
}
