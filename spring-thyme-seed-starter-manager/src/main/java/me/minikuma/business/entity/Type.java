package me.minikuma.business.entity;

public enum Type {

    PLASTIC("PLASTIC"),
    WOOD("WOOD");

    public static final Type[] ALL = { PLASTIC, WOOD };
    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Type forName(final String name) {
        if (name == null) {
            // null 처리
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        if (name.toUpperCase().equals(PLASTIC.getName())) {
            return PLASTIC;
        } else if (name.toUpperCase().equals(WOOD.getName())) {
            return WOOD;
        }
        // 일치하는 타입이 없는 경우 에러
        throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any Type");
    }
}
