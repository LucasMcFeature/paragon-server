/*
  Copyright (c) 2010-2010 www.kodemore.com

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

package com.kodemore.jasper.test;

import com.kodemore.jasper.KmJasperBand;
import com.kodemore.jasper.KmJasperReport;
import com.kodemore.jasper.KmJasperReportBuilder;
import com.kodemore.jasper.KmJasperStyle;
import com.kodemore.jasper.KmJasperText;
import com.kodemore.jasper.field.KmJasperIntegerField;
import com.kodemore.jasper.field.KmJasperStringField;

public class KmJasperTestShapesAndColors
    implements KmJasperTestConstantsIF
{
    public static void main(String[] args)
    {
        new KmJasperTestShapesAndColors().run();
    }

    private void run()
    {
        KmJasperReportBuilder builder;
        builder = getBuilder();
        builder.dumpXml();

        KmJasperReport report;
        report = builder.compile();
        report.setEchoDataSource(100);
        report.writePdfFile(OUTPUT_PDF_FILE);

        System.out.println("ok.");
    }

    private KmJasperReportBuilder getBuilder()
    {
        KmJasperReportBuilder builder;
        builder = new KmJasperReportBuilder();

        KmJasperIntegerField idField = builder.addIntegerField("id");
        KmJasperStringField nameField = builder.addStringField("name");

        KmJasperBand detail;
        detail = builder.getDetail();
        detail.addEvenRowShading();

        int x = 0;
        int y = 0;
        int w = 100;
        int h = 0;

        // static text, no formatting...

        KmJasperText text;
        text = detail.addText("Text");
        text.setShape(x, y, w, h);

        // simple field, green...

        x += w;
        text = detail.addText(idField);
        text.setShape(x, y, w, h);
        text.getColor().setGreen();

        // simple field, white text, blue background, red border...

        KmJasperStyle blueStyle;
        blueStyle = builder.addStyle();
        blueStyle.getBorderColor().setRed();
        blueStyle.setBorderPadding(5);
        blueStyle.getColor().setYellow();
        blueStyle.getFillColor().setBlue();

        x += w;
        text = detail.addText(nameField);
        text.setShape(x, y, w, h);
        text.setStyle(blueStyle);
        text.getFont().setBold();

        // change the fill conditionally...

        KmJasperStyle evenOddStyle;
        evenOddStyle = builder.addStyle();
        evenOddStyle.getBorderColor().setGray();
        evenOddStyle.setBorderPadding(5);
        evenOddStyle.addStyleIf(idField.exprIsLessThan(5)).getFillColor().setRed();
        evenOddStyle.addStyleIf(idField.exprIsLessThan(10)).getFillColor().setYellow();
        evenOddStyle.addStyleIf(idField.exprIsLessThan(15)).getFillColor().setGreen();

        x += w + 10;
        text = detail.addText(idField);
        text.setShape(x, y, w, h);
        text.alignCenter();
        text.setStyle(evenOddStyle);

        return builder;
    }
}
