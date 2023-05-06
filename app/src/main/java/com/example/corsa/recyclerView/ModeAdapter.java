package com.example.corsa.recyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.corsa.modes.AccelCompActivity.ACCEL_COMP_PREFS;
import static com.example.corsa.modes.CarGuessActivity.CAR_GUESS_PREFS;
import static com.example.corsa.modes.NurbCompActivity.NURB_COMP_PREFS;
import static com.example.corsa.modes.PowerCompActivity.POWER_COMP_PREFS;
import static com.example.corsa.modes.ProductionGuessActivity.PROD_GUESS_PREFS;
import static com.example.corsa.modes.RandomActivity.RANDOM_PREFS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.diffRecyclerView.Diff;
import com.example.corsa.diffRecyclerView.DiffAdapter;
import com.example.corsa.modes.AccelCompActivity;
import com.example.corsa.modes.CarGuessActivity;
import com.example.corsa.modes.NurbCompActivity;
import com.example.corsa.modes.PowerCompActivity;
import com.example.corsa.modes.PowerGuessActivity;
import com.example.corsa.modes.ProductionGuessActivity;
import com.example.corsa.R;
import com.example.corsa.modes.RandomActivity;
import com.example.corsa.Utils;

import java.util.ArrayList;
import java.util.List;

public class ModeAdapter extends RecyclerView.Adapter<ModeViewHolder> {

    Context context;
    List<Mode> modes;
    public static final String PREFS_NAME = "MyPrefs";

    public ModeAdapter(Context context, List<Mode> modes) {
        this.context = context;
        this.modes = modes;
    }

    @NonNull
    @Override
    public ModeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModeViewHolder(LayoutInflater.from(context).inflate(R.layout.view_mode_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ModeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.icon.setImageResource(modes.get(position).icon);
        holder.name.setText(modes.get(position).name);
        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, modes.get(position).textSize);
        holder.name.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        ImageView button = holder.itemView.findViewById(R.id.difficulty_view_card);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(context).inflate(R.layout.view_diff_window, null);
                PopupWindow popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);

                Button dismiss = popupView.findViewById(R.id.dismiss_diff_window);

                ArrayList<Diff> diffs;
//                RecyclerView recyclerView = popupView.findViewById(R.id.diff_recycler_view);

                TextView title = popupView.findViewById(R.id.title_diff_window);
                title.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_bold));

                switch (modes.get(position).name) {
                    case "Guess The Car":
                        diffs = new ArrayList<>();
                        diffs.add(new Diff("BEGINNER", 1, "HID.", "20", "20", "%"));
                        diffs.add(new Diff("SKILLED", 2, "HID.", "50", "50", "%"));
                        diffs.add(new Diff("HARD", 5, "HID.", "70", "70", "%"));
                        diffs.add(new Diff("EXTREME", 10, "HID.", "90", "90", "%"));

                        RecyclerView recyclerView = popupView.findViewById(R.id.diff_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new DiffAdapter(context, diffs));

                        ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener1 = new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                for (int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++) {
                                    View view = recyclerView.getLayoutManager().getChildAt(i);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            TextView hidd = view.findViewById(R.id.bound1_menu_card);
                                            TextView xps = view.findViewById(R.id.xp_menu_card);
                                            String hid = hidd.getText().toString();
                                            String xp = xps.getText().toString();
                                            SharedPreferences sharedPreferences = context.getSharedPreferences(CAR_GUESS_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("hid", hid);
                                            editor.putString("xp", xp);
                                            editor.apply();
                                            Utils.dismiss(popupWindow, popupView);
                                        }
                                    });
                                }
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        };
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener1);
                        break;
                    case "Acceleration Comparison":
                        diffs = new ArrayList<>();
                        diffs.add(new Diff("BEGINNER", 1, "DIFF.", "3.0", "5.0", "s"));
                        diffs.add(new Diff("SKILLED", 2, "DIFF.", "1.5", "3.0", "s"));
                        diffs.add(new Diff("HARD", 5, "DIFF.", "0.5", "1.0", "s"));
                        diffs.add(new Diff("EXTREME", 10, "DIFF.", "0.1", "0.5", "s"));

                        recyclerView = popupView.findViewById(R.id.diff_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new DiffAdapter(context, diffs));

                        ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener2 = new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                for (int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++) {
                                    View view = recyclerView.getLayoutManager().getChildAt(i);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            TextView b1 = view.findViewById(R.id.bound1_menu_card);
                                            TextView b2 = view.findViewById(R.id.bound2_menu_card);
                                            TextView xps = view.findViewById(R.id.xp_menu_card);
                                            String bound1 = b1.getText().toString();
                                            String bound2 = b2.getText().toString();
                                            String xp = xps.getText().toString();
                                            SharedPreferences sharedPreferences = context.getSharedPreferences(ACCEL_COMP_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("b1", bound1);
                                            editor.putString("b2", bound2);
                                            editor.putString("xp", xp);
                                            editor.apply();
                                            Utils.dismiss(popupWindow, popupView);
                                        }
                                    });
                                }
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        };
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener2);
                        break;

                    case "Nurburgring Comparison":
                        diffs = new ArrayList<>();
                        diffs.add(new Diff("BEGINNER", 1, "DIFF.", "20", "40", "s"));
                        diffs.add(new Diff("SKILLED", 2, "DIFF.", "10", "20", "s"));
                        diffs.add(new Diff("HARD", 5, "DIFF.", "5", "10", "s"));
                        diffs.add(new Diff("EXTREME", 10, "DIFF.", "1", "5", "s"));

                        recyclerView = popupView.findViewById(R.id.diff_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new DiffAdapter(context, diffs));

                        ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener3 = new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                for (int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++) {
                                    View view = recyclerView.getLayoutManager().getChildAt(i);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            TextView b1 = view.findViewById(R.id.bound1_menu_card);
                                            TextView b2 = view.findViewById(R.id.bound2_menu_card);
                                            TextView xps = view.findViewById(R.id.xp_menu_card);
                                            String bound1 = b1.getText().toString();
                                            String bound2 = b2.getText().toString();
                                            String xp = xps.getText().toString();
                                            SharedPreferences sharedPreferences = context.getSharedPreferences(NURB_COMP_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("b1", bound1);
                                            editor.putString("b2", bound2);
                                            editor.putString("xp", xp);
                                            editor.apply();
                                            Utils.dismiss(popupWindow, popupView);
                                        }
                                    });
                                }
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        };
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener3);
                        break;

                    case "Power Comparison":
                        diffs = new ArrayList<>();
                        diffs.add(new Diff("BEGINNER", 1, "DIFF.", "50", "70", "HP"));
                        diffs.add(new Diff("SKILLED", 2, "DIFF.", "20", "50", "HP"));
                        diffs.add(new Diff("HARD", 5, "DIFF.", "5", "20", "HP"));
                        diffs.add(new Diff("EXTREME", 10, "DIFF.", "1", "5", "HP"));

                        recyclerView = popupView.findViewById(R.id.diff_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new DiffAdapter(context, diffs));

                        ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener4 = new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                for (int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++) {
                                    View view = recyclerView.getLayoutManager().getChildAt(i);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            TextView b1 = view.findViewById(R.id.bound1_menu_card);
                                            TextView b2 = view.findViewById(R.id.bound2_menu_card);
                                            TextView xps = view.findViewById(R.id.xp_menu_card);
                                            String bound1 = b1.getText().toString();
                                            String bound2 = b2.getText().toString();
                                            String xp = xps.getText().toString();
                                            SharedPreferences sharedPreferences = context.getSharedPreferences(POWER_COMP_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("b1", bound1);
                                            editor.putString("b2", bound2);
                                            editor.putString("xp", xp);
                                            editor.apply();
                                            Utils.dismiss(popupWindow, popupView);
                                        }
                                    });
                                }
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        };
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener4);

                        break;

                    case "Guess The Power":
                        diffs = new ArrayList<>();
                        diffs.add(new Diff("BEGINNER", 1, "DIFF.", "50", "70", "HP"));
                        diffs.add(new Diff("SKILLED", 2, "DIFF.", "20", "50", "HP"));
                        diffs.add(new Diff("HARD", 5, "DIFF.", "5", "20", "HP"));
                        diffs.add(new Diff("EXTREME", 10, "DIFF.", "0", "5", "HP"));

                        recyclerView = popupView.findViewById(R.id.diff_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new DiffAdapter(context, diffs));

                        title.setText("XP CHART");
                        break;

                    case "Guess The Start Of Production":
                        diffs = new ArrayList<>();
                        diffs.add(new Diff("BEGINNER", 1, "DIFF.", "-15", "15", "Y"));
                        diffs.add(new Diff("SKILLED", 2, "DIFF.", "-10", "10", "Y"));
                        diffs.add(new Diff("HARD", 5, "DIFF.", "-5", "5", "Y"));
                        diffs.add(new Diff("EXTREME", 10, "DIFF.", "-2", "2", "Y"));

                        recyclerView = popupView.findViewById(R.id.diff_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new DiffAdapter(context, diffs));

                        ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener6 = new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                for (int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++) {
                                    View view = recyclerView.getLayoutManager().getChildAt(i);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            TextView b1 = view.findViewById(R.id.bound1_menu_card);
                                            TextView b2 = view.findViewById(R.id.bound2_menu_card);
                                            String bound1 = b1.getText().toString();
                                            String bound2 = b2.getText().toString();
                                            SharedPreferences sharedPreferences = context.getSharedPreferences(PROD_GUESS_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("b1", bound1);
                                            editor.putString("b2", bound2);
                                            editor.apply();
                                            Utils.dismiss(popupWindow, popupView);
                                        }
                                    });
                                }
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        };
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener6);

                        break;

                    case "Random":
                        diffs = new ArrayList<>();
                        diffs.add(new Diff("BEGINNER", 1, "DIFF.", "-", "-", "UnT"));
                        diffs.add(new Diff("SKILLED", 2, "DIFF.", "-", "-", "UnT"));
                        diffs.add(new Diff("HARD", 5, "DIFF.", "-", "-", "UnT"));
                        diffs.add(new Diff("EXTREME", 10, "DIFF.", "-", "-", "UnT"));

                        recyclerView = popupView.findViewById(R.id.diff_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new DiffAdapter(context, diffs));

                        ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener7 = new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                for (int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++) {
                                    View view = recyclerView.getLayoutManager().getChildAt(i);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            TextView diffName = view.findViewById(R.id.diffName_menu_card);
                                            String diff = diffName.getText().toString();
                                            SharedPreferences sharedPreferences = context.getSharedPreferences(RANDOM_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("diff", diff);
                                            editor.apply();
                                            Utils.dismiss(popupWindow, popupView);
                                        }
                                    });
                                }
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        };
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener7);

                        break;
                }
                View parentView = holder.itemView.findViewById(R.id.button_view_card);

                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(300);
                popupView.startAnimation(fadeIn);

                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.dismiss(popupWindow, popupView);
                    }
                });

                popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.vibrate(context);
                holder.itemView.setEnabled(false);

                Intent intent;
                switch (modes.get(position).name) {
                    case "Guess The Car":
                        intent = new Intent(context, CarGuessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");

                        SharedPreferences prefs1 = context.getSharedPreferences(CAR_GUESS_PREFS, MODE_PRIVATE);
                        String value = prefs1.getString("hid", "");
                        intent.putExtra("hid", value);

                        context.startActivity(intent);
                        break;
                    case "Acceleration Comparison":
                        intent = new Intent(context, AccelCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");

                        SharedPreferences prefs2 = context.getSharedPreferences(ACCEL_COMP_PREFS, MODE_PRIVATE);
                        String bound1SP2 = prefs2.getString("b1", "");
                        String bound2SP2 = prefs2.getString("b2", "");
                        intent.putExtra("b1", bound1SP2);
                        intent.putExtra("b2", bound2SP2);

                        context.startActivity(intent);
                        break;
                    case "Nurburgring Comparison":
                        intent = new Intent(context, NurbCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");

                        SharedPreferences prefs3 = context.getSharedPreferences(NURB_COMP_PREFS, MODE_PRIVATE);
                        String bound1SP3 = prefs3.getString("b1", "");
                        String bound2SP3 = prefs3.getString("b2", "");
                        intent.putExtra("b1", bound1SP3);
                        intent.putExtra("b2", bound2SP3);

                        context.startActivity(intent);
                        break;
                    case "Power Comparison":
                        intent = new Intent(context, PowerCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");

                        SharedPreferences prefs4 = context.getSharedPreferences(POWER_COMP_PREFS, MODE_PRIVATE);
                        String bound1SP4 = prefs4.getString("b1", "");
                        String bound2SP4 = prefs4.getString("b2", "");
                        intent.putExtra("b1", bound1SP4);
                        intent.putExtra("b2", bound2SP4);

                        context.startActivity(intent);
                        break;
                    case "Guess The Power":
                        intent = new Intent(context, PowerGuessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");

                        context.startActivity(intent);
                        break;
                    case "Guess The Start Of Production":
                        intent = new Intent(context, ProductionGuessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");

                        SharedPreferences prefs6 = context.getSharedPreferences(PROD_GUESS_PREFS, MODE_PRIVATE);
                        String bound1SP6 = prefs6.getString("b1", "");
                        String bound2SP6 = prefs6.getString("b2", "");
                        intent.putExtra("b1", bound1SP6);
                        intent.putExtra("b2", bound2SP6);

                        context.startActivity(intent);
                        break;
                    case "Random":
                        intent = new Intent(context, RandomActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        SharedPreferences prefs7 = context.getSharedPreferences(RANDOM_PREFS, MODE_PRIVATE);
                        String difficulty = prefs7.getString("diff", "");
                        intent.putExtra("diff", difficulty);

                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }
}
