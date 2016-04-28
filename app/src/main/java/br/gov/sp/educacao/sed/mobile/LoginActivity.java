package br.gov.sp.educacao.sed.mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import br.gov.sp.educacao.sed.mobile.Action.AtualizarBancoOff;
import br.gov.sp.educacao.sed.mobile.Action.UpdateDBService;
import br.gov.sp.educacao.sed.mobile.Action.ValidarLogin;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.Util.Analytics;
import br.gov.sp.educacao.sed.mobile.Util.NetworkUtils;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 19/06/2015.
 */
public class LoginActivity extends Activity {

    private EditText editusuario, editsenha;
    private Button btlogar;
    private Activity activity;
    private Context context;
    private ProgressDialog progress;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;

        Analytics.setTela(activity, activity.getClass().toString());

        setContentView(R.layout.activity_login);

        editusuario = (EditText) findViewById(R.id.editusuario);
        editsenha = (EditText) findViewById(R.id.editsenha);
        btlogar = (Button) findViewById(R.id.btlogar);

        btlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isWifi(LoginActivity.this)) {
                    startProgress(getResources().getString(R.string.verifica_usuario));

                    AtualizarBancoOff.limparTabelas(LoginActivity.this);

                    String strUsuario = editusuario.getText().toString();
                    String strSenha = editsenha.getText().toString();

                    if (!strUsuario.equals("") && !strSenha.equals("")) {
                        //Chamada de uma task separada somente para Login
                        ValidarLogin validarLogin = new ValidarLogin(context, editusuario.getText().toString().trim(), editsenha.getText().toString().trim());

                        if (validarLogin.executar()) {
                            endProgress();

                            startProgress(getResources().getString(R.string.carrega_info));

                            Intent intentUpdate = new Intent(LoginActivity.this, UpdateDBService.class);
                            startService(intentUpdate);

                            BroadcastReceiver receiver = new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    endProgress();

                                    UsuarioTO usuario = Queries.getUsuarioAtivo(activity);
                                    Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                                    intentLogin.putExtra("usuario", usuario);
                                    startActivity(intentLogin);
                                }
                            };

                            registerReceiver(receiver, new IntentFilter("br.gov.sp.educacao.sed.mobile.CARREGADADOS"));

                            /*ResgatarTurmas resgatarTurmas = new ResgatarTurmas(context);
                            if (resgatarTurmas.executar()) {
                                endProgress();



                            } else {
                                endProgress();
                                AlertFactory.constroeAlert("Ops!", getResources().getString(R.string.erro_turmas), activity).show();
                            }*/
                        } else {
                            endProgress();
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.erro_servidor), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (strUsuario.equals("")) {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.hintusuario), Toast.LENGTH_SHORT).show();
                        } else if (strSenha.equals("")) {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.hintsenha), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.conecte_wifi), Toast.LENGTH_SHORT).show();
                }

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //TODO Validação de Usuario e Senha
    public void startProgress(final String msg) {

        progress = new ProgressDialog(activity);

        Thread thread = new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setTitle("Aguarde");
                        progress.setCanceledOnTouchOutside(false);

                        progress.setMessage(msg);
                        progress.show();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        thread.run();

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {

                    progress.setTitle("Aguarde");
                    progress.setCanceledOnTouchOutside(false);

                    progress.setMessage(msg);
                    progress.show();

                    TimeUnit.SECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    public void endProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://br.gov.sp.educacao.sed.mobile/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://br.gov.sp.educacao.sed.mobile/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}