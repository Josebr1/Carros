package br.com.android.google.carros.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import org.parceler.Parcels;

import br.com.android.google.carros.R;
import br.com.android.google.carros.domain.Carro;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {


    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        VideoView videoView = (VideoView) view.findViewById(R.id.videoView);
        Carro carro = Parcels.unwrap(getArguments().getParcelable("carro"));
        if (carro != null) {
            videoView.setVideoURI(Uri.parse(carro.urlVideo));
            videoView.setMediaController(new MediaController(getContext()));
            videoView.start();
            Toast.makeText(getActivity(), "Mapa" + carro.urlVideo, Toast.LENGTH_SHORT).show();
        }

        return view;
    }

}
