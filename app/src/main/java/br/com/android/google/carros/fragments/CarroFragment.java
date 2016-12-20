package br.com.android.google.carros.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import br.com.android.google.carros.CarrosApplication;
import br.com.android.google.carros.R;
import br.com.android.google.carros.activity.CarroActivity;
import br.com.android.google.carros.domain.Carro;
import br.com.android.google.carros.domain.CarroBD;
import br.com.android.google.carros.fragments.dialog.DeletarCarroDialog;
import br.com.android.google.carros.fragments.dialog.EditarCarroDialog;
import livroandroid.lib.utils.IntentUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {

    private Carro mCarro;

    public CarroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        mCarro = Parcels.unwrap(getArguments().getParcelable("carro"));

        setHasOptionsMenu(true); // Precisa informar o Android que este fragment contém menu

        view.findViewById(R.id.imgPlayVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo(mCarro.urlVideo, v);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Atualiza a view do fragment com os dados do carro
        setTextString(R.id.tDesc, mCarro.desc);
        final ImageView imgView = (ImageView) getView().findViewById(R.id.img);
        Picasso.with(getContext()).load(mCarro.urlFoto).fit().into(imgView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_flag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            //toast("Editar: " + mCarro.nome);
            EditarCarroDialog.show(getFragmentManager(), mCarro, new EditarCarroDialog.CallBack() {
                @Override
                public void onCarroUpdated(Carro carro) {
                    toast("Carro [" + carro.nome + "] atualizado");
                    // Salva o carro depois de fechar o dialog
                    CarroBD db = new CarroBD(getContext());
                    db.save(carro);
                    //Atualiza o titulo com o novo nome
                    CarroActivity carroActivity = (CarroActivity) getActivity();
                    carroActivity.setTitle(carro.nome);

                    // Envia o evento para bus
                    CarrosApplication.getInstance().getBus().post("refresh");
                }
            });
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            //toast("Delete: " + mCarro.nome);
            DeletarCarroDialog.show(getFragmentManager(), new DeletarCarroDialog.CallBack() {
                @Override
                public void onClickYes() {
                    toast("Carro [" + mCarro.nome + "] deletado.");
                    //Deleta Carro
                    CarroBD db = new CarroBD(getActivity());
                    db.delete(mCarro);
                    //Fecha a Activity
                    getActivity().finish();

                    // Envia o evento para o bus
                    CarrosApplication.getInstance().getBus().post("refresh");
                }
            });
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            toast("Compartilhar");
            return true;
        } else if (item.getItemId() == R.id.action_maps) {
            toast("Mapa");
            return true;
        } else if (item.getItemId() == R.id.action_video) {
            toast("Video");
            // URL do vídeo
            final String url = mCarro.urlVideo;
            // Lê a view que é a âncora do popup (é a view do botão da action bar)
            View menuItemView = getActivity().findViewById(item.getItemId());
            if (menuItemView != null && url != null) {
                // Mostra o alerta com as opções do vídeo
                showVideo(url, menuItemView);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // Cria a PopupMenu posicionado na âncora
    private void showVideo(final String url, View ancoraView){
        if(url != null && ancoraView != null){
            // Cria o PopupMenu posicionado na âncora
            PopupMenu popupMenu = new PopupMenu(getActionBar().getThemedContext(), ancoraView);
            popupMenu.inflate(R.menu.menu_popup_video);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId() == R.id.action_video_browser){
                        // Abre o vídeo no browser
                        IntentUtils.openBrowser(getContext(), url);
                    }else if(item.getItemId() == R.id.action_video_player){
                        // Abre o vídeo no player de vídeo nativo
                        IntentUtils.showVideo(getContext(), url);
                    }else if(item.getItemId() == R.id.action_video_videoview){
                        // Abre outra activity com o VideoView
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }

}
