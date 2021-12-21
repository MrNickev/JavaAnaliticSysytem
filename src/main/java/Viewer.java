import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class Viewer {
    private ChartPanel panel;

    private ArrayList<JFreeChart> charts;
    private JFrame frame;
    private int chartNum;
    private JPanel buttonPanel;

    public Viewer() throws SQLException, ClassNotFoundException, ParseException {
        charts = createChartsArray();
        createButtonsPanel();
        createPanel(charts.get(0));
        createAndShowGUI();
    }

    public void createAndShowGUI() throws SQLException, ClassNotFoundException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Statistic");
        var pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        pan.add(buttonPanel, BorderLayout.SOUTH);
        pan.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(panel);
//        frame.getContentPane().add(buttonPanel);
        frame.getContentPane().add(pan);
        frame.pack();
        frame.setVisible(true);
    }

    private void createButtonsPanel() {
        var next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanelChart(ButtonsAction.NEXT);
            }
        });
        var previous = new JButton("Previous");
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanelChart(ButtonsAction.PREVIEW);
            }
        });
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 30));
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(next, BorderLayout.WEST);
        buttonPanel.add(previous, BorderLayout.EAST);
    }

    private void createPanel(JFreeChart chart){
        panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);


    }

    private void changePanelChart(ButtonsAction action) {
        if (action == ButtonsAction.NEXT)
            chartNum = chartNum < charts.size()-1 ? chartNum + 1 : 0;
        else chartNum = chartNum == 0 ? charts.size() - 1 : chartNum - 1;
        var chart = charts.get(chartNum);
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        panel.setChart(chart);
    }

    private ArrayList<JFreeChart> createChartsArray() throws SQLException, ClassNotFoundException, ParseException {
        var charts = new ArrayList<JFreeChart>();
        charts.add(GraphBuilder.createPieChart(DBGetter.GetCityStat(), "City statistic"));
        charts.add(GraphBuilder.createPieChart(DBGetter.getAgeStat(), "Age statistic"));

        charts.addAll(GraphBuilder.createBarCharts(DBGetter.GetStudentAchievmentStat()));

        return charts;
    }
}
