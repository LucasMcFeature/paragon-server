package com.app.ui.activity.login;

import com.app.dao.base.MyDaoRegistry;
import com.app.model.MyAccount;
import com.app.model.MyEmail;
import com.app.model.MyInvitation;
import com.app.model.MyUser;
import com.app.property.MyPropertyRegistry;
import com.app.utility.MyConstantsIF;
import com.app.utility.MyGlobals;
import com.app.utility.MyUrls;

import com.kodemore.html.KmHtmlBuilder;
import com.kodemore.utility.KmEmailParser;
import com.kodemore.utility.Kmu;

public class MyTransferAccountActivity
{

    //##################################################
    //# start
    //##################################################

    /**ask_valerie 
     * about static methods
     */
    public void start(MyAccount account, String email)
    {
        MyUser user = getAccess().getUserDao().findEmail(email);

        if ( user == null )
        {
            user = createUser(email);
            sendTransferNewUserInvitation(user, account);
        }
        else
            sendTransferExistingUserInvitation(user, account);

        showSentMessage(email);
    }

    private MyUser createUser(String email)
    {
        KmEmailParser p;
        p = new KmEmailParser();
        p.setEmail(email);

        String name;
        name = p.getName();

        MyUser u;
        u = new MyUser();
        u.setName(name);
        u.setEmail(email);
        u.saveDao();

        return u;
    }

    private void sendTransferNewUserInvitation(MyUser user, MyAccount account)
    {
        MyPropertyRegistry p = getProperties();

        String userName = user.getName();
        String email = user.getEmail();
        String accountName = account.getName();
        String app = MyConstantsIF.APPLICATION_NAME;

        MyInvitation i;
        i = new MyInvitation();
        i.setUser(user);
        i.saveDao();

        KmHtmlBuilder msg;
        msg = new KmHtmlBuilder();
        msg.printfln("Hi %s", userName);
        msg.printfln();
        msg.printf("Welcome to %s! ", app);
        msg.printf("A new user account has been created for the email %s. ", email);
        msg.printf("You have been asked to acquire the account %s. ", accountName);
        msg.printfln();
        msg.printf("To take ownership of the new account and to activate your new user account "
            + "click the following link.");
        msg.printfln();
        msg.printfln();
        msg.printLink("Activate My Account and Take Ownership.", MyUrls.getInvitationUrl(i));
        msg.printfln();

        String subject = Kmu.format("%s Account Transfer Invitation", app);

        MyEmail e;
        e = new MyEmail();
        e.setSubject(subject);
        e.addToRecipient(email);
        e.setFromAddress(p.getSendEmailFromAddress());
        e.addHtmlPart(msg.toString());
        e.markReady();
        e.saveDao();
    }

    private void sendTransferExistingUserInvitation(MyUser user, MyAccount account)
    {
        MyPropertyRegistry p = getProperties();

        String userName = user.getName();
        String email = user.getEmail();
        String accountName = account.getName();
        String app = MyConstantsIF.APPLICATION_NAME;

        MyInvitation i;
        i = new MyInvitation();
        i.setUser(user);
        i.saveDao();

        KmHtmlBuilder msg;
        msg = new KmHtmlBuilder();
        msg.printfln("Hi %s", userName);
        msg.printfln();
        msg.printf("You have been asked to acquire the account %s. ", accountName);
        msg.printfln();
        msg.printf("To take ownership of this account click the following link.");
        msg.printfln();
        msg.printfln();
        msg.printLink("Take Ownership.", MyUrls.getInvitationUrl(i));
        msg.printfln();

        String subject = Kmu.format("%s Account Transfer Invitation", app);

        MyEmail e;
        e = new MyEmail();
        e.setSubject(subject);
        e.addToRecipient(email);
        e.setFromAddress(p.getSendEmailFromAddress());
        e.addHtmlPart(msg.toString());
        e.markReady();
        e.saveDao();
    }

    private void showSentMessage(String email)
    {
        KmHtmlBuilder out;
        out = new KmHtmlBuilder();

        out.println("Your request has been sent to:");
        out.println();

        out.beginDivCss("indent");
        out.printBold(email);
        out.endDiv();

        //        MyManageAccountsPage.clearTransferFrame();
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