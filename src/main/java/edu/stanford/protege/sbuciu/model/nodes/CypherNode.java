package edu.stanford.protege.sbuciu.model.nodes;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.OWLAnnotation;

import java.util.ArrayList;
import java.util.List;

public abstract class CypherNode {
    public final List<OWLAnnotation> annotations = new ArrayList<>();

    public abstract String getName();

    protected abstract HasIRI getIRI();

    public String getIRIAsString() {
        return getIRI().getIRI().toString();
    }

    public String getDisplayName() {
        return getIRI().getIRI().getShortForm();
    }
}
