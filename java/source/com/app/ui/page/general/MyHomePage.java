package com.app.ui.page.general;

import com.kodemore.servlet.ScParameterList;
import com.kodemore.servlet.control.ScBorderLayout;
import com.kodemore.servlet.control.ScDiv;
import com.kodemore.servlet.control.ScGroup;
import com.kodemore.servlet.control.ScPageRoot;

import com.app.ui.layout.MyLeftMenuItem;
import com.app.ui.page.MyPage;

public class MyHomePage
    extends MyPage
{
    //##################################################
    //# singleton
    //##################################################

    public static final MyHomePage instance = new MyHomePage();

    private MyHomePage()
    {
        // singleton
    }

    //##################################################
    //# setup
    //##################################################

    @Override
    public MyLeftMenuItem getMenuItem()
    {
        return MyLeftMenuItem.home;
    }

    //##################################################
    //# navigation
    //##################################################

    public void push()
    {
        _push();
    }

    public String formatQueryString()
    {
        return _formatQueryString();
    }

    @Override
    public ScParameterList composeQueryParameters()
    {
        return null;
    }

    @Override
    public void applyQueryParameters(ScParameterList v)
    {
        // none
    }

    //##################################################
    //# install
    //##################################################

    @Override
    protected void installRoot(ScPageRoot root)
    {
        // todo_wyatt: 
        // String msg = "Welcome to " + MyConstantsIF.APPLICATION_NAME;

        root.css().fill();

        ScBorderLayout border;
        border = root.addBorderLayout();

        addTop(border);
        addCenter(border);
    }

    private void addTop(ScBorderLayout border)
    {
        ScDiv top;
        top = border.addTopPercent(50);
        top.css().pad();

        ScGroup group;
        group = top.addGroup("Top");
        group.css().fillPad();

        int n = 100;
        for ( int i = 0; i < n; i++ )
            group.addTextSpan("top line " + i).css().block();
    }

    private void addCenter(ScBorderLayout border)
    {
        ScDiv div;
        div = border.addCenter();
        div.css().pad();

        ScGroup group;
        group = div.addGroup("Center");
        group.css().fillPad();
        group.addPad();

        int n = 100;
        for ( int i = 0; i < n; i++ )
            group.addTextSpan("center line " + i).css().block();
    }

}
