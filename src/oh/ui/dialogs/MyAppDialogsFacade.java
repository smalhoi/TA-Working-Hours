/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui.dialogs;

import djf.AppTemplate;
import djf.modules.AppRecentWorkModule;
import djf.ui.dialogs.AppDialogsFacade;

/**
 *
 * @verson pre-final
 */
public class MyAppDialogsFacade extends AppDialogsFacade{
    public static String showWelcomeDialog(AppTemplate app) {
        // FIRST LOAD ALL THE RECENT WORK
        AppRecentWorkModule recentWork = app.getRecentWorkModule();
        recentWork.loadRecentWorkList();

        // OPEN THE DIALOG
        MyAppWelcomeDialog wd = new MyAppWelcomeDialog(app);
        wd.showAndWait();
        
        return null;
    }
}
