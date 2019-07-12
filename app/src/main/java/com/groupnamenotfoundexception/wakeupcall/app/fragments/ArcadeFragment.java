package com.groupnamenotfoundexception.wakeupcall.app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.groupnamenotfoundexception.wakeupcall.app.R;

public class ArcadeFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";

    private int position;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Position
     * @return A new instance of fragment ArcadeFragment.
     */
    public static ArcadeFragment newInstance(int position) {
        ArcadeFragment fragment = new ArcadeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public ArcadeFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_arcade, container, false);
        ((TextView) rootView.findViewById(R.id.highscore)).setText("Highscore: "
                + getActivity().getSharedPreferences(getActivity().getString(R.string.alarms_master), Context.MODE_PRIVATE)
                .getInt(getActivity().getString(R.string.highscore), 0));
        return rootView;
    }

}
