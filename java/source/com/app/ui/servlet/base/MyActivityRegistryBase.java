//###############################################################
//###############################################################
//##
//##  AUTO GENERATED - DO NOT EDIT
//##
//###############################################################
//###############################################################

package com.app.ui.servlet.base;

import com.app.ui.activity.admin.MyAdminMenuPage;
import com.app.ui.activity.admin.MyEmailEditPage;
import com.app.ui.activity.admin.MyEmailListPage;
import com.app.ui.activity.admin.MyEmailViewPage;
import com.app.ui.activity.admin.MyProfilePage;
import com.app.ui.activity.admin.MyUsersPage;
import com.app.ui.activity.admin.accounts.MyAccounts2Page;
import com.app.ui.activity.admin.accounts.MyAccountsPage;
import com.app.ui.activity.general.MyHomePage;
import com.app.ui.activity.general.MySignOutPage;
import com.app.ui.activity.general.MyTimeoutPage;
import com.app.ui.activity.login.MyHandleJoinInvitationActivity;
import com.app.ui.activity.login.MyHandleNewUserInvitationActivity;
import com.app.ui.activity.login.MyHandlePasswordResetActivity;
import com.app.ui.activity.login.MyHandleTransferInvitationActivity;
import com.app.ui.activity.login.MyInvalidInvitationPage;
import com.app.ui.activity.login.MySignInActivity;
import com.app.ui.activity.test.MyAccordionTestPage;
import com.app.ui.activity.test.MyAnimationTestPage;
import com.app.ui.activity.test.MyAutoCompleteTestPage;
import com.app.ui.activity.test.MyBarcodeTestPage;
import com.app.ui.activity.test.MyBlankTestPage;
import com.app.ui.activity.test.MyBlockTestPage;
import com.app.ui.activity.test.MyBorderTestPage;
import com.app.ui.activity.test.MyCardFlipTestPage;
import com.app.ui.activity.test.MyColorFieldTestPage;
import com.app.ui.activity.test.MyDateFieldTestPage;
import com.app.ui.activity.test.MyDomainDropdownSetValueTestPage;
import com.app.ui.activity.test.MyDownloadTestPage;
import com.app.ui.activity.test.MyDragScrollToTestPage;
import com.app.ui.activity.test.MyDropzoneTestPage;
import com.app.ui.activity.test.MyEqualizeTestPage;
import com.app.ui.activity.test.MyFieldTestPage;
import com.app.ui.activity.test.MyFilterTableRowTestPage;
import com.app.ui.activity.test.MyFormTestPage;
import com.app.ui.activity.test.MyGmailTestPage;
import com.app.ui.activity.test.MyGoogleChartTestPage;
import com.app.ui.activity.test.MyGradientTestPage;
import com.app.ui.activity.test.MyGridTestPage;
import com.app.ui.activity.test.MyGroupIconHeaderTestPage;
import com.app.ui.activity.test.MyGroupTestPage;
import com.app.ui.activity.test.MyHandleSortTestPage;
import com.app.ui.activity.test.MyHideErrorsTestPage;
import com.app.ui.activity.test.MyHoverTestPage;
import com.app.ui.activity.test.MyLocalValueTestPage;
import com.app.ui.activity.test.MyMemoryLeakTestPage;
import com.app.ui.activity.test.MyNotebookTestPage;
import com.app.ui.activity.test.MyOpenWindowTestPage;
import com.app.ui.activity.test.MyPaddingTestPage;
import com.app.ui.activity.test.MyPlaceholderTestPage;
import com.app.ui.activity.test.MyPopupMenuTestPage;
import com.app.ui.activity.test.MyRadioButtonTestPage;
import com.app.ui.activity.test.MyScriptTestPage;
import com.app.ui.activity.test.MyShowDialogTestPage;
import com.app.ui.activity.test.MySlowTestPage;
import com.app.ui.activity.test.MyStaticIncludeTestPage;
import com.app.ui.activity.test.MyTabbedTestPage;
import com.app.ui.activity.test.MyTestMenuPage;
import com.app.ui.activity.test.MyToastTestPage;
import com.app.ui.activity.tools.MyApplicationPropertiesPage;
import com.app.ui.activity.tools.MyBeanShellPage;
import com.app.ui.activity.tools.MyPerformanceLogPage;
import com.app.ui.activity.tools.MySharedFileBrowserPage;
import com.app.ui.activity.tools.MySqlPage;
import com.app.ui.activity.tools.MySystemLogListPage;
import com.app.ui.activity.tools.MySystemPropertiesPage;
import com.app.ui.activity.tools.MyToolsMenuPage;
import com.app.ui.activity.tools.MyUtilityPage;
import com.app.ui.activity.user.MyUserPasswordPage;
import com.app.ui.activity.user.MyUserSettingsPage;

import com.kodemore.servlet.ScActivityRegistry;

public abstract class MyActivityRegistryBase
    extends ScActivityRegistry
{
    @Override
    protected void registerActivities()
    {
        super.registerActivities();
        
        add(MyAccordionTestPage.instance);
        add(MyAccounts2Page.instance);
        add(MyAccountsPage.instance);
        add(MyAdminMenuPage.instance);
        add(MyAnimationTestPage.instance);
        add(MyApplicationPropertiesPage.instance);
        add(MyAutoCompleteTestPage.instance);
        add(MyBarcodeTestPage.instance);
        add(MyBeanShellPage.instance);
        add(MyBlankTestPage.instance);
        add(MyBlockTestPage.instance);
        add(MyBorderTestPage.instance);
        add(MyCardFlipTestPage.instance);
        add(MyColorFieldTestPage.instance);
        add(MyDateFieldTestPage.instance);
        add(MyDomainDropdownSetValueTestPage.instance);
        add(MyDownloadTestPage.instance);
        add(MyDragScrollToTestPage.instance);
        add(MyDropzoneTestPage.instance);
        add(MyEmailEditPage.instance);
        add(MyEmailListPage.instance);
        add(MyEmailViewPage.instance);
        add(MyEqualizeTestPage.instance);
        add(MyFieldTestPage.instance);
        add(MyFilterTableRowTestPage.instance);
        add(MyFormTestPage.instance);
        add(MyGmailTestPage.instance);
        add(MyGoogleChartTestPage.instance);
        add(MyGradientTestPage.instance);
        add(MyGridTestPage.instance);
        add(MyGroupIconHeaderTestPage.instance);
        add(MyGroupTestPage.instance);
        add(MyHandleJoinInvitationActivity.instance);
        add(MyHandleNewUserInvitationActivity.instance);
        add(MyHandlePasswordResetActivity.instance);
        add(MyHandleSortTestPage.instance);
        add(MyHandleTransferInvitationActivity.instance);
        add(MyHideErrorsTestPage.instance);
        add(MyHomePage.instance);
        add(MyHoverTestPage.instance);
        add(MyInvalidInvitationPage.instance);
        add(MyLocalValueTestPage.instance);
        add(MyMemoryLeakTestPage.instance);
        add(MyNotebookTestPage.instance);
        add(MyOpenWindowTestPage.instance);
        add(MyPaddingTestPage.instance);
        add(MyPerformanceLogPage.instance);
        add(MyPlaceholderTestPage.instance);
        add(MyPopupMenuTestPage.instance);
        add(MyProfilePage.instance);
        add(MyRadioButtonTestPage.instance);
        add(MyScriptTestPage.instance);
        add(MySharedFileBrowserPage.instance);
        add(MyShowDialogTestPage.instance);
        add(MySignInActivity.instance);
        add(MySignOutPage.instance);
        add(MySlowTestPage.instance);
        add(MySqlPage.instance);
        add(MyStaticIncludeTestPage.instance);
        add(MySystemLogListPage.instance);
        add(MySystemPropertiesPage.instance);
        add(MyTabbedTestPage.instance);
        add(MyTestMenuPage.instance);
        add(MyTimeoutPage.instance);
        add(MyToastTestPage.instance);
        add(MyToolsMenuPage.instance);
        add(MyUserPasswordPage.instance);
        add(MyUserSettingsPage.instance);
        add(MyUsersPage.instance);
        add(MyUtilityPage.instance);
    }
}
