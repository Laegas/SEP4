package test.weatherDecoderTest;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;
import util.weatherUtil.MetarReader;

import static org.junit.Assert.*;

/**
 * Created by kenneth on 23/05/2018.
 */
@RunWith(Arquillian.class)
public class MetarReaderTest {
    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void decodeMetar() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MetarReader.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
