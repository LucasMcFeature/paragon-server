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

package com.kodemore.servlet.control;

import com.kodemore.collection.KmList;
import com.kodemore.html.KmHtmlBuilder;
import com.kodemore.servlet.script.ScRootScript;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.Kmu;

public class ScChart
    extends ScDiv
{
    //##################################################
    //# constants
    //##################################################

    private static final int Y_LABEL_MARGIN = 100;

    //##################################################
    //# variables
    //##################################################

    private int              _height;
    private int              _width;

    private String           _xAxisLabel;
    private String           _yAxisLabel;

    private KmList<String>   _data;
    /**
     *  review_aaron: data points are currently strings. Need to
     *  be in the following format:
     *      { x: 1, y: 2 } 
     */
    private KmList<String>   _dataPoints;

    //##################################################
    //# constructor
    //##################################################

    @Override
    protected void install()
    {
        super.install();

        // review_aaron: need good defaults
        _height = 400;
        _width = 400;
    }

    //##################################################
    //# data
    //##################################################

    public KmList<String> getDataPoints()
    {
        return _dataPoints;
    }

    public void setDataPoints(KmList<String> e)
    {
        _dataPoints = e;
    }

    public void addDataPoint(String e)
    {
        getDataPoints().add(e);
    }

    //##################################################
    //# height / width
    //##################################################

    public int getHeight()
    {
        return _height;
    }

    public void setHeight(int e)
    {
        _height = e;
    }

    public int getWidth()
    {
        return _width;
    }

    public void setWidth(int e)
    {
        _width = e;
    }

    //##################################################
    //# axis labels
    //##################################################

    public String getXAxisLabel()
    {
        return _xAxisLabel;
    }

    public void setXAxisLabel(String e)
    {
        _xAxisLabel = e;
    }

    public boolean hasXAxisLabel()
    {
        return getXAxisLabel() != null;
    }

    public String getYAxisLabel()
    {
        return _yAxisLabel;
    }

    public void setYAxisLabel(String e)
    {
        _yAxisLabel = e;
    }

    public boolean hasYAxisLabel()
    {
        return getYAxisLabel() != null;
    }

    //##################################################
    //# print
    //##################################################

    @Override
    protected void renderControlOn(KmHtmlBuilder out)
    {
        String style = Kmu.format("height: %spx; width: %spx;", getHeight(), getWidth());

        out.openDiv();
        out.printAttribute("id", getHtmlId());
        out.printAttribute("style", style);
        out.close();

        out.begin("svg");
        out.end("svg");

        out.endDiv();

        ScRootScript ajax;
        ajax = out.getPostRender();
        ajax.run(formatLineChartScript());
    }

    //##################################################
    //# script
    //##################################################

    private String formatLineChartScript()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.print("nv.addGraph(function(){");
        initializeChart(out);
        formatXAxis(out);
        formatYAxis(out);
        finalizeChart(out);
        out.print("});");
        return out.toString();
    }

    private void initializeChart(KmStringBuilder out)
    {
        out.print("var chart;");
        out.print("chart = nv.models.lineChart().useInteractiveGuideline(true);");
        out.print("chart.x(function(d,i){return d.x});");

        if ( hasYAxisLabel() )
            out.printf("chart.margin({left:%s});", Y_LABEL_MARGIN);
    }

    private void formatXAxis(KmStringBuilder out)
    {
        out.print("chart.xAxis.tickFormat(d3.format(',.1f'));");

        if ( hasXAxisLabel() )
            out.printf("chart.xAxis.axisLabel('%s');", getXAxisLabel());
    }

    private void formatYAxis(KmStringBuilder out)
    {
        out.print("chart.yAxis.tickFormat(d3.format(',.1f'));");

        if ( hasYAxisLabel() )
            out.printf("chart.yAxis.axisLabel('%s');", getYAxisLabel());
    }

    private void finalizeChart(KmStringBuilder out)
    {
        out.printf(
            "d3.select('#%s svg').datum(%s).transition().duration(500).call(chart);",
            getHtmlId(),
            formatData());
        out.print("nv.utils.windowResize(chart.update);");
        out.print("return chart;");
    }

    //##################################################
    //# format data
    //##################################################

    private String formatData()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();

        out.print(formatDataSeries());

        return out.toString();
    }

    private String formatDataSeries()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.print("[{");
        out.print("key: 'Sample Data',");
        out.print("area: false,");
        out.print("values : [");
        out.print(Kmu.formatList(getDataPoints()));
        out.print("]");
        out.print("}]");
        return out.toString();
    }
}
