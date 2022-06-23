package br.edu.univas.si7.topicos.domain.enums;

import java.util.stream.Stream;

public enum UserProfile {

	ADMIN(1, "ROLE_ADMIN"),
	CUSTOMER(2, "ROLE_CUSTOMER");
	
    private int code;
    private String description;

    private UserProfile(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserProfile toEnum(String description) {

        return Stream.of(UserProfile.values())
          .filter(t -> t.getDescription().equals(description))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Invalid profile: " + description));
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
