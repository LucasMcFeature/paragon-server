//###############################################################
//###############################################################
//##
//##  AUTO GENERATED - DO NOT EDIT
//##
//###############################################################
//###############################################################

package com.app.model.base;

import java.util.*;

import com.kodemore.collection.*;
import com.kodemore.exception.*;
import com.kodemore.servlet.action.*;
import com.kodemore.servlet.control.*;
import com.kodemore.servlet.field.*;
import com.kodemore.time.*;
import com.kodemore.utility.*;
import com.kodemore.validator.*;

import com.app.filter.*;
import com.app.finder.*;
import com.app.model.*;
import com.app.model.core.*;
import com.app.model.meta.*;
import com.app.utility.*;

/**
 * Miscellaneous tools for use with the model.
 */
public abstract class MySettingsToolsBase
    extends MyAbstractDomainTools
{
    public static final MyMetaSettings Meta = MySettings.Meta;

    public ScDomainDropdownField<MySettings,Integer> newDomainDropdown()
    {
        ScDomainDropdownField<MySettings,Integer> e;
        e = new ScDomainDropdownField<MySettings,Integer>();
        e.setLabel(Meta.getLabel());
        e.setFilter(new MySettingsFilter());
        e.setFinder(new MySettingsFinder());
        e.setOptionKeyAdaptor(Meta.Code);
        return e;
    }
}
