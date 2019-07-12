package com.groupnamenotfoundexception.wakeupcall.app.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.activities.AlarmEditorActivity;
import com.groupnamenotfoundexception.wakeupcall.app.adapters.AlarmViewAdapter;
import com.groupnamenotfoundexception.wakeupcall.app.swipeToDismissExtension.SwipeToDismissTouchListener;
import com.groupnamenotfoundexception.wakeupcall.app.swipeToDismissExtension.SwipeableItemClickListener;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

/**
 * The Fragment that holds the alarms
 */
public class HomeFragment extends Fragment {
    /**
     * PROPERTIES
     */
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";

    // RecyclerView Properties
    RecyclerView alarmList;
    AlarmViewAdapter adapter;

    // Position of the fragment
    private int position;

    /**
     * CONSTRUCTORS
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    /** METHODS */

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Position
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates the view when called. Does the job of Constructor. Presented by Android, Overridden by Project Group
     *
     * @param savedInstanceState ,If the view has a saved instance - in this case there is none - loads this.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    /**
     * Called when fragment is reshown
     */
    @Override
    public void onResume() {
        super.onResume();
        // Resets the dataset
        adapter.changeDataSet();
        adapter.notifyDataSetChanged();
    }

    /**
     * When this is wanted to be shown this method will be called, set the View
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Prepared View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup RecyclerView
        alarmList = (RecyclerView) rootView.findViewById(R.id.alarmList);
        alarmList.setLayoutManager(new LinearLayoutManager(getActivity()));
        alarmList.setHasFixedSize(true);
        adapter = new AlarmViewAdapter(getActivity());
        alarmList.setAdapter(adapter);

        // Setup SwipeToDismiss Library
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<RecyclerViewAdapter>(
                        new RecyclerViewAdapter(alarmList),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                adapter.remove(position);
                            }
                        });

        // Assign it
        alarmList.setOnTouchListener(touchListener);

        // If any of the item is clicked
        alarmList.addOnItemTouchListener(new SwipeableItemClickListener(getActivity(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // Deleted
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        }
                        // Undo
                        else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                            touchListener.removeCallback();
                        }
                        // Repeat Days - Mon
                        else if (view.getId() == R.id.mon) {
                            view.setEnabled(!view.isEnabled());
                            adapter.getSharedPreferences(position).edit().putBoolean(getActivity().getString(R.string.monday), view.isEnabled()).commit();
                        }
                        // Repeat Days - Tue
                        else if (view.getId() == R.id.tue) {
                            view.setEnabled(!view.isEnabled());
                            adapter.getSharedPreferences(position).edit().putBoolean(getActivity().getString(R.string.tuesday), view.isEnabled()).commit();
                        }
                        // Repeat Days - Wed
                        else if (view.getId() == R.id.wed) {
                            view.setEnabled(!view.isEnabled());
                            adapter.getSharedPreferences(position).edit().putBoolean(getActivity().getString(R.string.wednesday), view.isEnabled()).commit();
                        }
                        // Repeat Days - Thu
                        else if (view.getId() == R.id.thu) {
                            view.setEnabled(!view.isEnabled());
                            adapter.getSharedPreferences(position).edit().putBoolean(getActivity().getString(R.string.thursday), view.isEnabled()).commit();
                        }
                        // Repeat Days - Fri
                        else if (view.getId() == R.id.fri) {
                            view.setEnabled(!view.isEnabled());
                            adapter.getSharedPreferences(position).edit().putBoolean(getActivity().getString(R.string.friday), view.isEnabled()).commit();
                        }
                        // Repeat Days - Sat
                        else if (view.getId() == R.id.sat) {
                            view.setEnabled(!view.isEnabled());
                            adapter.getSharedPreferences(position).edit().putBoolean(getActivity().getString(R.string.saturday), view.isEnabled()).commit();
                        }
                        // Repeat Days - Sun
                        else if (view.getId() == R.id.sun) {
                            view.setEnabled(!view.isEnabled());
                            adapter.getSharedPreferences(position).edit().putBoolean(getActivity().getString(R.string.sunday), view.isEnabled()).commit();
                        }
                        // Disable Switch Action
                        else if (view.getId() == R.id.isEnabled) {
                            // Needed to prevent opening Alarm Editor when it is not wanted or needed
                        }
                        else if (view.getId() == R.id.undoView){
                            // Needed to prevent opening Alarm Editor when it is not wanted or needed
                        }
                        // The Card is clicked
                        else {
                            Intent i = new Intent(getActivity(), AlarmEditorActivity.class);
                            i.putExtra(getString(R.string.alarm_index), adapter.getAlarmIndex(position));
                            startActivity(i);
                        }
                    }
                }));
        return rootView;
    }
}
