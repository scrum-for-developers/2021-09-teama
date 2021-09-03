package de.codecentric.psd.worblehat.web.formdata;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookDataFormDataTest {

  private Validator validator;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void shouldTrimIsbn() {
    BookDataFormData bookForm = new BookDataFormData();
    bookForm.setIsbn("  11111111111  ");
    assertThat(bookForm.getIsbn(), is("11111111111"));
  }

  @Test
  public void shouldDenyFutureDates() {
    BookDataFormData bookForm = new BookDataFormData();
    bookForm.setYearOfPublication("2030");
    Set<ConstraintViolation<BookDataFormData>> violations =
        validator.validateProperty(bookForm, "yearOfPublication");
    assertThat(
        violations,
        contains(
            hasProperty(
                "messageTemplate", is("{invalid.length.bookDataFormData.yearOfPublication}"))));
  }
}