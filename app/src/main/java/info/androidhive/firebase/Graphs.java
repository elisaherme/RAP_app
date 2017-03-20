package info.androidhive.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.Calendar;
import java.util.Date;


public class Graphs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 3.15),
                new DataPoint(d2, 2.67),
                new DataPoint(d3, 2.89),
                new DataPoint(d4, 3.46),
                new DataPoint(d5, 3.25)
        });

        series.setShape(PointsGraphSeries.Shape.POINT);

        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(Graphs.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d6.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        graph.setTitle("Progress");
        graph.setTitleTextSize(80);

        series.setTitle("Average Speed");

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Average Speed (m/s)");
        gridLabel.setHorizontalAxisTitle("Date");

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(4);

        graph.setBackgroundColor(getResources().getColor(android.R.color.background_light));

        series.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(Graphs.this, "Average Speed / Date = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}