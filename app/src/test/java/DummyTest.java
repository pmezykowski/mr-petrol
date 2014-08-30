import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by pawel on 2014-08-11.
 */


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    @Before
    public void setup() {

    }

    @Test
    public void shouldAlwaysPass2() {
        Assert.assertFalse(false);
    }

    @Test
    public void shouldAlwaysPass() {
        Assert.assertTrue(true);
    }
}
