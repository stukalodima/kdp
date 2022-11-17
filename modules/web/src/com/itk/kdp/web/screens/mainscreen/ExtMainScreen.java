package com.itk.kdp.web.screens.mainscreen;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.itk.kdp.web.screens.desktop.MyTasksMenuBadge;
import de.diedavids.cuba.userinbox.web.screens.UserInboxMessageMenuBadge;

import javax.inject.Inject;


@UiController("extMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen {
    @Inject
    protected SideMenu sideMenu;
    @Inject
    protected Timer updateCountersTimer;

    @Inject
    protected UserInboxMessageMenuBadge userInboxMessageMenuBadge;
    @Inject
    private Messages messages;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private MyTasksMenuBadge myTasksMenuBadge;

    @Subscribe
    protected void onInit(InitEvent event) {
        myTasksMenuBadge.initMyTasksMenuItem(sideMenu, updateCountersTimer, this);
        userInboxMessageMenuBadge.initMessagesMenuItem(
                sideMenu,
                updateCountersTimer,
                this
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        super.onAfterShow(event);
        myTasksMenuBadge.updateTasksCounter(sideMenu);
        userInboxMessageMenuBadge.updateMessageCounter(sideMenu);
    }

    @Subscribe("updateCountersTimer")
    protected void onUpdateCountersTimerTimerAction(Timer.TimerActionEvent event) {
        myTasksMenuBadge.updateTasksCounter(sideMenu);
        userInboxMessageMenuBadge.updateMessageCounter(sideMenu);
    }
}