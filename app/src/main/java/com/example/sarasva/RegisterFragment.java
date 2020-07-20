package com.example.sarasva;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    EditText Name,Mail,Contact;
    Button Register;
    ScrollView scrollView;
    CheckBox Paytm,PhonePay,Cash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        scrollView = view.findViewById(R.id.scrollViewFragment);
        Name = view.findViewById(R.id.nameApplicant);
        Mail = view.findViewById(R.id.mailApplicant);
        Contact = view.findViewById(R.id.contactApplicant);
        Register = view.findViewById(R.id.btnRegister);
        Paytm = view.findViewById(R.id.checkboxPayTm);
        PhonePay = view.findViewById(R.id.checkboxPhonePay);
        Cash = view.findViewById(R.id.checkboxCash);
        scrollView.isSmoothScrollingEnabled();
        scrollView.setEnabled(false);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(Name.getText().toString()) && !TextUtils.isEmpty(Mail.getText().toString()) && !TextUtils.isEmpty(Contact.getText().toString())){
                    scrollView.setEnabled(true);
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }else{
                    Toast.makeText(getActivity(), "All Parameters Required!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


}
