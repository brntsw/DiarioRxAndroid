package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.R;

/**
 * Created by techresult on 24/08/2015.
 */
public class FragmentConsultaAvaliacao extends Fragment {

    private RelativeLayout layout;
    private TextView tvHorario;
    private LinearLayout linearHorario;
    private ListView lvConsulta;
    private Bundle bundle;
    private Turma turma;
    private String ano;
    private CharSequence[] sequence = {"1 bimestre","2 bimestre","3 bimestre","4 bimestre"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_consulta_avaliacao, container, false);

        bundle = getArguments();
        tvHorario = (TextView) layout.findViewById(R.id.tv_horario);
        linearHorario = (LinearLayout) layout.findViewById(R.id.linearHorario);
        lvConsulta = (ListView) layout.findViewById(R.id.lv_consulta);

        linearHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getResources().getString(R.string.periodo));
                builder.setItems(sequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CharSequence selectedValue = sequence[which];
                        tvHorario.setText(selectedValue);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        tvHorario.setText(sequence[0]);
        ano = bundle.getString("ano");
        turma = (Turma) bundle.getSerializable("turma");
        TextView tvTurma = (TextView) layout.findViewById(R.id.tv_turma);
        tvTurma.setText(turma.getNomeTurma() + " / " + turma.getNomeDisciplina());

        return layout;

    }
}
