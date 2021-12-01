// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package features;

import fr.snapgames.fromclasstogame.core.gfx.Window;
import io.cucumber.java8.En;

import java.awt.*;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WindowDefSteps implements En {

    Window win;
    boolean multiScreenStation;
    GraphicsDevice dev0;

    public WindowDefSteps() {
        Given("A Window is created", () -> {
            win = new Window("test", 320, 200);
        });

        Then("the window is in Windowed mode", () -> {
            assertTrue("The window has net been set to windowed mode", !win.isFullScreen());
        });

        When("I switched window to full screen mode", () -> {
            win.switchFullScreen();
        });

        Then("the window is in fullscreen mode", () -> {
            Thread.sleep(100);
            assertTrue("The window has net been set to windowed mode", win.isFullScreen());
        });

        Given("A fullscreen Window", () -> {
            win = new Window("test", 320, 200);
            win.switchFullScreen();
        });

        When("multiple screens are available", () -> {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devs = env.getScreenDevices();
            multiScreenStation = devs.length > 1;
        });

        And("I switch between screens", () -> {
            if (multiScreenStation) {
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                dev0 = env.getDefaultScreenDevice();
                win.switchScreen();
            } else {
                assertTrue(true);
            }
        });

        Then("Window is in fullscreen on the next screen.", () -> {
            GraphicsDevice dev1 = win.getScreenDevice();
            assertNotEquals("The Window has not switched to another screen", dev0.getIDstring(), dev1.getIDstring());
        });
    }
}
