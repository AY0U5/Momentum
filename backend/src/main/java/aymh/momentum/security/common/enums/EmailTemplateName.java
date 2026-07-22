package aymh.momentum.security.common.enums;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACCOUNT_CREATED("account_created", "Welcome at Momentum !"),
    RESET_PASSWORD("reset_password", "Reset your password");

    private final String name;
    private final String defaultSubject;

    EmailTemplateName(String name, String defaultSubject) {
        this.name = name;
        this.defaultSubject = defaultSubject;
    }
}