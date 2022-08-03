package com.itk.kdp.web.screens.desktop;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.web.WebConfig;
import com.itk.kdp.service.MyTasksService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(MyTasksMenuBadge.NAME)
public class MyTasksMenuBadge {
    static final String NAME = "kdp_MyTasksMenuBadge";

    @Inject
    protected Messages messages;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected WebConfig webConfig;
    @Inject
    private MyTasksService myTasksService;

    public void initMyTasksMenuItem(SideMenu sideMenu, Timer updateCountersTimer, FrameOwner frameOwner) {

        SideMenu.MenuItem myTasksMenuItem = sideMenu.createMenuItem("myTasks");
        myTasksMenuItem.setCaption(messages.getMainMessage("menu_config.kdp_myTasks"));
        myTasksMenuItem.setIcon("font-icon:TASKS");
        myTasksMenuItem.setCommand(menuItem ->
                screenBuilders.screen(frameOwner)
                        .withLaunchMode(OpenMode.NEW_TAB)
                        .withScreenClass(DesktopTable.class)
                        .show()
        );

        sideMenu.addMenuItem(myTasksMenuItem, 0);

        initUpdateCounterTimerDelay(updateCountersTimer);

    }


    private void initUpdateCounterTimerDelay(Timer updateCountersTimer) {
        int period = webConfig.getAppFoldersRefreshPeriodSec() * 1000;
        updateCountersTimer.setDelay(period);
    }

    public void updateTasksCounter(SideMenu sideMenu) {
        sideMenu.getMenuItemNN("myTasks")
                .setBadgeText(
                        messages.formatMainMessage("menu-config.kdp_myTasks.badge", getTasksCounter())
                );
    }

    private long getTasksCounter() {
        return myTasksService.countTasksForCurrentUser();
    }
}
