package com.app.ui.page.admin;

import com.kodemore.servlet.control.ScBox;
import com.kodemore.servlet.control.ScGroup;
import com.kodemore.servlet.control.ScGroupArray;
import com.kodemore.servlet.control.ScPageRoot;

import com.app.ui.page.admin.accountSettings.MyAccountSettingsPage;
import com.app.ui.page.admin.accountUsers.MyAccountUsersPage;
import com.app.ui.page.admin.userProfile.MyUserProfilePage;

public class MyAdminPage
    extends MyAbstractAdminPage
{
    //##################################################
    //# singleton
    //##################################################

    public static final MyAdminPage instance = new MyAdminPage();

    private MyAdminPage()
    {
        // singleton
    }

    //##################################################
    //# install
    //##################################################

    @Override
    protected void installRoot(ScPageRoot root)
    {
        root.css().gap();

        ScGroupArray groups;
        groups = root.addGroupArray();
        groups.style().width(150).height(200);

        ScGroup group;
        group = groups.addGroup("Admin");

        ScBox links;
        links = group.addLinkBox();
        links.addLink(MySelectAccountPage.instance);
        links.addLink(MyAccountSettingsPage.instance);
        links.addLink(MyAccountUsersPage.instance);

        group.addDivider();

        links = group.addLinkBox();
        links.addLink(MyUserProfilePage.instance);
    }
}
