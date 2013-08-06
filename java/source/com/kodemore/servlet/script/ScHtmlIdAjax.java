/*
  Copyright (c) 2005-2011 www.kodemore.com

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package com.kodemore.servlet.script;

import com.kodemore.html.KmHtmlBuilder;
import com.kodemore.html.cssBuilder.KmCssDefaultConstantsIF;
import com.kodemore.json.KmJsonObject;
import com.kodemore.servlet.action.ScActionIF;
import com.kodemore.servlet.control.ScControl;
import com.kodemore.servlet.field.ScHtmlIdIF;
import com.kodemore.utility.Kmu;

/**
 * A block script that knows about a specific element.
 * This allows for a variety of concise convenience methods.
 */
public class ScHtmlIdAjax
    extends ScAjaxWrapper
{
    //##################################################
    //# variables
    //##################################################

    private ScHtmlIdIF _target;

    //##################################################
    //# constructor
    //##################################################

    public ScHtmlIdAjax(ScHtmlIdIF e)
    {
        _target = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public ScHtmlIdIF getTarget()
    {
        return _target;
    }

    public String formatJquerySelector()
    {
        return getTarget().formatJquerySelector();
    }

    public String formatJqueryReference()
    {
        return getTarget().formatJqueryReference();
    }

    //##################################################
    //# layout
    //##################################################

    public void printMain()
    {
        ajax().printMain(getTarget());
    }

    //##################################################
    //# visible
    //##################################################

    public ScShowScript show()
    {
        return ajax().show(getTarget());
    }

    public ScHideScript hide()
    {
        return ajax().hide(getTarget());
    }

    public ScToggleScript toggle()
    {
        return ajax().toggle(getTarget());
    }

    //##################################################
    //# contents
    //##################################################

    public void setContents(ScControl contents)
    {
        ajax().setContents(getTarget(), contents);
    }

    public void setContents(KmHtmlBuilder out)
    {
        ajax().setContents(getTarget(), out);
    }

    public void clearContents()
    {
        ajax().clearContents(getTarget());
    }

    public ScAddContentScript addContents()
    {
        return ajax().addContentsTo(getTarget());
    }

    //##################################################
    //# replace
    //##################################################

    public void replace()
    {
        ajax().replace(getTarget());
    }

    //##################################################
    //# text
    //##################################################

    public void setText(String e)
    {
        ajax().setText(getTarget(), e);
    }

    public void clearText()
    {
        ajax().clearText(getTarget());
    }

    //##################################################
    //# html
    //##################################################

    public void setHtml(String value)
    {
        ajax().setHtml(getTarget(), value);
    }

    public void clearHtml(ScHtmlIdIF target)
    {
        ajax().clearHtml(getTarget());
    }

    //##################################################
    //# misc
    //##################################################

    public void hideAllErrors()
    {
        String target = formatJquerySelector();
        String error = KmCssDefaultConstantsIF.error;

        run("$('%s .%s').hide();", target, error);
    }

    public void focus()
    {
        ajax().focus(getTarget());
    }

    //##################################################
    //# value
    //##################################################

    public void setValue(String e)
    {
        ajax().setValue(getTarget(), e);
    }

    public void clearValue()
    {
        ajax().clearValue(getTarget());
    }

    //##################################################
    //# stack
    //##################################################

    public void pop()
    {
        ajax().pop();
    }

    public void pushDefer()
    {
        ajax().pushDeferUntil(getTarget());
    }

    public void defer()
    {
        ajax().deferUntil(getTarget());
    }

    //##################################################
    //# on escape
    //##################################################

    public void onEscape(ScActionIF action)
    {
        ajax().onEscape(getTarget(), action);
    }

    public void onEscape(String script)
    {
        ajax().onEscape(getTarget(), script);
    }

    public void onEscape(ScScriptIF script)
    {
        ajax().onEscape(getTarget(), script);
    }

    //##################################################
    //# on control-enter
    //##################################################

    public void onControlEnter(ScScriptIF script)
    {
        ajax().onControlEnter(getTarget(), script);
    }

    public void onControlEnter(ScHtmlIdIF target, String e)
    {
        ajax().onControlEnter(getTarget(), e);
    }

    public void onControlEnter(ScHtmlIdIF target, ScActionIF e)
    {
        ajax().onControlEnter(getTarget(), e);
    }

    //##################################################
    //# block
    //##################################################

    public void block()
    {
        ajax().blockControl(getTarget());
    }

    public void unblock()
    {
        ajax().unblockControl(getTarget());
    }

    //##################################################
    //# hack
    //##################################################

    // fixme_wyatt: toast kludge 
    public void toast(String msg, Object... args)
    {
        ajax().toast(msg, args);
    }

    //##################################################
    //# equalize children
    //##################################################

    public void equalizeChildren()
    {
        equalizeChildren(true);
    }

    public void equalizeChildren(boolean reset)
    {
        equalizeChildrenWidth(reset);
        equalizeChildrenHeight(reset);
    }

    public void equalizeChildrenHeight(boolean reset)
    {
        run(equalizeHeightScript(reset));
    }

    public void equalizeChildrenWidth(boolean reset)
    {
        run(equalizeWidthScript(reset));
    }

    /**
     *  review_wyatt: (aaron) It seems that the Equalize tool does not actually support modes
     *  for inner and outer height and width, even though it claims to.  The readme files says
     *  you can pass a string in instead of the options object like so: 
     *  $('.parent').equalize('outerHeight');
     *  $('.parent').equalize('innerHeight');
     *  
     *  however, when implemented like that (as seen commented out below) the tool no 
     *  longer functions properly, and whenever equalize is called, the elements are reduced to 
     *  a single pixel.
     *  
     *  Alternatively I tried setting the value of the "equalize" parameter to outerHeight or
     *  innerHeight, but this had the same effect as above, reducing the items to a signle
     *  pixel.
     */
    private String equalizeHeightScript(boolean reset)
    {
        KmJsonObject options;
        options = new KmJsonObject();
        // review_aaron: this doesn't work
        //        options.setString("equalize", "outerHeight");
        options.setString("equalize", "height");
        options.setBoolean("reset", reset);

        // review_aaron: this doesn't work
        //                return Kmu.format("%s.equalize(%s);", formatJqueryReference(), "'outerHeight'");
        return Kmu.format("%s.equalize(%s);", formatJqueryReference(), options);
    }

    private String equalizeWidthScript(boolean reset)
    {
        KmJsonObject options;
        options = new KmJsonObject();
        // review_aaron: this doesn't work
        //        options.setString("equalize", "outerWidth");
        options.setString("equalize", "width");
        options.setBoolean("reset", reset);

        // review_aaron: this doesn't work
        //        return Kmu.format("%s.equalize(%s);", formatJqueryReference(), "'outerWidth'");
        return Kmu.format("%s.equalize(%s);", formatJqueryReference(), options);
    }
}
