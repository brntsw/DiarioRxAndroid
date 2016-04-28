package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Adapter.LancamentoAvaliacaoAdapter;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Avaliacao;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;

/**
 * Created by techresult on 28/08/2015.
 */
public class FragmentAvaliacaoLista extends Fragment {

    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private ArrayList<Aluno> listaAlunos;
    private LancamentoAvaliacaoAdapter adapter;
    private ListView lvLancamento;
    private Avaliacao avaliacao;
    private boolean flag;

    public FragmentLancamentoAvaliacaoPager fragmentLancamentoAvaliacaoPager;

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentLancamentoAvaliacaoPager.refreshLista();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        appCompatActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(null);
            toolbar.setTitle(R.string.avaliacao);
            toolbar.getMenu().clear();
            toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha, null));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appCompatActivity.onBackPressed();
                }
            });
        }

        Bundle bundle = getArguments();

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_lancamento_lista, container, false);

        final Bundle state = savedInstanceState;

        listaAlunos = (ArrayList<Aluno>) bundle.getSerializable("listaAlunos");
        avaliacao = (Avaliacao) bundle.getSerializable("avaliacao");
        flag = bundle.getBoolean("flag");

        adapter = new LancamentoAvaliacaoAdapter(activity, listaAlunos, avaliacao, fragmentLancamentoAvaliacaoPager);
        //Lista de alunos
        lvLancamento = (ListView) layout.findViewById(R.id.lv_lancamento);
        lvLancamento.setAdapter(adapter);

        return layout;
    }

}
