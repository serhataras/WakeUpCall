package com.groupnamenotfoundexception.wakeupcall.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.adapters.StatsAdapter;
import lecho.lib.hellocharts.view.LineChartView;

public class StatsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";

    private int position;

    private LineChartView lineChartViewSound, lineChartViewMovement;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Position
     * @return A new instance of fragment StatsFragment.
     */
    public static StatsFragment newInstance(int position) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        RecyclerView stats = (RecyclerView) view.findViewById(R.id.stats);
        stats.setLayoutManager(new LinearLayoutManager(getActivity()));
        stats.setHasFixedSize(true);
        StatsAdapter adapter = new StatsAdapter(getActivity());
        stats.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
