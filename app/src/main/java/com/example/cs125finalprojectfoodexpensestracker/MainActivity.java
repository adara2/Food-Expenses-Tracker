package com.example.cs125finalprojectfoodexpensestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class   MainActivity extends AppCompatActivity {

    private FloatingActionButton customizeBudget;
    private FloatingActionButton food;
    private FloatingActionButton addNewExpense;
    private FloatingActionButton viewExpenses;
    private Button submitNewExpense;
    private Button cancelNewExpense;

    private TextView currentDailyBudget;
    private TextView currentWeeklyBudget;
    private TextView currentMonthlyBudget;
    private TextView currentYearlyBudget;
    private TextView foodName;
    private TextView foodDesc;
    private TextView foodPrice;
    private TextView textbox1;
    private TextView textbox2;
    private TextView textbox3;
    private BarChart barchart;
    private TextView remainingBudget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        remainingBudget = findViewById(R.id.remainingBudget);
        barchart = (BarChart) findViewById(R.id.barchart);
        barchart.setDrawBarShadow(false);
        barchart.setDrawValueAboveBar(true);
        barchart.setPinchZoom(false);
        barchart.setDrawGridBackground(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, budget_customization.getDailyBudget()));
        barEntries.add(new BarEntry(2, budget_customization.getWeeklyBudget()));
        barEntries.add(new BarEntry(3, budget_customization.getMonthlyBudget()));
        barEntries.add(new BarEntry(4, budget_customization.getYearlyBudget()));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Day/Week/Month/Year");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(.9f);
        barchart.setData(data);

        String[] time = new String[]{"", "", "", "", "", ""};
        XAxis xAxis = barchart.getXAxis();
        xAxis.setValueFormatter(new XAxisFormat(time));



        currentDailyBudget = findViewById(R.id.currentDailyBudget);
        currentWeeklyBudget = findViewById(R.id.currentWeeklyBudget);
        currentMonthlyBudget = findViewById(R.id.currentMonthlyBudget);
        currentYearlyBudget = findViewById(R.id.currentYearlyBudget);
        foodName = findViewById(R.id.nameChunk);
        foodDesc = findViewById(R.id.foodDesc);
        foodPrice = findViewById(R.id.foodPrice);

        textbox1 = findViewById(R.id.textbox1);
        textbox2 = findViewById(R.id.textbox2);
        textbox3 = findViewById(R.id.textbox3);

        foodName.setVisibility(View.GONE);
        foodDesc.setVisibility(View.GONE);
        foodPrice.setVisibility(View.GONE);
        textbox1.setVisibility(View.GONE);
        textbox2.setVisibility(View.GONE);
        textbox3.setVisibility(View.GONE);


        currentDailyBudget.setText("Daily: $" + (budget_customization.getDailyBudget()));
        currentWeeklyBudget.setText("Weekly: $" + (budget_customization.getWeeklyBudget()));
        currentMonthlyBudget.setText("Monthly: $" + (budget_customization.getMonthlyBudget()));
        currentYearlyBudget.setText("Yearly: $" + (budget_customization.getYearlyBudget()));


        customizeBudget = findViewById(R.id.customizeBudget);
        customizeBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBudgetCustomization();
            }
        });

        food = findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRestaurant();
            }
        });

        submitNewExpense = findViewById(R.id.submitExpense);
        submitNewExpense.setVisibility(View.GONE);
        submitNewExpense.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                remainingBudget.setVisibility(View.VISIBLE);
                barchart.setVisibility(View.VISIBLE);
                textbox1.setVisibility(View.GONE);
                textbox2.setVisibility(View.GONE);
                textbox3.setVisibility(View.GONE);
                viewExpenses.setVisibility(View.VISIBLE);
                customizeBudget.setVisibility(View.VISIBLE);
                food.setVisibility(View.VISIBLE);
                viewExpenses.setVisibility(View.VISIBLE);
                addNewExpense.setVisibility(View.VISIBLE);
                currentDailyBudget.setVisibility(View.VISIBLE);
                currentWeeklyBudget.setVisibility(View.VISIBLE);
                currentMonthlyBudget.setVisibility(View.VISIBLE);
                currentYearlyBudget.setVisibility(View.VISIBLE);
                submitNewExpense.setVisibility(View.GONE);
                cancelNewExpense.setVisibility(View.GONE);
                foodName.setVisibility(View.GONE);
                foodDesc.setVisibility(View.GONE);
                foodPrice.setVisibility(View.GONE);
                String name = foodName.getText().toString();
                String desc = foodDesc.getText().toString();
                int price = Integer.parseInt(foodPrice.getText().toString());



                currentDailyBudget.setText("Remaining Daily Budget: $" + (budget_customization.getDailyBudget() - price));
                currentWeeklyBudget.setText("Remaining Weekly Budget: $" + (budget_customization.getWeeklyBudget() - price));
                currentMonthlyBudget.setText("Remaining Monthly Budget: $" + (budget_customization.getMonthlyBudget() - price));
                currentYearlyBudget.setText("Remaining Yearly Budget: $" + (budget_customization.getYearlyBudget() - price));


                Log.d("name", name);
                Log.d("desc", desc);
                Log.d("price", foodPrice.getText().toString());

                food_expenses_list.addNewExpense(name, desc, price);

            }
        });

        cancelNewExpense = findViewById(R.id.cancelExpense);
        cancelNewExpense.setVisibility(View.GONE);
        cancelNewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remainingBudget.setVisibility(View.VISIBLE);
                barchart.setVisibility(View.VISIBLE);
                textbox1.setVisibility(View.GONE);
                textbox2.setVisibility(View.GONE);
                textbox3.setVisibility(View.GONE);
                viewExpenses.setVisibility(View.VISIBLE);
                customizeBudget.setVisibility(View.VISIBLE);
                food.setVisibility(View.VISIBLE);
                viewExpenses.setVisibility(View.VISIBLE);
                addNewExpense.setVisibility(View.VISIBLE);
                currentDailyBudget.setVisibility(View.VISIBLE);
                currentWeeklyBudget.setVisibility(View.VISIBLE);
                currentMonthlyBudget.setVisibility(View.VISIBLE);
                currentYearlyBudget.setVisibility(View.VISIBLE);
                submitNewExpense.setVisibility(View.GONE);
                cancelNewExpense.setVisibility(View.GONE);
                foodName.setVisibility(View.GONE);
                foodDesc.setVisibility(View.GONE);
                foodPrice.setVisibility(View.GONE);
            }
        });

        addNewExpense = findViewById(R.id.newExpense);
        addNewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remainingBudget.setVisibility(View.GONE);
                barchart.setVisibility(View.GONE);
                textbox1.setVisibility(View.VISIBLE);
                textbox2.setVisibility(View.VISIBLE);
                textbox3.setVisibility(View.VISIBLE);
                viewExpenses.setVisibility(View.GONE);
                customizeBudget.setVisibility(View.GONE);
                food.setVisibility(View.GONE);
                viewExpenses.setVisibility(View.GONE);
                addNewExpense.setVisibility(View.GONE);
                currentDailyBudget.setVisibility(View.GONE);
                currentWeeklyBudget.setVisibility(View.GONE);
                currentMonthlyBudget.setVisibility(View.GONE);
                currentYearlyBudget.setVisibility(View.GONE);
                submitNewExpense.setVisibility(View.VISIBLE);
                cancelNewExpense.setVisibility(View.VISIBLE);
                foodName.setVisibility(View.VISIBLE);
                foodDesc.setVisibility(View.VISIBLE);
                foodPrice.setVisibility(View.VISIBLE);
            }
        });

        viewExpenses = findViewById(R.id.expensesList);
        viewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpensesList();
            }
        });
    }
    public class XAxisFormat implements IAxisValueFormatter {
        private String[] values;

        public XAxisFormat(String[] setValues) {
            this.values = setValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return values[(int)value];
        }
    }





    public void openBudgetCustomization() {
        Intent intent = new Intent(this, budget_customization.class);
        startActivity(intent);
    }

    public void openRestaurant() {
        Intent intent = new Intent(this, restaurant.class);
        startActivity(intent);
    }

    public void openExpensesList() {
        Intent intent = new Intent(this, food_expenses_list.class);
        startActivity(intent);
    }
}


