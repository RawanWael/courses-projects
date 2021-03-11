package Algorithms;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GanttchartNode {
    int id;
    double start;
    double end;
    String idMultiprogrammed;

    public GanttchartNode(int id, double start, double end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public GanttchartNode(String idMultiprogrammed, double start, double end) {
        this.start = start;
        this.end = end;
        this.idMultiprogrammed = idMultiprogrammed;
    }

    public String getIdMultiprogrammed() {
        return idMultiprogrammed;
    }

    public void setIdMultiprogrammed(String idMultiprogrammed) {
        this.idMultiprogrammed = idMultiprogrammed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStart() {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = this.start;
        return Double.parseDouble(df.format(temp));
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = this.end;
        return Double.parseDouble(df.format(temp));
    }

    public void setEnd(double end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "GanttchartNode{" +
                "start=" + start +
                ", end=" + end +
                ", idMultiprogrammed='" + idMultiprogrammed + '\'' +
                '}';
    }
}

