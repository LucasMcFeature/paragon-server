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

package com.kodemore.exception.error;

import com.kodemore.exception.KmApplicationException;
import com.kodemore.utility.Kmu;

/**
 * I am used to wrap exceptions so that they may be passed as unchecked
 * exceptions while still preserving the state of the original exception.
 */
public abstract class KmError
    implements KmErrorIF
{
    //##################################################
    //# code
    //##################################################

    @Override
    public String getCode()
    {
        return getClass().getName();
    }

    @Override
    public boolean hasCode(String s)
    {
        return Kmu.isEqual(getCode(), s);
    }

    //##################################################
    //# convenience
    //##################################################

    public void throwException()
    {
        throw new KmApplicationException(this);
    }

    @Override
    public boolean isConfirmation()
    {
        return false;
    }

    //##################################################
    //# format
    //##################################################

    @Override
    public String formatMessage()
    {
        return formatProblem();
    }

    @Override
    public String formatProblem()
    {
        StringBuilder sb = new StringBuilder();
        formatProblem(sb);
        return sb.toString();
    }

    public abstract void formatProblem(StringBuilder sb);

}
