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
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;


public class Graphs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        //Pressure on chest graph
        DataPoint[] points = new DataPoint[1000];
        for(int i = 0; i < points.length; i++){
            points[i] = new DataPoint(i, 37);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        series.setDrawDataPoints(true);
        series.setThickness(3);
        series.setColor(Color.BLACK);
        series.setDataPointsRadius(5);

        graph.addSeries(series);

        graph.setTitle("Training");
        graph.setTitleTextSize(60);

        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (s)");


        //Avarage pressure on seat graph
        DataPoint[] points2 = new DataPoint[1000];
        for(int i = 0; i < points2.length; i++){
            points2[i] = new DataPoint(i, 58);
        }

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(points2);


        series2.setDrawDataPoints(true);
        series2.setThickness(3);
        series2.setColor(Color.BLUE);
        series2.setDataPointsRadius(5);

        graph.addSeries(series2);

        graph.setTitle("Training");
        graph.setTitleTextSize(60);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);


        //Speed graph
        DataPoint[] points3 = new DataPoint[1000];
        for(int i = 0; i < points3.length; i++){
            points3[i] = new DataPoint(i, 20);
        }

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(points3);


        series3.setDrawDataPoints(true);
        series3.setThickness(3);
        series3.setColor(Color.RED);
        series3.setDataPointsRadius(5);

        graph.addSeries(series3);

        graph.setTitle("Training");
        graph.setTitleTextSize(60);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);



        GridLabelRenderer gridLabel2 = graph.getGridLabelRenderer();
        gridLabel2.setHorizontalAxisTitle("Time (s)");

        series.setTitle("ChestPres");
        series2.setTitle("SeatPres");
        series3.setTitle("Speed");

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.setBackgroundColor(getResources().getColor(android.R.color.background_light));

        series.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(Graphs.this, "Chest Pressure / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        series2.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series2, DataPointInterface dataPoint) {
                Toast.makeText(Graphs.this, "Seat Pressure / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        series3.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series3, DataPointInterface dataPoint) {
                Toast.makeText(Graphs.this, "Speed / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}