import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import simu.framework.Trace;
import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;
import simu.model.Tuotehallinta.GroceryCategory;
import simu.model.Tuotehallinta.Item;

import java.util.ArrayList;
import java.util.HashSet;

public class AsiakasTest {
    private Asiakas asiakas;

    @BeforeEach
    public void setUp() {
        Trace.setTraceLevel(Trace.Level.INFO);
        asiakas = new Asiakas();
    }

    @Test
    public void testAddSpentMoney() {
        double initialMoney = asiakas.getSpentMoney();
        asiakas.addSpentMoney(100.0);
        assertEquals(initialMoney + 100.0, asiakas.getSpentMoney());
    }
    @Test
    public void testGenerateRandomRuokalista() {
        HashSet<TapahtumanTyyppi> servicePointList = asiakas.getservicePointList();
        assertTrue(servicePointList.contains(TapahtumanTyyppi.ARRMARKET));
        assertTrue(servicePointList.contains(TapahtumanTyyppi.CHECKOUTDEP));
    }

    @Test
    public void testGetGroceryList() {
        ArrayList<GroceryCategory> groceryList = asiakas.getGroceryList();
        for (GroceryCategory category : groceryList) {
            assertTrue(category instanceof GroceryCategory);
        }
    }
}