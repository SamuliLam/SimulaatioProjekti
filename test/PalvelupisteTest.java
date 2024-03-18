import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.model.Asiakas;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

import java.sql.SQLException;

public class PalvelupisteTest {
    private Palvelupiste palvelupiste;
    private ContinuousGenerator generator;
    private Tapahtumalista eventList;
    private Asiakas asiakas;

    @BeforeEach
    public void setUp() throws SQLException {
        Trace.setTraceLevel(Trace.Level.INFO);
        generator = Mockito.mock(ContinuousGenerator.class);
        eventList = new Tapahtumalista();
        palvelupiste = new Palvelupiste(generator, eventList, TapahtumanTyyppi.ARRMARKET);
        asiakas = new Asiakas();
        Trace.setTraceLevel(Trace.Level.INFO);
    }

    @Test
    public void testAddToQue() {
        palvelupiste.addToQue(asiakas);
        assertEquals(1, palvelupiste.getQueSize());
    }

    @Test
    public void testTakeFromQue() {
        palvelupiste.addToQue(asiakas);
        Asiakas removedAsiakas = palvelupiste.takeFromQue();
        assertEquals(asiakas, removedAsiakas);
        assertEquals(0, palvelupiste.getQueSize());
    }

    @Test
    public void testStartService() {
        Mockito.when(generator.sample()).thenReturn(1.0);
        palvelupiste.addToQue(asiakas);
        palvelupiste.startService();
        assertTrue(palvelupiste.isReserved());
    }

    @Test
    public void testInQue() {
        assertFalse(palvelupiste.inQue());
        palvelupiste.addToQue(asiakas);
        assertTrue(palvelupiste.inQue());
    }
}