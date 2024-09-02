package org.hae.tasklogue.utils.enums;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    Activate_Account("activate_account")

    ;
    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
