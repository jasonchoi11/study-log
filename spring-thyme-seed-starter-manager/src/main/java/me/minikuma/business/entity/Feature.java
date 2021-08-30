package me.minikuma.business.entity;

public enum Feature {

    SEEDSTARTER_SPECIFIC_SUBSTRATE("SEEDSTARTER_SPECIFIC_SUBSTRATE"),
    FERTILIZER("FERTILIZER"),
    PH_CORRECTOR("PH_CORRECTOR");

    private String name;
    public static final Feature[] ALL = { SEEDSTARTER_SPECIFIC_SUBSTRATE, FERTILIZER, PH_CORRECTOR};

    Feature(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Feature forName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for feature");
        }
        if (name.toUpperCase().equals(SEEDSTARTER_SPECIFIC_SUBSTRATE.getName())) {
            return SEEDSTARTER_SPECIFIC_SUBSTRATE;
        } else if (name.toUpperCase().equals(FERTILIZER.getName())) {
            return FERTILIZER;
        } else if (name.toUpperCase().equals(PH_CORRECTOR.getName())) {
            return PH_CORRECTOR;
        }
        throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any Feature");
    }
}
