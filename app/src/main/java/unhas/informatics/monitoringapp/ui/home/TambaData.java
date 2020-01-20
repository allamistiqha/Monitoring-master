package unhas.informatics.monitoringapp.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import unhas.informatics.monitoringapp.R;

public class TambaData extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tamba_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button save;
        save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });

    }


}
