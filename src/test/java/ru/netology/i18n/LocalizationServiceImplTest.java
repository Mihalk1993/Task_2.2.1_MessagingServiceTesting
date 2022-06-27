package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {

    //  public String locale(Country country)
    @ParameterizedTest
    @MethodSource("localeMethodSource")
    public void localeTestParamMethodSource(Country country, String expectedText) {
//      arrange
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
//      act
        String locale = localizationService.locale(country);
//      assert
        Assertions.assertEquals(expectedText, locale);
    }

    public static Stream<Arguments> localeMethodSource() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"));
    }
}
