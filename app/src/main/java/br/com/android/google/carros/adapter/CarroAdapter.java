package br.com.android.google.carros.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.android.google.carros.R;
import br.com.android.google.carros.domain.Carro;

/**
 * Created by jose on 07/08/2016.
 *
 */
public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarrosViewHolder> {

    protected static final String TAG = "livroandroid";
    private final List<Carro> mCarros;
    private final Context mContext;
    private CarroOnClickListener mCarroOnClickListener;


    public CarroAdapter(Context context,List<Carro> carros, CarroOnClickListener carroOnClickListener) {
        this.mCarros = carros;
        this.mContext = context;
        this.mCarroOnClickListener = carroOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.mCarros != null ? this.mCarros.size() : 0;
    }

    @Override
    public CarroAdapter.CarrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_carro, parent, false);

        CarrosViewHolder holder = new CarrosViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CarroAdapter.CarrosViewHolder holder, final int position) {
        //Atualiza a view
        Carro c = mCarros.get(position);
        holder.tNome.setText(c.nome);
        holder.progress.setVisibility(View.INVISIBLE);
        //Faz o download da foto e mostra o ProgressBar
        Picasso.with(mContext).load(c.urlFoto).fit().into(holder.img, new com.squareup.picasso.Callback(){


            @Override
            public void onSuccess() {
                holder.progress.setVisibility(View.GONE);// download ok
            }

            @Override
            public void onError() {
                holder.progress.setVisibility(View.GONE);
            }
        });

        // Click normal
        if(mCarroOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // A variável position é final
                    mCarroOnClickListener.onClickCarro(holder.itemView, position);
                }
            });
        }

        // Click Longo
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCarroOnClickListener.onLongClickCarro(holder.itemView, position);
                return true;
            }
        });

    }

    public interface CarroOnClickListener{
        void onClickCarro(View view, int idx);
        void onLongClickCarro(View view, int idx);
    }

    // ViewHolder com as views
    public static class CarrosViewHolder extends RecyclerView.ViewHolder{

        public TextView tNome;
        ImageView img;
        ProgressBar progress;
        CardView cardView;

        public CarrosViewHolder(View view) {
            super(view);
            //Cria as view para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.text);
            img = (ImageView) view.findViewById(R.id.img);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}
