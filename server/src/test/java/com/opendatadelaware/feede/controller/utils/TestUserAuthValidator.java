package com.opendatadelaware.feede.controller.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by denniskalaygian on 6/30/17.
 */
public class TestUserAuthValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestUserAuthValidator.class);

    private static void testErrorLogger() {
        LOGGER.error("The test was setup improperly");
    }

    public Optional<UserAuthValidator> configure(String fileNameAndPath) {
        try {
            URL inputFile = TestUserAuthValidator.class.getResource(fileNameAndPath);
            byte[] jsonData = Files.readAllBytes(Paths.get(inputFile.toURI()));
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.of(objectMapper.readValue(jsonData, UserAuthValidator.class));
        } catch (URISyntaxException | IOException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Test
    public void testIsValidBadInputJson() {
        Optional<UserAuthValidator> userAuthValidator = configure("/json/BadUserInput.json");
        Boolean result = userAuthValidator.get().isValid();
        if (userAuthValidator.isPresent()) Assert.assertFalse("The object exists and it shouldn't", result);
        else testErrorLogger();
    }

    @Test
    public void testIsValidGoodInputJson() {
        Optional<UserAuthValidator> userAuthValidator = configure("/json/GoodUserInput.json");
        Boolean result = userAuthValidator.get().isValid();
        if (userAuthValidator.isPresent()) Assert.assertTrue("The object doesn't exist", result);
        else testErrorLogger();
    }

    @Test
    public void testInvalidPhoneNumberNotEnoughDigits() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("John")
                .setLastName("Doe").setEmail("johnDoe@gmail.com")
                .setPassword("12345").setPhone("3023333")
                .setStreet("111 This Street").setCity("Milford").setStreet("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The phone number doesn't have enough digits", user.isValid());
    }

    @Test
    public void testInvalidPhoneNumberTooManyDigits() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("John")
                .setLastName("Doe").setEmail("johnDoe@gmail.com")
                .setPassword("12345").setPhone("3023321333333")
                .setStreet("111 This Street").setCity("Milford").setStreet("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The phone number has too many digits", user.isValid());
    }

    @Test
    public void testValidPhoneNumberTenDigits() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail.com").setPassword("12345").setPhone("3023833333")
                .setStreet("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertTrue("The ten digit phone number isn't valid", user.isValid());
    }

    @Test
    public void testValidPhoneNumberElevenDigits() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail.com").setPassword("12345").setPhone("13023833333")
                .setStreet("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertTrue("The eleven digit phone number isn't valid", user.isValid());
    }

    @Test
    public void testInvalidPhoneNumberLetter() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail.com").setPassword("12345").setPhone("30233a3333")
                .setState("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The phone number contains a letter", user.isValid());
    }

    @Test
    public void testInvalidPhoneNumberParenthesis() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail.com").setPassword("12345").setPhone("(302-383-3)333")
                .setStreet("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The phone number has invalid parenthesis", user.isValid());
    }

    @Test
    public void testValidPhoneNumberParenthesis() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail.com").setPassword("12345").setPhone("(302)-383-3333")
                .setStreet("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertTrue("The phone number with parenthesis isn't valid", user.isValid());
    }

    @Test
    public void testInvalidPhoneNumberTooManyDashes() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("John")
                .setLastName("Doe").setEmail("johnDoe@gmail.com")
                .setPassword("12345").setPhone("302--333-3333")
                .setStreet("111 This Street").setCity("Milford").setStreet("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The phone number has too many dashes", user.isValid());
    }

    @Test
    public void testValidPhoneNumberDashes() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail.com").setPassword("12345").setPhone("302-383-3333")
                .setStreet("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertTrue("The phone number with dashes isn't valid", user.isValid());
    }

    @Test
    public void testInvalidEmailNoCommercialAt() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe.com").setPassword("12345").setPhone("(302)--33a-3333")
                .setState("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The email is missing the commercial at", user.isValid());
    }

    @Test
    public void testInvalidEmailNoTopLevelDomain() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail").setPassword("12345").setPhone("(302)--33a-3333")
                .setState("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The email is missing a top level domain", user.isValid());
    }

    @Test
    public void testInvalidEmailNoMailbox() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("@gmail.com").setPassword("12345").setPhone("(302)--33a-3333")
                .setState("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertFalse("The email has no mailbox", user.isValid());
    }

    @Test
    public void testValidEmail() {
        UserAuthValidator user = new UserAuthValidator().setFirstName("Jane").setLastName("Doe")
                .setEmail("johnDoe@gmail.com").setPassword("12345").setPhone("(302)-383-3333")
                .setStreet("111 This Street").setCity("Milford").setState("DE")
                .setZip("19963").setType("Farm");
        Assert.assertTrue("The email isn't valid", user.isValid());
    }

    @Test
    public void testToString() {
        Optional<UserAuthValidator> userAuthValidator = configure("/json/GoodUserInput.json");
        String result = userAuthValidator.get().toString();
        String expected = "[first_name=John, last_name=Doe, email=JohnDoe@gmail.com]";
        Assert.assertTrue("The wrong string is being returned", expected.equals(result));
    }

}
