package com.kodemore.generator.property;

import com.kodemore.collection.KmList;
import com.kodemore.generator.KmgElement;
import com.kodemore.stf.KmStfElement;
import com.kodemore.stf.KmStfParser;
import com.kodemore.stf.KmStfRoot;

public class KmgPropertyFile
    extends KmgElement
{
    //##################################################
    //# variables
    //##################################################

    private KmList<KmgPropertyGroup> _groups;
    private KmList<String>           _validFlags;

    //##################################################
    //# constructor
    //##################################################

    public KmgPropertyFile(KmgElement parent)
    {
        super(parent);
        _groups = new KmList<KmgPropertyGroup>();
        _validFlags = new KmList<String>();
    }

    //##################################################
    //# accessing
    //##################################################

    public KmList<KmgPropertyGroup> getGroups()
    {
        return _groups;
    }

    public KmList<String> getValidFlags()
    {
        return _validFlags;
    }

    public void addValidFlag(String e)
    {
        _validFlags.add(e);
    }

    public void addValidFlags(KmList<String> v)
    {
        _validFlags.addAll(v);
    }

    //##################################################
    //# parse
    //##################################################

    public void parseFile(String path)
    {
        KmStfParser parser;
        parser = new KmStfParser();
        parser.parseFile(path);

        KmStfRoot root;
        root = parser.getRoot();

        parse(root);
    }

    @Override
    public void parse(KmStfElement e)
    {
        for ( KmStfElement c : e.getChildren("group") )
        {
            KmgPropertyGroup g;
            g = new KmgPropertyGroup(this);
            g.parse(c);
            _groups.add(g);
        }
    }

    @Override
    public void validate()
    {
        KmList<String> names = new KmList<String>();

        KmList<KmgProperty> all = getAll();
        for ( KmgProperty e : all )
            names.add(e.getName());

        KmList<String> dups = names.getDuplicates();
        if ( dups.isNotEmpty() )
            error("Duplicate property names(%s). ", dups.format());
    }

    @Override
    public void postValidate()
    {
        // none
    }

    private KmList<KmgProperty> getAll()
    {
        KmList<KmgProperty> v = new KmList<KmgProperty>();
        for ( KmgPropertyGroup g : _groups )
            v.addAll(g.getProperties());
        return v;
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return "PropertyFile";
    }
}
