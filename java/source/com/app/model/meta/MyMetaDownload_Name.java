//###############################################################
//###############################################################
//##
//##  AUTO GENERATED - DO NOT EDIT
//##
//###############################################################
//###############################################################

package com.app.model.meta;

import com.app.dao.MyDownloadDao;
import com.app.dao.base.MyDaoRegistry;
import com.app.model.MyDownload;
import com.app.model.MyDownloadValidator;
import com.app.utility.MyGlobals;

import com.kodemore.comparator.KmCompareUtility;
import com.kodemore.meta.KmMetaDaoPropertyIF;
import com.kodemore.meta.KmMetaStringProperty;
import com.kodemore.validator.KmStringValidator;

public class MyMetaDownload_Name
    extends KmMetaStringProperty<MyDownload>
    implements KmMetaDaoPropertyIF<MyDownload,String>
{
    //##################################################
    //# accessing
    //##################################################

    @Override
    public String getName()
    {
        return "name";
    }

    @Override
    public String getLabel()
    {
        return "Name";
    }

    @Override
    public int getColumnWidth()
    {
        return 15;
    }

    @Override
    public boolean isEditable()
    {
        return true;
    }

    @Override
    public KmStringValidator getValidator()
    {
        return MyDownloadValidator.instance.getNameValidator();
    }

    //##################################################
    //# dao
    //##################################################

    @Override
    public String getDaoPropertyName()
    {
        return "name";
    }

    @Override
    public MyDownloadDao getDao()
    {
        return getAccess().getDownloadDao();
    }

    private MyDaoRegistry getAccess()
    {
        return MyGlobals.getAccess();
    }
    
    //##################################################
    //# value
    //##################################################

    @Override
    public String getValueFor(MyDownload model)
    {
        return model.getName();
    }
    
    @Override
    public void setValueFor(MyDownload model, String value)
    {
        model.setName(value);
    }
    
    @Override
    public boolean hasValueFor(MyDownload model, String value)
    {
        return model.hasName(value);
    }
    
    @Override
    public int compareValues(MyDownload o1, MyDownload o2, boolean nullsOnTop)
    {
        return KmCompareUtility.compare(getValueFor(o1), getValueFor(o2), nullsOnTop);    
    }
    
}
