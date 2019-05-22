package com.example.myapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IDTerminalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IDTerminalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IDTerminalFragment extends Fragment {



    private Context context;
    private DataBase mydb;



    private OnFragmentInteractionListener mListener;

    public IDTerminalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IDTerminalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IDTerminalFragment newInstance(String param1, String param2) {
        IDTerminalFragment fragment = new IDTerminalFragment( );
       return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments( ) != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        final View view = inflater.inflate(R.layout.fragment_id, container, false);

        final TextView textViewIDTerminal = (TextView) view.findViewById(R.id.textID);



        mydb=new DataBase(getContext());
        mydb.open();

        String SIdTerminal = new String();
        SIdTerminal = mydb.Select_IDTerminal();


        if(SIdTerminal == "0000000000" || SIdTerminal == null ){


            textViewIDTerminal.setText("N/A");
        }else{

            textViewIDTerminal.setText(SIdTerminal);
        }




        // Inflate the layout for this fragment
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
