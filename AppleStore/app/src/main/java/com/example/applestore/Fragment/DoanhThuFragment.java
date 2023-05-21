package com.example.applestore.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.applestore.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class DoanhThuFragment extends Fragment {
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        barChart = view.findViewById(R.id.chart);
        createBarChart();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Sales");
        return view;
    }

    private void createBarChart() {
        List<BarEntry> doanhThuEntries = new ArrayList<>();
        doanhThuEntries.add(new BarEntry(1, 100)); // Tháng 1, doanh thu
        doanhThuEntries.add(new BarEntry(2, 150)); // Tháng 2, doanh thu
        doanhThuEntries.add(new BarEntry(3, 200)); // Tháng 3, doanh thu

        doanhThuEntries.add(new BarEntry(4, 100)); // Tháng 1, doanh thu
        doanhThuEntries.add(new BarEntry(5, 150)); // Tháng 2, doanh thu
        doanhThuEntries.add(new BarEntry(6, 200)); // Tháng 3, doanh thu


        List<BarEntry> loiNhuanEntries = new ArrayList<>();
        loiNhuanEntries.add(new BarEntry(1, 50)); // Tháng 1, lợi nhuận
        loiNhuanEntries.add(new BarEntry(2, 75)); // Tháng 2, lợi nhuận
        loiNhuanEntries.add(new BarEntry(3, 100)); // Tháng 3, lợi nhuận
        loiNhuanEntries.add(new BarEntry(4, 50)); // Tháng 1, lợi nhuận
        loiNhuanEntries.add(new BarEntry(5, 75)); // Tháng 2, lợi nhuận
        loiNhuanEntries.add(new BarEntry(6, 100)); // Tháng 3, lợi nhuận

        BarDataSet doanhThuDataSet = new BarDataSet(doanhThuEntries, "Doanh thu");
        doanhThuDataSet.setColor(Color.BLUE);

        BarDataSet loiNhuanDataSet = new BarDataSet(loiNhuanEntries, "Lợi nhuận");
        loiNhuanDataSet.setColor(Color.GREEN);

        BarData barData = new BarData(doanhThuDataSet, loiNhuanDataSet);
        barData.setBarWidth(0.3f); // Độ rộng của các cột

        // Thiết lập giá trị của trục x (tháng)
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt trục x ở dưới biểu đồ
        xAxis.setDrawGridLines(false); // Vô hiệu hóa các đường gridline
        xAxis.setGranularity(1f); // Đặt khoảng cách giữa các giá trị là 1 (tháng)
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int month = (int) value; // Chuyển đổi giá trị float về int và cộng thêm 1 để biểu diễn tháng
                return "Tháng " + month;
            }
        });
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
}
