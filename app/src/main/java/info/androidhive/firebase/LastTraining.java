package info.androidhive.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class LastTraining extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_training);

        ArrayList<String> fsr1 =getIntent().getExtras().getStringArrayList("list");
        ArrayList<String> fsr2 =getIntent().getExtras().getStringArrayList("fsr2");
        ArrayList<String> fsr3 =getIntent().getExtras().getStringArrayList("fsr3");
        ArrayList<String> velo =getIntent().getExtras().getStringArrayList("vel");

        GraphView graph = (GraphView) findViewById(R.id.graphLT);

        //Pressure on chest graph
        /*DataPoint[] points = new DataPoint[fsr3.size()];
        for(int i = 0; i < points.length; i++){
            int x = Integer.valueOf(fsr3.get(i));
            points[i] = new DataPoint(i, x);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        series.setDrawDataPoints(true);
        series.setThickness(3);
        series.setColor(Color.BLACK);
        series.setDataPointsRadius(5);

        graph.addSeries(series);*/


        //Average pressure on seat graph
        DataPoint[] points2 = new DataPoint[fsr1.size()];
        for(int i = 0; i < points2.length; i++){
            int x = Integer.valueOf(fsr1.get(i));
            points2[i] = new DataPoint(i, x);
        }

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(points2);


        series2.setDrawDataPoints(true);
        series2.setThickness(3);
        series2.setColor(Color.BLUE);
        series2.setDataPointsRadius(5);

        graph.addSeries(series2);

        //Speed graph
        /*DataPoint[] points3 = new DataPoint[velo.size()];
        for(int i = 0; i < points3.length; i++){
            double x = Double.valueOf(velo.get(i));
            points3[i] = new DataPoint(i, x);
        }

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(points3);

        series3.setDrawDataPoints(true);
        series3.setThickness(3);
        series3.setColor(Color.RED);
        series3.setDataPointsRadius(5);

        graph.addSeries(series3);*/

        graph.setTitle("Training");
        graph.setTitleTextSize(60);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);

        GridLabelRenderer gridLabel2 = graph.getGridLabelRenderer();
        gridLabel2.setHorizontalAxisTitle("Time (s)");

        //series.setTitle("ChestPres");
        series2.setTitle("SeatPres");
        //series3.setTitle("Speed");

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.setBackgroundColor(getResources().getColor(android.R.color.background_light));

        /*series.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(LastTraining.this, "Chest Pressure / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });*/

        series2.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series2, DataPointInterface dataPoint) {
                Toast.makeText(LastTraining.this, "Seat Pressure / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        /*series3.setOnDataPointTapListener(new OnDataPointTapListener(){
            @Override
            public void onTap(Series series3, DataPointInterface dataPoint) {
                Toast.makeText(LastTraining.this, "Speed / Time = " +dataPoint, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
