//###############################################################
//###############################################################
//##
//##  AUTO GENERATED - DO NOT EDIT
//##
//###############################################################
//###############################################################

package com.app.model.base;

import com.app.model.MySettings;
import com.app.model.MySettingsValidator;
import com.app.model.core.MyDomainValidator;

import com.kodemore.collection.KmList;
import com.kodemore.exception.error.KmErrorIF;
import com.kodemore.validator.KmIntegerValidator;
import com.kodemore.validator.KmStringValidator;

/**
 * Validation rules for settings.
 */
public class MySettingsValidatorBase
    extends MyDomainValidator<MySettings>
{
    //##################################################
    //# static
    //##################################################

    public static final MySettingsValidator instance = new MySettingsValidator();

    //##################################################
    //# variables
    //##################################################

    private KmIntegerValidator codeValidator;
    private KmStringValidator someMessageValidator;
    private KmIntegerValidator lockVersionValidator;

    //##################################################
    //# constructor
    //##################################################

    protected MySettingsValidatorBase()
    {
        super();
        codeValidator = newCodeValidator();
        someMessageValidator = newSomeMessageValidator();
        lockVersionValidator = newLockVersionValidator();
    }

    //##################################################
    //# accessing
    //##################################################

    public KmIntegerValidator getCodeValidator()
    {
        return codeValidator;
    }

    public KmStringValidator getSomeMessageValidator()
    {
        return someMessageValidator;
    }

    public KmIntegerValidator getLockVersionValidator()
    {
        return lockVersionValidator;
    }

    //##################################################
    //# validate
    //##################################################

    @Override
    public void convertOnly(MySettings value)
    {
        value.setCode(codeValidator.convertOnly(value.getCode()));
        value.setSomeMessage(someMessageValidator.convertOnly(value.getSomeMessage()));
        value.setLockVersion(lockVersionValidator.convertOnly(value.getLockVersion()));
    }

    @Override
    public void validateOnly(MySettings value, KmList<KmErrorIF> errors)
    {
        codeValidator.validateOnly(value.getCode(), errors);
        someMessageValidator.validateOnly(value.getSomeMessage(), errors);
        lockVersionValidator.validateOnly(value.getLockVersion(), errors);
    }

    //##################################################
    //# instance creation
    //##################################################

    public KmIntegerValidator newCodeValidator()
    {
        KmIntegerValidator e;
        e = new KmIntegerValidator();
        e.setModel("settings");
        e.setField("code");
        e.setRequired();
        return e;
    }

    public KmStringValidator newSomeMessageValidator()
    {
        KmStringValidator e;
        e = new KmStringValidator();
        e.setMaximumLength(100);
        e.setAllowsPrintable(true);
        e.setModel("settings");
        e.setField("someMessage");
        return e;
    }

    public KmIntegerValidator newLockVersionValidator()
    {
        KmIntegerValidator e;
        e = new KmIntegerValidator();
        e.setModel("settings");
        e.setField("lockVersion");
        return e;
    }


}

