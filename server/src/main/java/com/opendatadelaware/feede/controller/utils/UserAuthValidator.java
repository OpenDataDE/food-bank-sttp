package com.opendatadelaware.feede.controller.utils;

import org.hibernate.validator.constraints.Email;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by denniskalaygian on 6/30/17.
 */

public class UserAuthValidator {
    private static final Validator VALIDATOR;

    static {
      VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @NotNull(message="First name cannot be null")
    private String firstName;

    @NotNull(message="Last name cannot be null")
    private String lastName;

    @NotNull(message="Email cannot be null")
    @Email
    private String email;

    @NotNull(message="Password cannot be null")
    private String password;

    @NotNull(message="Phone number cannot be null")
    @Pattern(regexp="(^$|(\\(?[0-9]{3}\\)?-?[0-9]{3}-?[0-9]{4})|(\\(?1\\)?-?\\(?[0-9]{3}\\)?-?[0-9]{3}-?[0-9]{4}))")
    private String phone;

    @NotNull(message ="Street cannot be null")
    private String street;

    @NotNull(message ="City cannot be null")
    private String city;

    @NotNull(message ="State cannot be null")
    private String state;

    @NotNull(message ="Zip cannot be null")
    private String zip;

    @NotNull(message="Type cannot be null")
    private String type;

    public UserAuthValidator() {}

    public boolean isValid() {
      return VALIDATOR.validate(this).size() == 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserAuthValidator setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAuthValidator setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAuthValidator setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAuthValidator setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserAuthValidator setPhone(String phoneNumber) {
        this.phone = phoneNumber;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public UserAuthValidator setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserAuthValidator setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public UserAuthValidator setState(String state) {
        this.state = state;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public UserAuthValidator setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getType() {
        return type;
    }

    public UserAuthValidator setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
      return String.format("[first_name=%s, last_name=%s, email=%s]", this.firstName, this.lastName, this.email);
    }
}
