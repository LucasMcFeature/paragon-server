/*
  Copyright (c) 2005-2013 www.kodemore.com

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

package com.kodemore.servlet.encoder.coder;

import com.kodemore.servlet.action.ScActionIF;
import com.kodemore.servlet.encoder.ScDecoder;
import com.kodemore.servlet.encoder.ScEncoder;
import com.kodemore.servlet.utility.ScControlRegistry;

public class ScActionCoder
    extends ScAbstractCoder
{
    @Override
    public String getKey()
    {
        return CODER_ACTION;
    }

    @Override
    public boolean matches(Object e)
    {
        return e instanceof ScActionIF;
    }

    @Override
    public void encode(ScEncoder encoder, Object o)
    {
        ScActionIF e = (ScActionIF)o;
        encoder._print(e.getKey());
    }

    @Override
    public Object decode(ScDecoder decoder, String s)
    {
        return ScControlRegistry.getInstance().getAction(s);
    }

}
