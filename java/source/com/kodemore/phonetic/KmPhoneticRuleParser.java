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

package com.kodemore.phonetic;

import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.List;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

public class KmPhoneticRuleParser
{
    //##################################################
    //# variables
    //##################################################

    private LineNumberReader _reader;

    //##################################################
    //# accessing
    //##################################################

    public KmList<KmPhoneticRule> parseFile(String path)
    {
        KmList<String> lines = Kmu.readTextFileLines(path);
        return parseLines(lines);
    }

    public KmList<KmPhoneticRule> parseLines(List<String> lines)
    {
        KmList<KmPhoneticRule> v = new KmList<KmPhoneticRule>();
        for ( String e : lines )
            parseLine(e);
        return v;
    }

    /**
     * Assume that this string represents a single line
     * and is therefone intended to parse to a single rule.
     * The line may contain a comment.  If the entire line
     * is a comment then return null.  If the line cannot
     * be parsed throw an exception.
     */
    public KmPhoneticRule parseLine(String s)
    {
        int i = s.indexOf("#");
        if ( i >= 0 )
            s = s.substring(0, i);

        s = s.trim().toLowerCase();
        if ( s.length() == 0 )
            return null;

        Iterator<String> ii = Kmu.tokenize(s, ',').iterator();
        String mode = null;
        String oldValue = null;
        String newValue = null;

        if ( ii.hasNext() )
            mode = ii.next();

        if ( ii.hasNext() )
            oldValue = ii.next();

        if ( ii.hasNext() )
            newValue = ii.next();

        return newRule(mode, oldValue, newValue);
    }

    public KmPhoneticRule newRule(String mode, String oldValue, String newValue)
    {
        KmPhoneticRule r = new KmPhoneticRule();
        if ( mode == null )
            error("Mode is required.");
        if ( mode.equals("all") )
            r.setMode(KmPhoneticRule.MODE_ALL);
        else
            if ( mode.equals("prefix") )
                r.setMode(KmPhoneticRule.MODE_PREFIX);
            else
                if ( mode.equals("suffix") )
                    r.setMode(KmPhoneticRule.MODE_SUFFIX);
                else
                    error("Unknown mode: " + mode);

        if ( oldValue == null )
            error("Old value is required.");
        if ( oldValue.equals("") )
            error("Old value cannot be empty.");
        r.setOldValue(oldValue);

        if ( newValue == null )
            error("New value is required.");
        r.setNewValue(newValue);
        return r;
    }

    public void error(String msg)
    {
        String s = "Metaphone Parser";
        if ( _reader != null )
            s += " (line " + _reader.getLineNumber() + ")";
        s += " " + msg;
        throw new RuntimeException(s);
    }

}
