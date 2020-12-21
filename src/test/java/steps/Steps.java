package steps;

import io.cucumber.java.ru.Когда;
import ru.homework.framework.managers.ManagerPages;

public class Steps {

    private ManagerPages app = ManagerPages.getManagerPages();

    @Когда("^Загружена стартовая страница$")
    public void getStartPage() {
        app.getStartPage();
    }
}
