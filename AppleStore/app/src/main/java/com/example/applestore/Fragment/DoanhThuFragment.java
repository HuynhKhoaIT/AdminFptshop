package com.example.applestore.Fragment;
import android.content.Context;
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

import com.example.applestore.APIService.APIService;
import com.example.applestore.R;
import com.example.applestore.Retrofit.RetrofitClient;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoanhThuFragment extends Fragment {
    private BarChart barChart;

    private Context context = getContext();
    private ArrayList<String> listDoanhThu;
    APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
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
        Call<ArrayList<String>> call = apiService.getDoanhThu();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if(response.isSuccessful())
                {
                    System.out.println("Lấy danh thu thành công");
                    listDoanhThu = new ArrayList<>();
                    listDoanhThu = response.body();
                    System.out.println(listDoanhThu.size());
                    int i = 1;
                    for(String s:listDoanhThu){
                        System.out.println(s + "-"+i);
                        doanhThuEntries.add(new BarEntry(i,Integer.parseInt(s)));
                        i++;
                    }
                    List<BarEntry> loiNhuanEntries = new ArrayList<>();

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

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }
}
