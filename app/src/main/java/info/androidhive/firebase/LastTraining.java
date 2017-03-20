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


public class LastTraining extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_training);

        GraphView graph = (GraphView) findViewById(R.id.graphLT);

        //Pressure on chest graph

        /*DataPoint[] points = new DataPoint[1000];
        for(int i = 0; i < points.length; i++){
            points[i] = new DataPoint(i, 37);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);*/

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 46),
                new DataPoint(2, 97),
                new DataPoint(3, 104),
                new DataPoint(4, 88),
                new DataPoint(5, 64),
                new DataPoint(6, 83),
                new DataPoint(7, 95),
                new DataPoint(8, 113),
                new DataPoint(9, 106),
                new DataPoint(10, 89),
                new DataPoint(11, 93),
                new DataPoint(12, 78),
                new DataPoint(13, 82),
                new DataPoint(14, 66),
                new DataPoint(15, 58),
                new DataPoint(16, 74),
                new DataPoint(17, 87),
                new DataPoint(18, 91),
                new DataPoint(19, 94),
                new DataPoint(20, 30)
        });


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


        //Total pressure on seat graph

        /*DataPoint[] points2 = new DataPoint[1000];
        for(int i = 0; i < points2.length; i++){
            points2[i] = new DataPoint(i, 58);
        }

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(points2);*/

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 67),
                new DataPoint(2, 253),
                new DataPoint(3, 284),
                new DataPoint(4, 309),
                new DataPoint(5, 282),
                new DataPoint(6, 312),
                new DataPoint(7, 321),
                new DataPoint(8, 273),
                new DataPoint(9, 238),
                new DataPoint(10, 215),
                new DataPoint(11, 257),
                new DataPoint(12, 296),
                new DataPoint(13, 314),
                new DataPoint(14, 303),
                new DataPoint(15, 318),
                new DataPoint(16, 281),
                new DataPoint(17, 292),
                new DataPoint(18, 263),
                new DataPoint(19, 132),
                new DataPoint(20, 64)
        });



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

        /*DataPoint[] points3 = new DataPoint[1000];
        for(int i = 0; i < points3.length; i++){
            points3[i] = new DataPoint(i, 20);
        }

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(points3);*/

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 0.21),
                new DataPoint(2, 0.58),
                new DataPoint(3, 0.81),
                new DataPoint(4, 1.06),
                new DataPoint(5, 1.62),
                new DataPoint(6, 1.98),
                new DataPoint(7, 2.27),
                new DataPoint(8, 2.72),
                new DataPoint(9, 3.15),
                new DataPoint(10, 3.56),
                new DataPoint(11, 3.86),
                new DataPoint(12, 3.69),
                new DataPoint(13, 3.91),
                new DataPoint(14, 3.75),
                new DataPoint(15, 3.82),
                new DataPoint(16, 4.07),
                new DataPoint(17, 3.94),
                new DataPoint(18, 3.87),
                new DataPoint(19, 4.12),
                new DataPoint(20, 4.15)
        });

        graph.getSecondScale().addSeries(series3);
        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(5);

        series3.setDrawDataPoints(true);
        series3.setThickness(3);
        series3.setColor(Color.RED);
        series3.setDataPointsRadius(5);

        graph.setTitle("Last Training");
        graph.setTitleTextSize(60);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);


        GridLabelRenderer gridLabel2 = graph.getGridLabelRenderer();
        gridLabel2.setHorizontalAxisTitle("Time (s)");
        //gridLabel2.setVerticalAxisTitle("Force (N)");

        series.setTitle("Chest Force");
        series2.setTitle("Seat Force");
        series3.setTitle("Speed");

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(21);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.setBackgroundColor(getResources().getColor(android.R.color.background_light));

        series.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(LastTraining.this, "Chest Pressure / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        series2.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series2, DataPointInterface dataPoint) {
                Toast.makeText(LastTraining.this, "Seat Pressure / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        series3.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series3, DataPointInterface dataPoint) {
                Toast.makeText(LastTraining.this, "Speed / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
