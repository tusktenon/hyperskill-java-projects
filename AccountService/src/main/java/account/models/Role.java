package account.models;

public enum Role {
    ACCOUNTANT, ADMINISTRATOR, AUDITOR, USER;

    public static enum Group {ADMINISTRATIVE, BUSINESS}

    public Group group() {
        return switch (this) {
            case ADMINISTRATOR -> Group.ADMINISTRATIVE;
            case ACCOUNTANT, AUDITOR, USER -> Group.BUSINESS;
        };
    }

    public String toStringWithPrefix() {
        return "ROLE_" + name();
    }
}
