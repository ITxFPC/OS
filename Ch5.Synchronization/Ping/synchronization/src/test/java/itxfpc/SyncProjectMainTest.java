package itxfpc;

import static org.junit.Assert.assertTrue;
import java.util.concurrent.Semaphore;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SyncProjectMainTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Semaphore s = new Semaphore(3);
        
        assertTrue( true );
    }
}
