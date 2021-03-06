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

package com.kodemore.telnet.sample;

import com.kodemore.telnet.KmTelnetForm;

public class KmTelnetSampleMenuForm
    extends KmTelnetForm
{
    //##################################################
    //# constructor
    //##################################################

    public KmTelnetSampleMenuForm()
    {
        addLabel(1, 1, "Sample Menu");
        addLabel(1, 3, "1) Edit Account");
        addLabel(1, 4, "2) Edit Address");
        addLabel(1, 5, "3) Logout");
        addField(1, 7, 1);
    }

    @Override
    public void handleVirtualKey(int key, boolean alt)
    {
        super.handleVirtualKey(key, alt);
        switch ( key )
        {
            case VK_F1:
                handleEditAccount();
                break;
            case VK_F2:
                handleEditAddress();
                break;
            case VK_F3:
                handleLogout();
                break;
        }
    }

    public void handleEditAccount()
    {
        show(new KmTelnetSampleEditAccountForm());
    }

    public void handleEditAddress()
    {
        show(new KmTelnetSampleEditAddressForm());
    }

    public void handleLogout()
    {
        throw new RuntimeException("Logout, now what?");
    }

    @Override
    public void updateView()
    {
        // allow subclass extension
    }

}
