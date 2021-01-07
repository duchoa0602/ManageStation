package com.manage.station.web.controller.highchart;

import java.util.List;

public class HighChartModel {
    private Title title;
    private XAxis xAxis;
    private YAxis yAxis;
    private List<SeriesObj> series;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public XAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(XAxis xAxis) {
        this.xAxis = xAxis;
    }

    public YAxis getyAxis() {
        return yAxis;
    }

    public void setyAxis(YAxis yAxis) {
        this.yAxis = yAxis;
    }

    public List<SeriesObj> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesObj> series) {
        this.series = series;
    }
}
