package com.kodemore.command;

import com.kodemore.string.KmStringBuilder;
import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;
import com.kodemore.utility.KmClock;
import com.kodemore.utility.KmContextFormatter;
import com.kodemore.utility.Kmu;

public abstract class KmCommand
{
    //##################################################
    //# variables
    //##################################################

    private String _context;

    //##################################################
    //# constructor
    //##################################################

    public KmCommand()
    {
        _context = KmContextFormatter.format();
    }

    //##################################################
    //# abstract
    //##################################################

    public abstract void run();

    //##################################################
    //# convenience
    //##################################################

    protected void error(String msg, Object... args)
    {
        Kmu.error(msg, args);
    }

    protected void fatal(String msg, Object... args)
    {
        Kmu.fatal(msg, args);
    }

    protected String getSimpleClassName()
    {
        return getClass().getSimpleName();
    }

    protected KmTimestamp getNowUtc()
    {
        return KmClock.getNowUtc();
    }

    protected KmDate getTodayUtc()
    {
        return getNowUtc().getDate();
    }

    //##################################################
    //# debug
    //##################################################

    public String getContext()
    {
        return _context;
    }

    public boolean hasContext()
    {
        return _context != null;
    }

    protected RuntimeException withContext(RuntimeException ex)
    {
        if ( !hasContext() )
            return ex;

        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("Attaching command context...");
        out.println("CONTEXT BEGIN");
        out.println(getContext());
        out.println("CONTEXT END");

        return new RuntimeException(out.toString(), ex);
    }
}
