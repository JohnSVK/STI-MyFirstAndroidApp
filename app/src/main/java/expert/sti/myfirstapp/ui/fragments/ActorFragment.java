package expert.sti.myfirstapp.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import expert.sti.myfirstapp.R;
import expert.sti.myfirstapp.entities.Actor;
import expert.sti.myfirstapp.presenters.ActorFragmentPresenter;
import expert.sti.myfirstapp.presenters.IActorFragmentPresenter;
import expert.sti.myfirstapp.ui.adapters.ActorAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ActorFragment extends Fragment {
    private static final String ARG_POSITION = "Position";

    private int mPosition;
    private IActorFragmentPresenter mPresenter;

    @BindView(R.id.list_view) ListView mListView;

    public ActorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Fragment position in tab layout
     * @param
     * @return A new instance of fragment ActorFragment.
     */

    public static ActorFragment newInstance(int position) {
        ActorFragment fragment = new ActorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_actor, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new ActorFragmentPresenter(this, mPosition);
        mPresenter.start();

        return view;
    }

    public void onWSResponse(ArrayList<Actor> actors) {
        ActorAdapter adapter = new ActorAdapter(getActivity(), actors);
        if(mListView == null)
            return;
        mListView.setAdapter(adapter);
    }

    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
