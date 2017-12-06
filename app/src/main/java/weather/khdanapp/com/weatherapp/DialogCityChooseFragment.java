package weather.khdanapp.com.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class DialogCityChooseFragment extends DialogFragment implements View.OnClickListener {

    EditText editText;
    private OnFragmentInteractionListener mListener;

    public DialogCityChooseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DialogCityChooseFragment newInstance() {
        DialogCityChooseFragment fragment = new DialogCityChooseFragment();
        return fragment;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.show_weather_dialog) {
            onButtonPressed(editText.getText().toString());
            getDialog().cancel();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        int style = DialogFragment.STYLE_NORMAL, theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_city_choose, container, false);

        getDialog().setTitle("Добавьте город на анг");

        Button b = (Button) view.findViewById(R.id.show_weather_dialog);
        b.setOnClickListener(this);

        editText = (EditText) view.findViewById(R.id.edit_text_dialog);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String str) {
        if (mListener != null) {
            mListener.onFragmentInteraction(str);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String str);
    }
}
