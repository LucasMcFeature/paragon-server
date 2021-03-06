/*
  Copyright (c) 2005-2014 www.kodemore.com

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

package com.kodemore.servlet.control;

import java.util.Iterator;

import com.kodemore.collection.KmCompositeIterator;
import com.kodemore.collection.KmList;
import com.kodemore.html.KmHtmlBuilder;

/**
 * I am used to create a layout similar to the traditional java AWT border layout.
 * 
 * I can be used to layout elements that "stick" to the sides of the parent with a 
 * center portion that fills any remaining space.  Each element is added inside
 * the elements previously added.
 * 
 * In HTML terms, I create <div> elements with position:absolute.
 * 
 * NOTE:
 *      I do not create an element of my own. 
 *      My children are added directly to my parent.\
 *      My parent must have a non-"static" position.  (e.g.: relative).
 *      
 *      Children use the alternate css size calcuations via "box-sizing:border-box".
 *      This allows you to add borders and padding to the children without breaking the layout.
 *
 *      You can only add one "center" panel, and it must be the last panel that you add.
 *      
 *      You can use either pixel or percent based sizes for the sides.
 *      You can use pixels for one side, and percents for another side.
 *      However, you must use the SAME mode if you add multiple elements to the SAME side.
 *      E.g.: 
 *          This is ok... addLeft(10), addTopPercent(50).
 *          This is ok... addLeft(10), addLeft(50).
 *          This will break... addLeft(10), addLeftPercent(50).
 *          
 *      If you need to mix pixel and percents for the same side, then you need to create
 *      multiple nested layouts.
 *      
 *          root.css().relative();
 *          
 *          outer = root.addBorderLayout();
 *          outerLeft = outer.addLeft(10);
 *          outerCenter = outer.addCenter();
 *
 *          inner = outerCenter.addBorderLayout();
 *          innerLeft = inner.addLeftPercent(50);
 *          innerCenter = inner.addCenter();
 */
public class ScBorderLayout
    extends ScControl
{
    //##################################################
    //# variables
    //##################################################

    private KmList<ScControl> _children;

    private int               _leftPixel;
    private int               _leftPercent;

    private int               _rightPixel;
    private int               _rightPercent;

    private int               _topPixel;
    private int               _topPercent;

    private int               _bottomPixel;
    private int               _bottomPercent;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void install()
    {
        super.install();

        _children = new KmList<ScControl>();
    }

    //##################################################
    //# accessing
    //##################################################

    public KmList<ScControl> getChildren()
    {
        return _children;
    }

    //##################################################
    //# left
    //##################################################

    public ScDiv addLeft(int px)
    {
        ScDiv e;
        e = addChild();
        e.style().width(px);

        applyTop(e);
        applyBottom(e);
        applyLeft(e);

        _leftPixel += px;
        return e;
    }

    public ScDiv addLeftPercent(int pct)
    {
        ScDiv e;
        e = addChild();
        e.style().widthPercent(pct);

        applyTop(e);
        applyBottom(e);
        applyLeft(e);

        _leftPercent += pct;
        return e;
    }

    //##################################################
    //# right
    //##################################################

    public ScDiv addRight(int px)
    {
        ScDiv e;
        e = addChild();
        e.style().width(px);

        applyTop(e);
        applyBottom(e);
        applyRight(e);

        _rightPixel += px;
        return e;
    }

    public ScDiv addRightPercent(int pct)
    {
        ScDiv e;
        e = addChild();
        e.style().widthPercent(pct);

        applyTop(e);
        applyBottom(e);
        applyRight(e);

        _rightPercent += pct;
        return e;
    }

    //##################################################
    //# top
    //##################################################

    public ScDiv addTop(int px)
    {
        ScDiv e;
        e = addChild();
        e.style().height(px);

        applyLeft(e);
        applyRight(e);
        applyTop(e);

        _topPixel += px;
        return e;
    }

    public ScDiv addTopPercent(int pct)
    {
        ScDiv e;
        e = addChild();
        e.style().heightPercent(pct);

        applyLeft(e);
        applyRight(e);
        applyTop(e);

        _topPercent += pct;
        return e;
    }

    //##################################################
    //# bottom
    //##################################################

    public ScDiv addBottom(int px)
    {
        ScDiv e;
        e = addChild();
        e.style().height(px);

        applyLeft(e);
        applyRight(e);
        applyBottom(e);

        _bottomPixel += px;
        return e;
    }

    public ScDiv addBottomPercent(int pct)
    {
        ScDiv e;
        e = addChild();
        e.style().heightPercent(pct);

        applyLeft(e);
        applyRight(e);
        applyBottom(e);

        _bottomPercent += pct;
        return e;
    }

    //##################################################
    //# center
    //##################################################

    public ScDiv addCenter()
    {
        ScDiv e = addChild();

        applyLeft(e);
        applyRight(e);
        applyTop(e);
        applyBottom(e);

        return e;
    }

    //##################################################
    //# apply
    //##################################################

    private void applyLeft(ScDiv e)
    {
        if ( _leftPercent > 0 )
            e.style().leftPercent(_leftPercent);
        else
            e.style().left(_leftPixel);
    }

    private void applyRight(ScDiv e)
    {
        if ( _rightPercent > 0 )
            e.style().rightPercent(_rightPercent);
        else
            e.style().right(_rightPixel);
    }

    private void applyTop(ScDiv e)
    {
        if ( _topPercent > 0 )
            e.style().topPercent(_topPercent);
        else
            e.style().top(_topPixel);
    }

    private void applyBottom(ScDiv e)
    {
        if ( _bottomPercent > 0 )
            e.style().bottomPercent(_bottomPercent);
        else
            e.style().bottom(_bottomPixel);
    }

    //##################################################
    //# render
    //##################################################

    @Override
    protected void renderControlOn(KmHtmlBuilder out)
    {
        Iterator<ScControl> i = getChildren().reverseIterator();
        while ( i.hasNext() )
            i.next().renderOn(out);
    }

    //##################################################
    //# components
    //##################################################

    @Override
    public Iterator<ScControl> getComponents()
    {
        KmCompositeIterator<ScControl> i;
        i = new KmCompositeIterator<ScControl>();

        i.addAll(super.getComponents());
        i.addAll(getChildren());

        return i;
    }

    //##################################################
    //# support
    //##################################################

    private ScDiv addChild()
    {
        ScDiv e;
        e = new ScDiv();
        e.css().absolute().boxSizingBorder().auto();

        _children.add(e);

        return e;
    }
}
