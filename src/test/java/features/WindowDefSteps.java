// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package features;

import static org.junit.Assert.assertTrue;

import fr.snapgames.fromclasstogame.core.gfx.Window;
import io.cucumber.java8.En;

public class WindowDefSteps implements En {

    Window win;

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
    }
}
