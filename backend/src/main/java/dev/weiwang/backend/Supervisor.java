package dev.weiwang.backend;

import lombok.Data;

@Data
public class Supervisor {
    private String id;
    private String phone;
    private String jurisdiction;
    private String identificationNumber;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return jurisdiction + " - " + lastName + ", " + firstName;
    }
}
