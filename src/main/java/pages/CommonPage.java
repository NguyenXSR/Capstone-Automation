package pages;

import base.BasePage;
import components.TopBarNavigation;

public class CommonPage extends BasePage {
    private TopBarNavigation topBarNavigation;

    public CommonPage() {
        topBarNavigation = new TopBarNavigation();
    }

    public TopBarNavigation getTopBarNavigation() {
        return topBarNavigation;
    }


}
