package com.app.ui.activity.test;

import com.kodemore.servlet.action.ScAction;
import com.kodemore.servlet.action.ScActionIF;
import com.kodemore.servlet.control.ScButton;
import com.kodemore.servlet.control.ScDiv;
import com.kodemore.servlet.control.ScForm;
import com.kodemore.servlet.control.ScGroup;
import com.kodemore.servlet.control.ScImage;
import com.kodemore.servlet.control.ScPageRoot;
import com.kodemore.servlet.field.ScTextField;

public class MyHoverTestPage
    extends MyTestPage
{
    //##################################################
    //# singleton
    //##################################################

    public static final MyHoverTestPage instance = new MyHoverTestPage();

    private MyHoverTestPage()
    {
        // singleton
    }

    //##################################################
    //# variables
    //##################################################

    private ScTextField _nameField;
    private ScForm      _form;

    //##################################################
    //# install
    //##################################################

    @Override
    protected void installRoot(ScPageRoot root)
    {
        _nameField = new ScTextField();
        _nameField.setLabel("Name");
        _nameField.setTitle("Please enter a name here.");
        _nameField.css().padLeft5();

        _form = root.addForm();
        _form.css().gap();
        _form.setTitle("This is text that shows when hovering over the entire form.");

        ScGroup group;
        group = _form.addGroup("Hover/Title Test");

        ScDiv left;
        left = group.getHeader().addFloatLeft();
        left.css().pad5();

        ScImage image;
        image = left.addImage();
        image.setSource(getCommonImageUrl("smiley.png"));
        image.setTitle("Smile!");

        group.addPad().addText(
            "Show hover text over the icon, form, field, and button using the title attribute");
        group.addPad().addFields().add(_nameField);
        group.addDivider();

        ScButton button;
        button = group.addButtonBox().addButton("Clear", newClearAction());
        button.setTitle("This button is used for clearing the field.");
    }

    //##################################################
    //# start
    //##################################################

    @Override
    public void start()
    {
        super.start();
        initializeTooltip();
    }

    private void initializeTooltip()
    {
        _form.ajax().tooltip();
    }

    //##################################################
    //# actions
    //##################################################

    private ScActionIF newClearAction()
    {
        return new ScAction(this)
        {
            @Override
            protected void handle()
            {
                handleClear();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleClear()
    {
        _nameField.clearText();
        _nameField.ajaxUpdateValue();
    }
}
