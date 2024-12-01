package com.example.dsa_visualizer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dsa_visualizer.HelperClasses.Bar;
import com.example.dsa_visualizer.HelperClasses.BarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bubble_sort extends AppCompatActivity {
    private List<Bar> bars;
    private boolean isSorting = false;
    private int speed = 100;
    private BarView barView;
    private Button generateButton, bubbleButton, selectionButton, insertionButton, quickButton, mergeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_sort);
        bars = new ArrayList<>();
        barView = findViewById(R.id.barView);

        generateButton = findViewById(R.id.generateButton);
        bubbleButton = findViewById(R.id.BubbleButton);
        selectionButton = findViewById(R.id.SelectionButton);
        insertionButton = findViewById(R.id.InsertionButton);
        quickButton = findViewById(R.id.QuickButton);
        mergeButton = findViewById(R.id.MergeButton);
        SeekBar speedSlider = findViewById(R.id.speedSlider);

        generateButton.setOnClickListener(v -> {
            if (!isSorting) {
                generateRandomBars();
            }
        });

        bubbleButton.setOnClickListener(v -> {
            if (!isSorting) {
                bubbleSort();
            }
        });
        selectionButton.setOnClickListener(v -> {
            if (!isSorting) {
                selectionSort();
            }
        });
        insertionButton.setOnClickListener(v -> {
            if (!isSorting) {
                insertionSort();
            }
        });
        quickButton.setOnClickListener(v -> {
            if (!isSorting) {
                startQuickSort();
            }
        });
        mergeButton.setOnClickListener(v -> {
            if (!isSorting) {
                startMergeSort();
            }
        });

        speedSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        generateRandomBars();
    }

    private void generateRandomBars() {
        bars.clear();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            bars.add(new Bar(random.nextInt(100), "#0000FF")); // Blue color
        }
        barView.setBars(bars);
    }

    // Define the highlightBars method
    private void highlightBars(int index1, int index2) {
        for (int i = 0; i < bars.size(); i++) {
            if (i == index1 || i == index2) {
                bars.get(i).color = "#FF0000"; // Red color for the bars being compared
            } else {
                bars.get(i).color = "#0000FF"; // Blue color for the other bars
            }
        }
        barView.setBars(bars);
        barView.invalidate(); // Redraw the custom view
    }

    private void disableButtons(boolean disable) {
        generateButton.setEnabled(!disable);
        bubbleButton.setEnabled(!disable);
        selectionButton.setEnabled(!disable);
        insertionButton.setEnabled(!disable);
        quickButton.setEnabled(!disable);
        mergeButton.setEnabled(!disable);
    }

    // Bubble Sort method
    private void bubbleSort() {
        isSorting = true;
        disableButtons(true);

        new Thread(() -> {
            for (int i = 0; i < bars.size(); i++) {
                for (int j = 0; j < bars.size() - i - 1; j++) {
                    if (bars.get(j).height > bars.get(j + 1).height) {
                        Bar temp = bars.get(j);
                        bars.set(j, bars.get(j + 1));
                        bars.set(j + 1, temp);
                    }

                    int finalJ = j;
                    runOnUiThread(() -> highlightBars(finalJ, finalJ + 1));

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            runOnUiThread(() -> {
                for (Bar bar : bars) {
                    bar.color = "#00FF00"; // Green color for sorted bars
                }
                barView.setBars(bars);
                barView.invalidate();
                isSorting = false;
                disableButtons(false);
            });
        }).start();
    }

    // Selection Sort method
    private void selectionSort() {
        isSorting = true;
        disableButtons(true);

        new Thread(() -> {
            for (int i = 0; i < bars.size(); i++) {
                int minIndex = i;
                for (int j = i + 1; j < bars.size(); j++) {
                    if (bars.get(j).height < bars.get(minIndex).height) {
                        minIndex = j;
                    }
                }

                if (minIndex != i) {
                    Bar temp = bars.get(i);
                    bars.set(i, bars.get(minIndex));
                    bars.set(minIndex, temp);
                }

                int finalI = i;
                int finalMinIndex = minIndex;
                runOnUiThread(() -> highlightBars(finalI, finalMinIndex));

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(() -> {
                for (Bar bar : bars) {
                    bar.color = "#00FF00"; // Green color for sorted bars
                }
                barView.setBars(bars);
                barView.invalidate();
                isSorting = false;
                disableButtons(false);
            });
        }).start();
    }

    // Insertion Sort method
    private void insertionSort()                                             {
        isSorting = true;
        disableButtons(true);

        new Thread(() -> {
            for (int i = 1; i < bars.size(); i++) {
                Bar key = bars.get(i);
                int j = i - 1;
                while (j >= 0 && bars.get(j).height > key.height) {
                    bars.set(j + 1, bars.get(j));
                    j--;
                }
                bars.set(j + 1, key);

                int finalJ = j;
                int finalI = i;
                runOnUiThread(() -> highlightBars(finalJ + 1, finalI));

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(() -> {
                for (Bar bar : bars) {
                    bar.color = "#00FF00"; // Green color for sorted bars
                }
                barView.setBars(bars);
                barView.invalidate();
                isSorting = false;
                disableButtons(false);
            });
        }).start();
    }

    private int partition(int low, int high) {
        Bar pivot = bars.get(high);  // Pivot element is the last one
        int i = (low - 1);  // Index of the smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to the pivot
            if (bars.get(j).height <= pivot.height) {
                i++;

                // Swap bars[i] and bars[j]
                Bar temp = bars.get(i);
                bars.set(i, bars.get(j));
                bars.set(j, temp);

                // Update UI to highlight bars being compared
                final int finalI = i, finalJ = j;
                runOnUiThread(() -> highlightBars(finalI, finalJ));

                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Swap bars[i + 1] and the pivot (bars[high])
        Bar temp = bars.get(i + 1);
        bars.set(i + 1, bars.get(high));
        bars.set(high, temp);

        return i + 1;
    }

    // Quick Sort method
    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            runOnUiThread(() -> highlightBars(low, pi));

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            quickSort(low, pi - 1); // Recursively sort the left half
            quickSort(pi + 1, high); // Recursively sort the right half
        }
    }

    public void startQuickSort() {
        isSorting = true;
        disableButtons(true);

        new Thread(() -> {
            quickSort(0, bars.size() - 1);
            runOnUiThread(() -> {
                for (Bar bar : bars) {
                    bar.color = "#00FF00"; // Green color for sorted bars
                }
                barView.setBars(bars);
                barView.invalidate();
                isSorting = false;
                disableButtons(false);
            });
        }).start();
    }

    // Merge Sort method
    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        List<Bar> leftArray = new ArrayList<>();
        List<Bar> rightArray = new ArrayList<>();

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray.add(bars.get(left + i));
        }
        for (int i = 0; i < n2; i++) {
            rightArray.add(bars.get(mid + 1 + i));
        }

        // Merge the temporary arrays
        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftArray.get(i).height <= rightArray.get(j).height) {
                bars.set(k, leftArray.get(i));
                i++;
            } else {
                bars.set(k, rightArray.get(j));
                j++;
            }

            // Update UI to highlight bars being merged
            final int finalK = k, finalI = i, finalJ = j;
            runOnUiThread(() -> highlightBars(finalK, finalI < n1 ? left + finalI : mid + 1 + finalJ));

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            k++;
        }

        // Copy remaining elements of leftArray if any
        while (i < n1) {
            bars.set(k, leftArray.get(i));
            i++;
            k++;

            runOnUiThread(() -> barView.invalidate());
        }

        // Copy remaining elements of rightArray if any
        while (j < n2) {
            bars.set(k, rightArray.get(j));
            j++;
            k++;

            runOnUiThread(() -> barView.invalidate());
        }
    }


    public void startMergeSort() {
        isSorting = true;
        disableButtons(true);

        new Thread(() -> {
            mergeSort(0, bars.size() - 1);
            runOnUiThread(() -> {
                for (Bar bar : bars) {
                    bar.color = "#00FF00"; // Green color for sorted bars
                }
                barView.setBars(bars);
                barView.invalidate();
                isSorting = false;
                disableButtons(false);
            });
        }).start();
    }
}
