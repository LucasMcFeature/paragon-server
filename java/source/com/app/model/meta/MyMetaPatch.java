//###############################################################
//###############################################################
//##
//##  AUTO GENERATED - DO NOT EDIT
//##
//###############################################################
//###############################################################

package com.app.model.meta;

import java.util.*;

import com.kodemore.collection.*;
import com.kodemore.exception.*;
import com.kodemore.meta.*;
import com.kodemore.servlet.encoder.*;
import com.kodemore.servlet.field.*;
import com.kodemore.time.*;
import com.kodemore.utility.*;
import com.kodemore.validator.*;

import com.app.model.*;
import com.app.model.core.*;
import com.app.utility.*;

public class MyMetaPatch
    extends KmMetaModel
{
    //##################################################
    //# singleton
    //##################################################

    public static final MyMetaPatch instance = new MyMetaPatch();

    //##################################################
    //# constructor
    //##################################################

    private MyMetaPatch()
    {
        // singleton
    }

    //##################################################
    //# accessing
    //##################################################

    @Override
    public String getName()
    {
        return "patch";
    }

    public static MyPatchValidator getValidator()
    {
        return MyPatchValidator.instance;
    }

    //##################################################
    //# fields and delegates
    //##################################################

    public static final MyMetaPatch_Name Name = new MyMetaPatch_Name();
    public static final MyMetaPatch_InstalledUtcTs InstalledUtcTs = new MyMetaPatch_InstalledUtcTs();
    public static final MyMetaPatch_Source Source = new MyMetaPatch_Source();
    public static final MyMetaPatch_InstalledLocalTs InstalledLocalTs = new MyMetaPatch_InstalledLocalTs();
    public static final MyMetaPatch_InstalledLocalTsMessage InstalledLocalTsMessage = new MyMetaPatch_InstalledLocalTsMessage();
    public static final MyMetaPatch_InstalledLocalDate InstalledLocalDate = new MyMetaPatch_InstalledLocalDate();
    public static final MyMetaPatch_InstalledLocalTime InstalledLocalTime = new MyMetaPatch_InstalledLocalTime();

    //##################################################
    //# associations
    //##################################################

}
