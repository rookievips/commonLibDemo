package com.chen.api.widgets.donutchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


import com.chen.api.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DonutChart extends View {
    private ArrayList<Donut> donuts = new ArrayList<>();
    private RectF rectf;
    private final Paint paint = new Paint(1);
    private final Paint paintLine = new Paint();
    private final Paint paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);


    private float degree = 0.0F;
    private float temp = 0.0F;
    private int i = 0;
    private int rectSize = 0;
    private int backgroundColor = 0;

    private int width = 0;
    private int canvasWidth = 0;

    private String cetStr;
    private String donutContentType;

    public DonutChart(Context context) {
        super(context);
    }

    public DonutChart(Context context, String content, int backgroundColor, ArrayList<Donut> donuts, String donutContentType) {
        super(context);

        textSize = DisplayUtil.dp2px(8) + 1;
        textPadding = DisplayUtil.dp2px(1);
        circleR = DisplayUtil.dp2px(3);
        this.donutContentType = donutContentType;
        this.backgroundColor = backgroundColor;
        initDonut(content, donuts);
    }

    public void initDonut(String content, ArrayList<Donut> donuts) {
        degree = 0.0F;
        i = 0;
        rectSize = 0;
        width = 0;

        this.donuts = donuts;
        this.cetStr = content;
        if (donuts != null && donuts.size() > 0) {
            this.temp = donuts.get(0).getDegree();
        }
        initPoint();
    }

    public void startDonut() {
        initDonut(cetStr, donuts);
        invalidate();

    }

    public void startDonut(String content, ArrayList<Donut> donuts, String donutContentType) {
        this.donutContentType = donutContentType;
        initDonut(content, donuts);
        invalidate();
    }

    float ratioHeight = 0.72f;
    int direction = -1;
    int angleOff = -45;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        if ((rectf == null) || (width != canvasWidth)) {
            paintCircle.setStyle(Paint.Style.FILL);
            paintCircle.setColor(backgroundColor);

            canvas.drawColor(backgroundColor);
            width = canvas.getWidth();
            int height = canvas.getHeight();
            this.rectSize = ((int) (width * ratioHeight));
            int xPoint = width / 2 - rectSize / 2;
            int yPoint = height / 2 - rectSize / 2;
            if (width > height) {
                width = height;
                height = canvas.getWidth();
                this.rectSize = ((int) (width * ratioHeight));
                xPoint = height / 2 - rectSize / 2;
                yPoint = width / 2 - rectSize / 2;
            }
            rectf = new RectF(xPoint, yPoint, rectSize + xPoint, rectSize + yPoint);
            oPointF.set((xPoint + rectSize + xPoint) / 2, (yPoint + rectSize + yPoint) / 2);
        }
        Donut donut = donuts.get(i);
        if (i == 0) {
            paint.setColor(donut.getColor());
            if (degree != 0)
                canvas.drawArc(rectf, direction * (temp - donut.getDegree()) + angleOff, direction * degree, true, paint);
        } else {
            paint.setColor(donut.getColor());
            if ((degree - (temp - donut.getDegree())) != 0)
                canvas.drawArc(rectf, direction * (temp - donut.getDegree()) + angleOff, direction * (degree - (temp - donut.getDegree())), true, paint);
        }

        for (int j = 0; j < i; j++) {
            paint.setColor(donuts.get(j).getColor());
            float v = 0;
            for (int x = 0; x < j; x++) {
                v += donuts.get(x).getDegree();
            }
            if ((donuts.get(j).getDegree()) != 0)
                canvas.drawArc(rectf, direction * v + angleOff, direction * (donuts.get(j).getDegree()), true, paint);
        }
        canvas.drawCircle(rectf.centerX(), rectf.centerY(), (float) (this.rectSize * 0.3), paintCircle);
        degree = degree + 5;
        if (degree >= temp) {
            i = i + 1;
            if (i >= donuts.size()) {
                i = i - 1;
            } else {
                temp += donuts.get(i).getDegree();
            }
        }
        //>360 stop drawing
        if ((degree <= 360)) {
            invalidate();
        } else {
            drawInfo(canvas);
        }
    }


    private void drawInfo(Canvas canvas) {
        float dff = 0.707f;
        float endX;
        float minHeight = textSize * 2 + textPadding * 4;
        ArrayList<PointLine> plRight = new ArrayList<>();
        ArrayList<PointLine> plLeft = new ArrayList<>();
        for (int i = 0; i < linePoints.size(); i++) {
            Float af = linePoints.get(i);
            double cos = Math.cos(af);
            double sin = Math.sin(af);
            int flagX = cos > 0 ? 1 : -1;
            int flagY = sin > 0 ? 1 : -1;

            float x = (float) (rectSize / 2.0 * cos + oPointF.x);
            float y = (float) (rectSize / 2.0 * sin + oPointF.y);
            float h = DisplayUtil.dp2px(15);
            float x0 = flagX * (h * dff) + x;
            float y0 = flagY * (h * dff) + y;

            PointF pfStart = new PointF(x, y);
            PointF pfMid = new PointF(x0, y0);
            if (flagX > 0) {
                endX = canvasWidth - DisplayUtil.dp2px(20);
            } else {
                endX = DisplayUtil.dp2px(20);
            }
            PointF pfEnd = new PointF(endX, y0);
            PointLine pl = new PointLine(pfStart, pfMid, pfEnd);
            pl.setColor(donuts.get(i).getColor());
            pl.setRatio(donuts.get(i).getRatioStr());
            pl.setName(donuts.get(i).getName());
            if (flagX > 0) {// right
                plRight.add(pl);
            } else {
                plLeft.add(pl);
            }
        }

        Collections.sort(plRight);
        Collections.sort(plLeft);

        ArrayList<PointLine> pls = plRight;
        drawLinePoint(canvas, minHeight, pls);
        pls = plLeft;
        drawLinePoint(canvas, minHeight, pls);

        float d = (float) (rectSize * 0.3);
        float textMaxWidth = (float) (0.314 * 4 * d);
        float textMaxHeight = d;
        float ts;
        float tsB = (float) (textMaxWidth * 0.182);
        int length = cetStr != null ? cetStr.length() : 0;
        if (cetStr != null && length > 0) {

        } else {
            cetStr = "0.00";
        }
        if ("0".equals(cetStr)) {
            cetStr = "0.00";
        }
        if (length < 8) {
            length = 8;
        }
        length -= 2;
        ts = textMaxWidth * 2 / length;
        Log.e("px", "ts:" + ts);
        if (ts > textMaxHeight) {
            ts = textMaxHeight;
        }

        Log.e("px", "textMaxWidth:" + textMaxWidth);
        Log.e("px", "textMaxHeight:" + textMaxHeight);
        paintLine.setTextSize(ts);
        paintLine.setTextAlign(Paint.Align.CENTER);

        paintLine.setColor(0xff434343);
        canvas.drawText(cetStr, oPointF.x, oPointF.y, paintLine);
        paintLine.setColor(0xff626262);
        paintLine.setTextSize(tsB);
//        canvas.drawText("消费金额 (元)", oPointF.x, oPointF.y + tsB + DisplayUtil.dp2px(3), paintLine);
        canvas.drawText(donutContentType, oPointF.x, oPointF.y + tsB + DisplayUtil.dp2px(3), paintLine);
    }

    private void drawLinePoint(Canvas canvas, float minHeight, ArrayList<PointLine> pls) {
        for (int i = 1; i < pls.size(); i++) {
            PointLine pl = pls.get(i);
            PointLine pl0 = pls.get(i - 1);

            PointF pfStart = pl.getPfStart();
            PointF pfMid = pl.getPfMid();
            PointF pfMid0 = pl0.getPfMid();
            PointF pfEnd = pl.getPfEnd();
            float v = pfMid.y - pfMid0.y;

            if (minHeight > Math.abs(v)) {

                fixMidPoint(pfStart, pfMid, pfEnd, minHeight, v);

                for (int j = i + 1; j < pls.size(); j++) {
                    PointLine pointLine = pls.get(j);

                    PointF pfStart2 = pointLine.getPfStart();
                    PointF pfMid2 = pointLine.getPfMid();
                    PointF pfEnd2 = pointLine.getPfEnd();
                    fixMidPoint(pfStart2, pfMid2, pfEnd2, minHeight, v);
                }
            }
        }

        for (int i = 0; i < pls.size(); i++) {
            PointLine pl = pls.get(i);
            String name = pl.getName();
            if (name == null || "".equals(name)) {
                continue;
            }


            PointF pfStart = pl.getPfStart();
            PointF pfMid = pl.getPfMid();
            PointF pfEnd = pl.getPfEnd();


            paintLine.setColor(pl.getColor());
            paintLine.setAntiAlias(true);//设置画笔为无锯齿
            paintLine.setStrokeWidth(DisplayUtil.dp2px(1f));//线宽

            paintLine.setStyle(Paint.Style.STROKE);
            path.reset();
            path.moveTo(pfStart.x, pfStart.y);
            path.lineTo(pfMid.x, pfMid.y);
            path.lineTo(pfEnd.x, pfEnd.y);
            canvas.drawPath(path, paintLine);
            paintLine.setStyle(Paint.Style.FILL);
            canvas.drawCircle(pfEnd.x, pfEnd.y, circleR, paintLine);
            float x1 = pfEnd.x;
            if (pfMid.x > oPointF.x) {
                x1 -= circleR;
                paintLine.setTextAlign(Paint.Align.RIGHT);
            } else {
                x1 += circleR;
                paintLine.setTextAlign(Paint.Align.LEFT);
            }
            paintLine.setTextSize(textSize);
            paintLine.setColor(0xff626262);

            String ratioStr = pl.getRatio();
            if (!"".equals(name)) {
                canvas.drawText(name, x1, pfEnd.y + textSize + textPadding + textPadding / 2, paintLine);
            }
            if (ratioStr != null && !"".equals(ratioStr)) {
                canvas.drawText(ratioStr, x1, pfEnd.y - textPadding - textPadding, paintLine);
            }
        }


    }

    private void fixMidPoint(PointF pfStart, PointF pfMid, PointF pfEnd, float minHeight, float v) {
        pfMid.y = pfMid.y + minHeight - Math.abs(v);
        if (pfMid.x > oPointF.x) {

            pfMid.x = pfMid.x - (minHeight - Math.abs(v));
            if (pfMid.x < pfStart.x) {
                pfMid.x = pfStart.x + pfMid.y - pfStart.y;
            }
        } else {
            pfMid.x = pfMid.x + minHeight - Math.abs(v);
            if (pfMid.x > pfStart.x) {
                pfMid.x = pfStart.x - (pfMid.y - pfStart.y);
            }
        }

        pfEnd.y = pfMid.y;
    }


    private void drawInfo2(Canvas canvas) {
        for (int i = 0; i < linePoints.size(); i++) {
            Float af = linePoints.get(i);

            double cos = Math.cos(af);
            double sin = Math.sin(af);
            float x = (float) (rectSize / 2.0 * cos + oPointF.x);
            float y = (float) (rectSize / 2.0 * sin + oPointF.y);

            int integer = donuts.get(i).getColor();

            paintLine.setColor(integer);
            paintLine.setAntiAlias(true);//设置画笔为无锯齿
            paintLine.setStrokeWidth(DisplayUtil.dp2px(1f));//线宽

            if (donuts != null && i < donuts.size()) {

                Donut donut = donuts.get(i);
                String ratioStr = donut.getRatioStr();
                String name = donut.getName();

                if (name == null || "".equals(name)) {
                    continue;
                }
                int flagX = cos > 0 ? 1 : -1;
                int flagY = sin > 0 ? 1 : -1;
                paintLine.setStyle(Paint.Style.STROKE);
                path.reset();
                path.moveTo(x, y);
                float dff = 0.707f;
                int h = DisplayUtil.dp2px(15);
                float x0 = flagX * (h * dff) + x;
                float y0 = flagY * (h * dff) + y;

                float x1;
                if (name.length() > 4) {
                    x1 = x0 + flagX * textSize * name.length();
                } else {
                    x1 = x0 + flagX * (textSize * 4 + textPadding);
                }

                path.lineTo(x0, y0);
                path.lineTo(x1, y0);
                canvas.drawPath(path, paintLine);

                paintLine.setStyle(Paint.Style.FILL);
                canvas.drawCircle(x1, y0, circleR, paintLine);
                if (flagX > 0) {
                    x1 -= circleR;
                    paintLine.setTextAlign(Paint.Align.RIGHT);
                } else {
                    x1 += circleR;
                    paintLine.setTextAlign(Paint.Align.LEFT);
                }
                paintLine.setTextSize(textSize);
                paintLine.setColor(0xff626262);

                if (!"".equals(name)) {
                    canvas.drawText(name, x1, y0 + textSize + textPadding + textPadding / 2, paintLine);
                }
                if (ratioStr != null && !"".equals(ratioStr)) {
                    canvas.drawText(ratioStr, x1, y0 - textPadding - textPadding, paintLine);
                }
            }
        }

        float d = (float) (rectSize * 0.32);
        float textMaxWidth = (float) (0.314 * 4 * d);
        float textMaxHeight = d;
        float ts = 0;
        float tsB = (float) (textMaxWidth * 0.16);
        int length = cetStr != null ? cetStr.length() : 0;
        if (cetStr != null && length > 0) {

        } else {
            cetStr = "0.00";
        }
        if ("0".equals(cetStr)) {
            cetStr = "0.00";
        }
        if (length < 8) {
            length = 8;
        }
        length -= 2;
        ts = textMaxWidth * 2 / length;
        Log.e("px", "ts:" + ts);
        if (ts > textMaxHeight) {
            ts = textMaxHeight;
        }

        Log.e("px", "textMaxWidth:" + textMaxWidth);
        Log.e("px", "textMaxHeight:" + textMaxHeight);
        paintLine.setTextSize(ts);
        paintLine.setTextAlign(Paint.Align.CENTER);

        paintLine.setColor(0xff434343);
        canvas.drawText(cetStr, oPointF.x, oPointF.y, paintLine);
        paintLine.setColor(0xff626262);
        paintLine.setTextSize(tsB);
        canvas.drawText("消费金额 (元)", oPointF.x, oPointF.y + ts, paintLine);
    }


    int textSize = 25;
    int textPadding = 4;
    float circleR = 8;
    Path path = new Path();
    private PointF oPointF = new PointF();
    private List<Float> linePoints = new ArrayList<>();

    private void initPoint() {
        if (donuts != null) {
            linePoints.clear();
            float sum = Math.abs(angleOff);
            for (int i = 0; i < donuts.size(); i++) {
                Float af = donuts.get(i).getDegree();
                Float pf = (af / 2 + sum);
                linePoints.add((float) (pf / 180.0 * 3.14 * direction));
                sum += af;
                Log.e("sum", sum + "");
                Log.e("pf", pf + "");
            }
        }
    }

    class PointLine implements Comparable<PointLine> {
        PointF pfStart;
        PointF pfMid;
        PointF pfEnd;
        int color;
        String name;
        String ratio;


        public PointLine() {
        }

        public PointLine(PointF pfStart, PointF pfMid, PointF pfEnd) {
            this.pfStart = pfStart;
            this.pfMid = pfMid;
            this.pfEnd = pfEnd;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public PointF getPfStart() {
            return pfStart;
        }

        public void setPfStart(PointF pfStart) {
            this.pfStart = pfStart;
        }

        public PointF getPfMid() {
            return pfMid;
        }

        public void setPfMid(PointF pfMid) {
            this.pfMid = pfMid;
        }

        public PointF getPfEnd() {
            return pfEnd;
        }

        public void setPfEnd(PointF pfEnd) {
            this.pfEnd = pfEnd;
        }

        @Override
        public int compareTo(@NonNull PointLine another) {
            if (pfMid.y == another.pfMid.y) {
                return 0;
            } else if (pfMid.y > another.pfMid.y) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}