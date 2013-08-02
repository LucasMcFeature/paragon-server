package com.app.ui.activity.test;

import com.kodemore.servlet.action.ScAction;
import com.kodemore.servlet.action.ScActionIF;
import com.kodemore.servlet.control.ScBox;
import com.kodemore.servlet.control.ScControl;
import com.kodemore.servlet.control.ScForm;
import com.kodemore.servlet.control.ScGroup;
import com.kodemore.servlet.field.ScTextField;

import com.app.ui.activity.MyActivity;

public class MyPlaceholderTestPage
    extends MyAbstractTestPage
{
    //##################################################
    //# singleton
    //##################################################

    public static final MyActivity instance = new MyPlaceholderTestPage();

    private MyPlaceholderTestPage()
    {
        // singleton
    }

    //##################################################
    //# variables
    //##################################################

    private ScTextField _textField;

    //##################################################
    //# install
    //##################################################

    @Override
    protected ScControl installRoot()
    {
        _textField = new ScTextField();
        _textField.setLabel("Field");
        _textField.setPlaceholder("Enter a name");
        _textField.css().padLeft5();

        ScForm root;
        root = new ScForm();
        root.setDefaultAction(newSubmitAction());
        root.css().padSpaced();

        ScGroup group;
        group = root.addGroup("Placeholder Test");

        ScBox box;
        box = group.addPadSpaced();
        box.addBox().addText(
            "The following field should show a 'placeholder' hint inside the field...");
        box.addFields().add(_textField);
        group.addDivider();
        group.addButtonBox().addSubmitButton();

        return root;
    }

    //##################################################
    //# actions
    //##################################################

    private ScActionIF newSubmitAction()
    {
        return new ScAction(this)
        {
            @Override
            protected void handle()
            {
                handleOk();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleOk()
    {
        String s = _textField.getValue();
        if ( s == null )
            s = "<null>";

        ajax().toast(s);
    }

}