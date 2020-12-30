package steps;

import io.cucumber.java.Before;
import org.junit.After;
import ru.homework.framework.managers.InitManager;

public class Hooks {

    @Before
    public void before() {
        InitManager.initFramework();
    }

    @After
    public void after() {
        InitManager.quitFramework();
    }
}
