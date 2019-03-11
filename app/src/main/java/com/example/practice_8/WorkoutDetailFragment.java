package com.example.practice_8;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;

//Класс расширяет класс Fragment системы Android.
public class WorkoutDetailFragment extends Fragment {

    //Идентификатор комплекса упражнений, выбранного пользователем. Позднее, при выводе
    //подробной информации, он будет использован для заполнения представлений фрагмента.
    private long workoutId;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getLong("workoutId");
        } else {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopwatchFragment stopwatchFragment = new StopwatchFragment();
            ft.replace(R.id.stopwatch_container, stopwatchFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Сообщает Android, какой макет используется фрагментом (в данном случае fragment_workout_detail).
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Метод getView() получает корневой объект View фрагмента. Далее полученный
        //объект используется для полуения ссылок
        //на надписи, предназначенные для названия
        //и описания комплекса упражнений.
        View view = getView();
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            Workout workout = Workout.workouts[(int) workoutId]; title.setText(workout.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("workoutId", workoutId);
    }

    //Метод для присваивания идентификатора. Метод используется активностью для передачи значения идентификатора фрагменту.
    public void setWorkout(long id) {
        this.workoutId = id;
    }
}