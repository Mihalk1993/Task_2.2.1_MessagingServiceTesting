package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AllTests {

    // byIp
    @Test
    public void messageSenderImplRUS() {
//      arrange
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.contains("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        String expectedText = "Добро пожаловать";
//      act
        String text = messageSender.send(headers);
//      assert
        Assertions.assertEquals(expectedText, text);
    }

    // byIp
    @Test
    public void messageSenderImplENG() {
//      arrange
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.contains("96.")))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        String expectedText = "Welcome";
//      act
        String text = messageSender.send(headers);
//      assert
        Assertions.assertEquals(expectedText, text);
    }

    //  public Location byIp(String ip)
    @ParameterizedTest
    @MethodSource("byIpMethodSource")
    public void byIpTestParamMethodSource(String ip, Location expectedLocation) {
//      arrange
        GeoServiceImpl geoService = new GeoServiceImpl();
//      act
        Location location = geoService.byIp(ip);
//      assert
        Assertions.assertEquals(expectedLocation, location);
    }

    public static Stream<Arguments> byIpMethodSource() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.1.3.5", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.1.3.5", new Location("New York", Country.USA, null, 0))
        );
    }

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
