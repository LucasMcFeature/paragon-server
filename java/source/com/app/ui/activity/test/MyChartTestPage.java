package com.app.ui.activity.test;

import com.kodemore.json.KmJsonObject;
import com.kodemore.servlet.control.ScBarChart;
import com.kodemore.servlet.control.ScChartSeries;
import com.kodemore.servlet.control.ScGroup;
import com.kodemore.servlet.control.ScLineChart;
import com.kodemore.servlet.control.ScMultiBarChart;
import com.kodemore.servlet.control.ScPageRoot;
import com.kodemore.servlet.control.ScPieChart;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.types.KmHtmlColor;
import com.kodemore.utility.KmRandomUtility;

/**
 * Test the layout and usage of the groups.
 */
public class MyChartTestPage
    extends MyTestPage
{
    //##################################################
    //# singleton
    //##################################################

    public static final MyChartTestPage instance = new MyChartTestPage();

    private MyChartTestPage()
    {
        // singleton
    }

    //##################################################
    //# constants
    //##################################################

    private static final int LINE_POINTS = 80;
    private static final int BAR_POINTS  = 20;

    //##################################################
    //# install
    //##################################################

    @Override
    protected void installRoot(ScPageRoot root)
    {
        root.css().gap();

        ScGroup group;
        group = root.addGroup("Chart Test");
        group.addPad().addText(getDescription());
        root.add(createLineChart());
        root.add(createBarChart());
        root.add(createMultiBarChart());
        root.add(createPieChart());
    }

    private String getDescription()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("This is a test of the Various ScCharts.");
        out.println();
        out.println("The charts demonstated here are:");
        out.println("ScLineChart");
        out.println("ScBarChart");
        out.println("ScMultiBarChart");
        out.println("ScPieChart");
        out.println();
        out.println("Each chart has a variety of options set in order to demonstrate "
            + "what options are available for each chart type.");
        return out.toString();
    }

    private ScLineChart createLineChart()
    {
        ScLineChart chart;
        chart = new ScLineChart();
        chart.css().borderBlack();
        chart.style().height(400);
        chart.setXAxisLabel("X Axis");
        chart.setYAxisLabel("Y Axis");
        chart.setYAxisPrecision(1);
        chart.setYAxisMin(-10);
        chart.setYAxisMax(75);

        ScChartSeries s;
        s = chart.addSeries();
        s.setKey("Blue Line");
        s.setColor(KmHtmlColor.createBlue());
        generateData(s, LINE_POINTS);

        s = chart.addSeries();
        s.setKey("Green Area");
        s.setColor(KmHtmlColor.createGreen());
        s.setArea();
        generateData(s, LINE_POINTS);

        s = chart.addSeries();
        s.setKey("No Color Set Line");
        generateData(s, LINE_POINTS);

        s = chart.addSeries();
        s.setKey("No Color Set Area");
        s.setArea();
        generateData(s, LINE_POINTS);

        return chart;
    }

    private ScBarChart createBarChart()
    {
        ScBarChart chart;
        chart = new ScBarChart();
        chart.css().borderBlack();
        chart.style().height(400);
        chart.setXAxisLabel("Name");
        chart.setYAxisLabel("Value");
        chart.setYAxisMax(5);
        chart.staggerLabels();

        generateBarChartData(chart);

        return chart;
    }

    private ScMultiBarChart createMultiBarChart()
    {
        ScMultiBarChart chart;
        chart = new ScMultiBarChart();
        chart.css().borderBlack();
        chart.style().height(400);
        chart.setXAxisLabel("X Axis");
        chart.setYAxisLabel("Y Axis");
        chart.setYAxisPrecision(2);
        chart.setGroupSpacing(0.2);
        chart.setRotateLabelsDegrees(45);
        chart.showGroupStackControl();

        ScChartSeries s;
        s = chart.addSeries();
        s.setKey("Blue");
        s.setColor(KmHtmlColor.createBlue());
        generateData(s, BAR_POINTS);

        s = chart.addSeries();
        s.setKey("Green");
        s.setColor(KmHtmlColor.createGreen());
        generateData(s, BAR_POINTS);

        s = chart.addSeries();
        s.setKey("No Color Set 1");
        generateData(s, BAR_POINTS);

        s = chart.addSeries();
        s.setKey("No Color Set 2");
        generateData(s, BAR_POINTS);

        return chart;
    }

    private ScPieChart createPieChart()
    {
        ScPieChart chart;
        chart = new ScPieChart();
        chart.setDonut(true);
        chart.setLabelTypeKey();
        chart.css().borderBlack();
        chart.style().height(400);

        generatePieChartData(chart);

        return chart;
    }

    //##################################################
    //# utility
    //##################################################

    private void generateData(ScChartSeries s, int number)
    {
        int n = number;
        for ( int i = 0; i < n; i++ )
            s.addPoint(createRandomDataPoint(i));
    }

    private KmJsonObject createRandomDataPoint(int i)
    {
        double x = i;
        double y = KmRandomUtility.getInteger(60)
            - KmRandomUtility.getInteger(40)
            + KmRandomUtility.getDouble();

        KmJsonObject point;
        point = new KmJsonObject();
        point.setDouble("x", x);
        point.setDouble("y", y);
        return point;
    }

    private void generateBarChartData(ScBarChart pie)
    {
        pie.addBar("pi (blue)", 3.14, KmHtmlColor.createBlue());
        pie.addBar("phi (black)", 1.618, KmHtmlColor.createBlack());
        pie.addBar("gamma (green)", 1.4, KmHtmlColor.createGreen());
        pie.addBar("e", 2.72);
        pie.addBar("0", 0);
        pie.addBar("-1", -1);
        pie.addBar("Square Root of 2", 1.414);
        pie.addBar("Random 1", getRandomNumber());
        pie.addBar("Random 2 (This is Extra Text to denonstate a large label)", getRandomNumber());
        pie.addBar(
            "Random 3 (Enabling Stagger Labels helps display large labels)",
            getRandomNumber());
        pie.addBar("Random 4", getRandomNumber());
    }

    private double getRandomNumber()
    {
        return KmRandomUtility.getDouble()
            + KmRandomUtility.getInteger(3)
            - KmRandomUtility.getInteger(3);
    }

    private void generatePieChartData(ScPieChart pie)
    {
        pie.addSlice("pi", 3.14);
        pie.addSlice("phi", 1.618);
        pie.addSlice("gamma", 1.4);
        pie.addSlice("e", 2.72);
        pie.addSlice("1", 1);
    }
}
