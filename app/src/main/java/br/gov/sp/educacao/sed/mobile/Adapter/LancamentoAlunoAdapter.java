package br.gov.sp.educacao.sed.mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import br.gov.sp.educacao.sed.mobile.Fragment.FragmentLancamentoLista;
import br.gov.sp.educacao.sed.mobile.Modelo.Aluno;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.SegmentedGroup;

/**
 * Created by BRUNO on 04/07/2015.
 */
public class LancamentoAlunoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Aluno> alunoArrayList;
    private String data, hora;
    public FragmentLancamentoLista fragmentLancamentoLista;

    private final String siglaCompareceu;
    private final String siglaFaltou;
    private final String siglaNaoSeAplica;
    private final String siglaTransferido;
    private String descTransferido;

    public LancamentoAlunoAdapter(Context context, ArrayList<Aluno> alunoArrayList, String data, String hora){
        this.context = context;
        this.alunoArrayList = alunoArrayList;
        this.data = data;
        this.hora = hora;

        siglaCompareceu = context.getResources().getString(R.string.sigla_compareceu);
        siglaFaltou = context.getResources().getString(R.string.sigla_falta);
        siglaNaoSeAplica = context.getResources().getString(R.string.sigla_nao_se_aplica);
        siglaTransferido = context.getResources().getString(R.string.sigla_transferido);
        descTransferido = context.getResources().getString(R.string.desc_transferido);
    }

    @Override
    public int getCount() {
        return alunoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return alunoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunoArrayList.get(position).getCodigoAluno();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Aluno aluno = alunoArrayList.get(position);

        int posicaoReal = position + 1;

        View row = convertView;
        final FrequenciaHolder holder;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.layout_frequencia_aluno, parent, false);

            holder = new FrequenciaHolder();

            holder.layAluno = (LinearLayout) row.findViewById(R.id.lay_aluno);
            holder.tvAluno = (TextView) row.findViewById(R.id.tv_aluno);
            holder.tvAtivo = (TextView) row.findViewById(R.id.tv_ativo);
            holder.selecaoSwitch = (SegmentedGroup) row.findViewById(R.id.segmented);

            row.setTag(holder);
        }
        else{
            holder = (FrequenciaHolder) row.getTag();
        }

        //String textAluno = posicaoReal < 10 ? "0" + posicaoReal + " - " + aluno.getNomeAluno() : posicaoReal + " - " + aluno.getNomeAluno();
        String textAluno = aluno.getNumeroChamada() + " - " + aluno.getNomeAluno();

        //int img = position % 2 == 0 ? R.drawable.lancamento_nao : R.drawable.lancamento_sim;

        if (aluno.getAlunoAtivo() == 1){
            holder.tvAtivo.setVisibility(View.GONE);
            holder.selecaoSwitch.setVisibility(View.VISIBLE);
            holder.tvAluno.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.selecaoSwitch.setVisibility(View.GONE);
            holder.tvAluno.setTextColor(context.getResources().getColor(R.color.cinza_titulo_indice));
            holder.tvAtivo.setVisibility(View.VISIBLE);
            holder.tvAtivo.setText(descTransferido);
        }

        holder.tvAluno.setText(textAluno);
        switch (aluno.getComparecimento()){
            case "T": {
                holder.selecaoSwitch.setTintColor(context.getResources().getColor(R.color.caldroid_middle_gray));

                for(int i = 0; i < holder.selecaoSwitch.getChildCount(); i++){
                    RadioButton radioButton = (RadioButton) holder.selecaoSwitch.getChildAt(i);
                    radioButton.setChecked(false);
                }

                holder.selecaoSwitch.setSelected(false);
                holder.selecaoSwitch.setActivated(false);
                break;
            }
            case "C": {
                holder.selecaoSwitch.setTintColor(context.getResources().getColor(R.color.blue_color));
                RadioButton radio = (RadioButton) holder.selecaoSwitch.findViewById(R.id.bt_compareceu);
                radio.setBackgroundColor(context.getResources().getColor(R.color.blue_color));
                radio.setTextColor(context.getResources().getColor(R.color.white));
                break;
            }
            case "F": {
                holder.selecaoSwitch.setTintColor(context.getResources().getColor(R.color.red_sangue));
                RadioButton radio = (RadioButton) holder.selecaoSwitch.findViewById(R.id.bt_falta);
                radio.setBackgroundColor(context.getResources().getColor(R.color.red_sangue));
                radio.setTextColor(context.getResources().getColor(R.color.white));
                break;
            }
            case "N": {
                holder.selecaoSwitch.setTintColor(context.getResources().getColor(R.color.amarelo_texto_dia_letivo));
                RadioButton radio = (RadioButton) holder.selecaoSwitch.findViewById(R.id.bt_nao_aplica);
                radio.setBackgroundColor(context.getResources().getColor(R.color.amarelo_texto_dia_letivo));
                radio.setTextColor(context.getResources().getColor(R.color.white));
                break;
            }
        }
        holder.selecaoSwitch.setOnCheckedChangeListener(null);
        for(int i = 0; i < holder.selecaoSwitch.getChildCount(); i++){
            RadioButton radioButton = (RadioButton) holder.selecaoSwitch.getChildAt(i);
            radioButton.setEnabled(false);
        }

        return row;
    }

    static class FrequenciaHolder{
        LinearLayout layAluno;
        TextView tvAluno;
        SegmentedGroup selecaoSwitch;
        TextView tvAtivo;
    }
}
