package account.models;

public enum Role {
    ACCOUNTANT, ADMINISTRATOR, USER;

    public static enum Group {ADMINISTRATIVE, BUSINESS}

    public Group group() {
        return switch (this) {
            case ADMINISTRATOR -> Group.ADMINISTRATIVE;
            case ACCOUNTANT, USER -> Group.BUSINESS;
        };
    }

    public String toStringWithPrefix() {
        return "ROLE_" + name();
    }
}
