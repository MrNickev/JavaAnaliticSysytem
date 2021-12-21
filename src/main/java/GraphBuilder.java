import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class GraphBuilder {

    private static PieDataset createPieDataset(HashMap<String, Integer> statistic) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (var data : statistic.entrySet())
            dataset.setValue(data.getKey(), data.getValue());
        return dataset;
    }

    private static CategoryDataset createCategoryDataset(HashMap<String, int[]> statistic) {
        var dataset = new DefaultCategoryDataset();
        for (var data : statistic.entrySet()) {
            dataset.addValue(data.getValue()[0], "Тесты", data.getKey().split("\\.")[0]);
            dataset.addValue(data.getValue()[1], "Задачи", data.getKey().split("\\.")[0]);
        }
        return dataset;
    }


    public static JFreeChart createPieChart(HashMap<String, Integer> statistic, String name) {
        var dataset = createPieDataset(statistic);
        var chart = ChartFactory.createPieChart(name, dataset, false, true, false);
        chart.setBackgroundPaint(Color.WHITE);

        PiePlot plot = (PiePlot) chart.getPlot();
        chart.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);
        plot.setExplodePercent(statistic.entrySet().iterator().next().getKey(), 0.01);

        for (var data : statistic.entrySet())
            plot.setSectionPaint(data.getKey(), createRandomColor());

        plot.setLabelFont(new Font("Century Gothic", Font.BOLD, 14));
        plot.setLabelLinkPaint(Color.BLACK);
        plot.setLabelLinkStroke(new BasicStroke((2.0f)));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.white);
        plot.setLabelBackgroundPaint(null);

        return chart;
    }

    private static JFreeChart createBarChart(HashMap<String, int[]> data, String name) {
        var barChart = ChartFactory.createBarChart("Статистика по студенту: " + name, "Темы", "Результат", createCategoryDataset(data), PlotOrientation.VERTICAL, true, true, false);
        return barChart;
    }

    public static ArrayList<JFreeChart> createBarCharts(TreeMap<String, HashMap<String, int[]>> statistic) {
        var charts = new ArrayList<JFreeChart>();
        for (var stud : statistic.entrySet())
            charts.add(createBarChart(stud.getValue(), stud.getKey()));
        return charts;
    }

    private static Color createRandomColor() {
        var rand = new Random();
        var r = rand.nextInt(255);
        var g = rand.nextInt(255);
        var b = rand.nextInt(255);
        return new Color(r,g,b);
    }
}
