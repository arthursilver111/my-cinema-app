package cinema.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class EmailValidatorTest {
    private static ConstraintValidator<ValidEmail, String> constraintValidator;
    private static ConstraintValidatorContext context;

    @BeforeAll
    static void beforeAll() {
        context = Mockito.mock(ConstraintValidatorContext.class);
        constraintValidator = new EmailValidator();
    }

    @Test
    void isValid_Ok() {
        String email = "abcda@gmail.com";
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
        boolean valid = constraintValidator.isValid(email, context);
        Assertions.assertTrue(valid);
    }

    @Test
    void isValid_NotOk() {
        String email = "1234";
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
        boolean valid = constraintValidator.isValid(email, context);
        Assertions.assertFalse(valid);
    }
}
