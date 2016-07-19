package com.example.SimpleFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/18 17:30
 * description:
 */
public abstract class IChart {
    public abstract void display();


    public static final int CHART_LINE = 0;
    public static final int CHART_HISTOGRAM = 1;
    public static final int CHAR_PIE = 2;

    /**
     * 对外提供静态方法
     */
    public static IChart getChart(int chart) {
        IChart iChart;
        switch (chart) {
            case CHAR_PIE:
                iChart = new PieChart();
                break;
            case CHART_HISTOGRAM:
                iChart = new HistogramChart();
                break;
            case CHART_LINE:
                iChart = new LineChart();
                break;
            default:
                throw new IllegalStateException("不支持该Chart");
        }

        return iChart;
    }
}
