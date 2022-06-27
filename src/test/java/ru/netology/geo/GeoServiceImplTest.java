package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

public class GeoServiceImplTest {

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
}
