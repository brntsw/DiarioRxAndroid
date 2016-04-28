package br.gov.sp.educacao.sed.mobile.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Adapter.LancamentoAlunoAdapter;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.Modelo.Aula;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;

public class FragmentLancamentoLista extends Fragment {

    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private LancamentoAlunoAdapter adapter;
    private Spinner dias;
    private ListView lvLancamento;
    private TextView tvDia, tvTurma;
    private int diasMes;
    private ArrayList<String> disableDateStrings;
    public FragmentLancamentoFrequenciaPager fragmentLancamentoFrequenciaPager;
    private ArrayList<Aluno> listaAlunos;
    private ArrayList<Aula> listaAula;
    private String data, hora;

    //Calendario
    private CaldroidFragment dialogCaldroidFragment;
    private AlertDialog alert;

    private Menu menu;
    private MenuItem item;

    public FragmentLancamentoLista() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentLancamentoFrequenciaPager.refreshLista();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        activity = getActivity();
        Analytics.setTela(activity, activity.getClass().toString());

        appCompatActivity = (AppCompatActivity) getActivity();
        dialogCaldroidFragment = new CaldroidFragment();

        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(null);
            toolbar.setTitle(R.string.frequencia);
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

        data = bundle.getString("data");
        hora = bundle.getString("hora");

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_lancamento_lista, container, false);

        final Bundle state = savedInstanceState;

        listaAlunos = (ArrayList<Aluno>) bundle.getSerializable("listaAlunos");
        listaAula = (ArrayList<Aula>) bundle.getSerializable("listaAulaSelecionada");

        adapter = new LancamentoAlunoAdapter(activity, listaAlunos, data, hora);
        adapter.fragmentLancamentoLista = FragmentLancamentoLista.this;
        //Lista de alunos
        lvLancamento = (ListView) layout.findViewById(R.id.lv_lancamento);
        lvLancamento.setAdapter(adapter);

        return layout;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.menu = menu;

    }

   /* public void setItem(boolean flag){
        item = menu.findItem(R.id.action_confirmar);
        item.setEnabled(flag);
    }
    */
}
