package com.app.ui.activity.login;

import com.app.dao.base.MyDaoRegistry;
import com.app.model.MyAccount;
import com.app.model.MyEmail;
import com.app.model.MyInvitation;
import com.app.model.MyInvitationType;
import com.app.model.MyPasswordReset;
import com.app.model.MyUser;
import com.app.property.MyPropertyRegistry;
import com.app.utility.MyConstantsIF;
import com.app.utility.MyGlobals;
import com.app.utility.MyUrls;

import com.kodemore.html.KmHtmlBuilder;
import com.kodemore.utility.KmEmailParser;
import com.kodemore.utility.Kmu;

public class MyJoinAccountUtility
{
    //##################################################
    //# variables
    //##################################################

    private MyUser _user;

    //##################################################
    //# start
    //##################################################

    public void start(MyAccount account, String email, String roleCode)
    {
        MyUser user = getAccess().getUserDao().findEmail(email);

        boolean isNewUser = user == null;

        if ( !isNewUser )
            _user = user;

        sendJoinInvitation(email, account, isNewUser, roleCode);
    }

    private void sendJoinInvitation(
        String email,
        MyAccount account,
        boolean isNewUser,
        String roleCode)
    {
        MyPropertyRegistry p = getProperties();

        String accountName = account.getName();

        MyInvitation inv;
        inv = new MyInvitation();
        inv.setType(MyInvitationType.Join);
        inv.setAccount(account);
        inv.setEmail(email);
        inv.setRoleCode(roleCode);
        inv.saveDao();

        KmHtmlBuilder msg;

        if ( isNewUser )
            msg = formatNewUserMsg(email, account, inv);
        else
            if ( account.getAccountUserFor(_user) != null )
                msg = formatExistingAccountUserMsg(_user, account, inv);
            else
                msg = formatExistingUserMsg(_user, account, inv);

        String subject = Kmu.format("%s Join Account Invitation", accountName);

        MyEmail e;
        e = new MyEmail();
        e.setSubject(subject);
        e.addToRecipient(email);
        e.setFromAddress(p.getSendEmailFromAddress());
        e.addHtmlPart(msg.toString());
        e.markReady();
        e.saveDao();
    }

    private KmHtmlBuilder formatNewUserMsg(String email, MyAccount account, MyInvitation i)
    {
        KmEmailParser parser;
        parser = new KmEmailParser();
        parser.setEmail(email);

        String name;
        name = parser.getName();

        String accountName = account.getName();
        String app = MyConstantsIF.APPLICATION_NAME;

        KmHtmlBuilder msg;
        msg = new KmHtmlBuilder();
        msg.printfln("Hi %s", name);
        msg.printfln();
        msg.printf("Welcome to %s! ", app);
        msg.printf("You have been asked to join %s! ", accountName);
        msg.printfln();
        msg.printf("To join %s and to activate your new user account "
            + "click the following link.", accountName);
        msg.printfln();
        msg.printfln();
        msg.printLink(
            "Activate My account and join " + accountName + ".",
            MyUrls.getInvitationUrl(i));
        msg.printfln();
        return msg;
    }

    private KmHtmlBuilder formatExistingAccountUserMsg(
        MyUser user,
        MyAccount account,
        MyInvitation i)
    {
        MyPasswordReset a;
        a = new MyPasswordReset();
        a.setUser(user);
        a.saveDao();

        KmHtmlBuilder msg;
        msg = new KmHtmlBuilder();
        msg.printfln("Hi %s", user.getName());
        msg.printfln();
        msg.printf(
            "A request was made to join the email %s to the account %s. ",
            user.getEmail(),
            account);
        msg.printf("However, this email is already joined to the account. ");
        msg.printf("If you did not initiate this request, please ignore this email. ");
        msg.printf("If you are having difficulty accessing your account, you may use ");
        msg.printf("the link below to reset your password.");
        msg.printfln();
        msg.printfln();
        msg.printLink("Reset My Password", MyUrls.getPasswordResetUrl(a));
        msg.printfln();
        return msg;
    }

    private KmHtmlBuilder formatExistingUserMsg(MyUser user, MyAccount account, MyInvitation i)
    {
        String userName = user.getName();
        String accountName = account.getName();

        KmHtmlBuilder msg;
        msg = new KmHtmlBuilder();
        msg.printfln("Hi %s", userName);
        msg.printfln();
        msg.printf("You have been asked to join %s! ", accountName);
        msg.printfln();
        msg.printf("To join this account click the following link.");
        msg.printfln();
        msg.printfln();
        msg.printLink("Join " + accountName + ".", MyUrls.getInvitationUrl(i));
        msg.printfln();
        return msg;
    }

    //##################################################
    //# utility
    //##################################################

    protected MyDaoRegistry getAccess()
    {
        return MyGlobals.getAccess();
    }

    protected MyPropertyRegistry getProperties()
    {
        return MyGlobals.getProperties();
    }
}
