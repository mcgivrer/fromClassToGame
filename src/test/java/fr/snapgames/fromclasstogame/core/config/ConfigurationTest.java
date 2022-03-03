package fr.snapgames.fromclasstogame.core.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationTest {
    Configuration config;

    @Before
    public void setUp() throws Exception {
        config = new Configuration("test");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void readValuesFromFile() {
    }

    @Test
    public void parseArgs() {
    }
}